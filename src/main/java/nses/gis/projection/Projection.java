package nses.gis.projection;




/**
 * 좌표계 변환 클래스
 * @author Administrator
 *
 */
public class Projection  {
	
    private int geoSystemCnt = 8;

    private static double PI = 3.14159265358979;
    private static double EPSLN = 0.0000000001;
    private static double S2R = 4.84813681109536E-06;


    private static int X_W2B = 128;
    private static int Y_W2B =-481;
    private static int Z_W2B =-664;

    // Ellips/System Type
    private int m_eSrcEllips;
    private int m_eSrcSystem;
    private int m_eDstEllips;
    private int m_eDstSystem;

    // Ellips Factor List
    private double[] m_arMajor = new double[3];
    private double[] m_arMinor = new double[3];

    // System Factor List
    private double[] m_arScaleFactor = new double[geoSystemCnt];
    private double[] m_arLonCenter = new double[geoSystemCnt];
    private double[] m_arLatCenter = new double[geoSystemCnt];
    private double[] m_arFalseNorthing = new double[geoSystemCnt];
    private double[] m_arFalseEasting = new double[geoSystemCnt];

    private static final long max_iter = 6; // maximun number of iterations

    // Internal Value for Tm2Geo
    private double m_dSrcE0, m_dSrcE1, m_dSrcE2, m_dSrcE3;
    private double m_dSrcE, m_dSrcEs, m_dSrcEsp;
    private double m_dSrcMl0, m_dSrcInd;

    // Internal Value for Geo2Tm
    private double m_dDstE0, m_dDstE1, m_dDstE2, m_dDstE3;
    private double m_dDstE, m_dDstEs, m_dDstEsp;
    private double m_dDstMl0, m_dDstInd;

    // Internal Value for DatumTrans
    private double m_dTemp;
    private double m_dEsTemp;
    private int m_iDeltaX;
    private int m_iDeltaY;
    private int m_iDeltaZ;
    private double m_dDeltaA, m_dDeltaF;


    public Projection(){
    	this.init();
    }
//

    public Projection(int eSrcEllips, int eSrcSystem,
                      int eDstEllips , int eDstSystem){
        m_eSrcEllips = eSrcEllips;
        m_eSrcSystem = eSrcSystem;
        m_eDstEllips = eDstEllips;
        m_eDstSystem = eDstSystem;

        SetSrcType(eSrcEllips, eSrcSystem);
        SetDstType(eDstEllips, eDstSystem);
        this.init();

    }
    
    /*
     * 좌표계 상수 초기화
     */
    private  void init(){
    	//장반경을 a, 단반경을 b라 하면 편평률은 (a-b)/a
        // 편평율 (장반경 - 단반경)/장반경
    	//1/298.257222101
    	// 단반경 = (장반경 - 편평율*장반경)
    	// GRS80과 WGS84와의 차이는 단반경이 약 0.1mm 
        m_arMajor[GeoEllips.kBessel1984] = 6377397.155;
        //원래 값
        //m_arMinor[GeoEllips.kBessel1984] = 6356078.96325;
        m_arMinor[GeoEllips.kBessel1984] = 6356078.9633422494;
        //6356078.9633422494

        m_arMajor[GeoEllips.kWgs84] = 6378137.0;
        m_arMinor[GeoEllips.kWgs84] = 6356752.31414;
        //6356752.31414
        //원래 값
        //m_arMinor[GeoEllips.kWgs84] = 6356752.3142;
        
        m_arMajor[GeoEllips.kGrs80] = 6378137.0;
        m_arMinor[GeoEllips.kGrs80] = 6356752.3141;
        //6356752.314140356

        // Set System Factor
        m_arScaleFactor[GeoSystem.kGeographic] = 1;
        m_arLonCenter[GeoSystem.kGeographic] = 0.0;
        m_arLatCenter[GeoSystem.kGeographic] = 0.0;
        m_arFalseNorthing[GeoSystem.kGeographic] = 0.0;
        m_arFalseEasting[GeoSystem.kGeographic] = 0.0;

        m_arScaleFactor[GeoSystem.kTmWest] = 1;
        m_arLonCenter[GeoSystem.kTmWest] = 2.18171200985643;
        m_arLatCenter[GeoSystem.kTmWest] = 0.663225115757845;
        m_arFalseNorthing[GeoSystem.kTmWest] = 500000.0;
        m_arFalseEasting[GeoSystem.kTmWest] = 200000.0;
        
        // x＝180*θ/π
        // θ= x*π/180

        //127도 레디안 값   2.21656815003279

        //10.405초 더한 값  2.21661859489632
        //10.405초 도단위 : 0.00289028 
        //10.405초 뺀 도단위 값 126.99710972
        //126.99710972의 레인안 값 : 2.216517705130494020984191365931
        
        //서울시(나브텍 데이타를 서울시 GRS80 TM 으로 변환시 x 축으로 1미터, y축으로 3미터 오차가 발생함
        m_arScaleFactor[GeoSystem.kTmMid] = 1;
        m_arLonCenter[GeoSystem.kTmMid] = 2.21656815003279;
        m_arLatCenter[GeoSystem.kTmMid] = 0.663225115757845;
        m_arFalseNorthing[GeoSystem.kTmMid] = 500000.0 +1;
        m_arFalseEasting[GeoSystem.kTmMid] = 200000.0 +3;
        
        //새주소 중부 원점
//        m_arScaleFactor[GeoSystem.kTmMid] = 0.9996;
//        //m_arLonCenter[GeoSystem.kTmMid] = 2.2253452363081528467024788244862;
//        m_arLonCenter[GeoSystem.kTmMid] = 2.225345236308152;
//        m_arLatCenter[GeoSystem.kTmMid] = 0.663225115757845;
//        m_arFalseNorthing[GeoSystem.kTmMid] = 2000000;
//        m_arFalseEasting[GeoSystem.kTmMid] = 1000000;

        //10.405초 더한 값  2.21661859489632
        /*
        m_arScaleFactor[GeoSystem.kTmMid] = 1;
        m_arLonCenter[GeoSystem.kTmMid] = 2.21661859489632;
        m_arLonCenter[GeoSystem.kTmMid] = 2.21656815003279;        
        m_arLatCenter[GeoSystem.kTmMid] = 0.663225115757845;
        m_arFalseNorthing[GeoSystem.kTmMid] = 500000.0;
        m_arFalseEasting[GeoSystem.kTmMid] = 200000.0;
        */

        //기본
        /*
        m_arScaleFactor[GeoSystem.kTmMid] = 1;
        m_arLonCenter[GeoSystem.kTmMid] = 2.21656815003279;
        m_arLonCenter[GeoSystem.kTmMid] = 2.21656815003279;        
        m_arLatCenter[GeoSystem.kTmMid] = 0.663225115757845;
        m_arFalseNorthing[GeoSystem.kTmMid] = 500000.0;
        m_arFalseEasting[GeoSystem.kTmMid] = 200000.0;
        */


        m_arScaleFactor[GeoSystem.kTmEast] = 1;
        m_arLonCenter[GeoSystem.kTmEast] = 2.2515251799362;
        m_arLatCenter[GeoSystem.kTmEast] = 0.663225115757845;
        m_arFalseNorthing[GeoSystem.kTmEast] = 500000.0;
        m_arFalseEasting[GeoSystem.kTmEast] = 200000.0;

        m_arScaleFactor[GeoSystem.kKatec] = 0.9999;
        m_arLonCenter[GeoSystem.kKatec] = 2.23402144255274;
        m_arLatCenter[GeoSystem.kKatec] = 0.663225115757845;
        m_arFalseNorthing[GeoSystem.kKatec] = 600000.0;
        m_arFalseEasting[GeoSystem.kKatec] = 400000.0;
        
        m_arScaleFactor[GeoSystem.kUtmKR] = 0.9996;
        m_arLonCenter[GeoSystem.kUtmKR] = 2.225294796292767;
        m_arLatCenter[GeoSystem.kUtmKR] = 0.663225115757844;
        m_arFalseNorthing[GeoSystem.kUtmKR] = 2000000.0;
        m_arFalseEasting[GeoSystem.kUtmKR] = 1000000.0;


        m_arScaleFactor[GeoSystem.kUtm52] = 0.9996;
        m_arLonCenter[GeoSystem.kUtm52] = 2.25147473507269;
        m_arLatCenter[GeoSystem.kUtm52] = 0.0;
        m_arFalseNorthing[GeoSystem.kUtm52] = 0.0;
        m_arFalseEasting[GeoSystem.kUtm52] = 500000.0;
        //0.052359877559829834

        //grs80 제원
        m_arScaleFactor[GeoSystem.kUtm51] = 0.9996;
        m_arLonCenter[GeoSystem.kUtm51] = 2.225294796292767;
        m_arLatCenter[GeoSystem.kUtm51] = 0.663225115757844;
        m_arFalseNorthing[GeoSystem.kUtm51] = 2000000.0;
        m_arFalseEasting[GeoSystem.kUtm51] = 1000000.0;
        
        /*
        m_arScaleFactor[GeoSystem.kUtm17] = 0.9996;
        m_arLonCenter[GeoSystem.kUtm17] = -1.4137166941154056;
        m_arLatCenter[GeoSystem.kUtm17] = 0.0;
        m_arFalseNorthing[GeoSystem.kUtm17] = 10000000.0;
        m_arFalseEasting[GeoSystem.kUtm17] = 500000.0;
        */
        

    }

    
    private void LonCenter(int GeoSystem, double lon){
        double rad = D2R(lon);
        m_arLonCenter[GeoSystem] = rad;
    }
    
    /**
     * 원본 좌표계 상수 셋팅
     * @param eEllips
     * @param eSystem
     */
    public void SetSrcType(int eEllips, int eSystem) {
        m_eSrcEllips = eEllips;
        m_eSrcSystem = eSystem;

        double temp = m_arMinor[m_eSrcEllips] / m_arMajor[m_eSrcEllips];
        m_dSrcEs = 1.0 - temp * temp;
        m_dSrcE = Math.sqrt(m_dSrcEs);
        m_dSrcE0 = e0fn(m_dSrcEs);
        m_dSrcE1 = e1fn(m_dSrcEs);
        m_dSrcE2 = e2fn(m_dSrcEs);
        m_dSrcE3 = e3fn(m_dSrcEs);
        m_dSrcMl0 = m_arMajor[m_eSrcEllips] * mlfn(m_dSrcE0, m_dSrcE1, m_dSrcE2, m_dSrcE3, m_arLatCenter[m_eSrcSystem]);
        m_dSrcEsp = m_dSrcEs / (1.0 - m_dSrcEs);

        if (m_dSrcEs < 0.00001)
            m_dSrcInd = 1.0;
        else
            m_dSrcInd = 0.0;

        InitDatumVar();
    }
    
    /**
     * 변환될 좌표계 셋팅
     * @param eEllips
     * @param eSystem
     */
    public void SetDstType(int eEllips, int eSystem){
        m_eDstEllips = eEllips;
        m_eDstSystem = eSystem;

        double temp = m_arMinor[m_eDstEllips] / m_arMajor[m_eDstEllips];
        m_dDstEs = 1.0 - temp * temp;
        m_dDstE = Math.sqrt(m_dDstEs);
        m_dDstE0 = e0fn(m_dDstEs);
        m_dDstE1 = e1fn(m_dDstEs);
        m_dDstE2 = e2fn(m_dDstEs);
        m_dDstE3 = e3fn(m_dDstEs);
        m_dDstMl0 = m_arMajor[m_eDstEllips] * mlfn(m_dDstE0, m_dDstE1, m_dDstE2, m_dDstE3, m_arLatCenter[m_eDstSystem]);
        m_dDstEsp = m_dDstEs / (1.0 - m_dDstEs);

        if (m_dDstEs < 0.00001)
            m_dDstInd = 1.0;
        else
            m_dDstInd = 0.0;

        InitDatumVar();
    }

    /**
     *  투영계수 초기화
     */
    private void InitDatumVar(){
        int iDefFact;
        double dF;

        // direction factor for datum transformation
        // eg) Bessel to Bessel would be 0
        //     WGS84 to Bessel would be 1
        //     BEssel to WGS84 would be -1
        iDefFact = m_eSrcEllips - m_eDstEllips;
        m_iDeltaX = iDefFact * X_W2B;
        m_iDeltaY = iDefFact * Y_W2B;
        m_iDeltaZ = iDefFact * Z_W2B;

        m_dTemp = m_arMinor[m_eSrcEllips] / m_arMajor[m_eSrcEllips];
        dF = 1.0 - m_dTemp; // flattening
        m_dEsTemp = 1.0 - m_dTemp * m_dTemp; // e2

        m_dDeltaA = m_arMajor[m_eDstEllips] - m_arMajor[m_eSrcEllips]; // output major axis - input major axis
        m_dDeltaF = m_arMinor[m_eSrcEllips] / m_arMajor[m_eSrcEllips] - m_arMinor[m_eDstEllips] / m_arMajor[m_eDstEllips]; // Output Flattening - input flattening
    }

    /**
     * 좌표 변환 함수
     * @param coord
     * @return
     */
    public Coordinate Conv(Coordinate coord ){
    	
    	if(m_eSrcEllips == m_eDstEllips && m_eSrcSystem == m_eDstSystem){
    		Coordinate _out = new Coordinate();
    		_out.x = coord.x;
    		_out.y = coord.y;
    		return _out;
    	}
    	
        double dInLon, dInLat;
        double dOutLon, dOutLat;
        double dTmX, dTmY;
        Coordinate _out = new Coordinate();
        Coordinate din = new Coordinate();
        Coordinate dout = new Coordinate();
        Coordinate tm = new Coordinate();
        if (m_eSrcSystem == GeoSystem.kGeographic){
            //dInLon = D2R(dInX);
            //dInLat = D2R(dInY);
            din.x = D2R(coord.x);
            din.y = D2R(coord.y);
        }
        else{
            // Geographic calculating
//                Tm2Geo(dInX, dInY, dInLon, dInLat);
            Tm2Geo(coord.x, coord.y,  din);
        }

        if (m_eSrcEllips == m_eDstEllips){
            //dOutLon = dInLon;
            //dOutLat = dInLat;
            dout.x= din.x;
            dout.y= din.y;
        }
        else{
            // Datum transformation using molodensky function
            //DatumTrans(dInLon, dInLat, dOutLon, dOutLat);
            DatumTrans(din.x, din.y, dout);
        }

        // now we should make a output. but it depends on user options
        // if output option is latitude & longitude
        if (m_eDstSystem == GeoSystem.kGeographic){

            _out.x = R2D(dout.x);
            _out.y = R2D(dout.y);
            /*
            _out.x = R2D(dout.x)*36000;
            _out.y = R2D(dout.y)*36000;
            */
        }
        else{
            // TM or UTM calculating
            Geo2Tm(dout.x, dout.y, tm);

            _out.x = tm.x;
            _out.y = tm.y;
            /*
            _out.x = tm.x*36000;
            _out.y = tm.y*36000;
            */
        }
        return _out;
    }
    
    /**
     * degree 단위를 radian 단위로 변환
     * @param degree
     * @return
     */
    public double D2R(double degree){
        return (degree * PI / 180.0);
    }

    /**
     * radian 단위를 degree 단위로 변환
     * @param radian
     * @return
     */
    public double R2D(double radian){
        return (radian * 180.0 / PI);
    }

    /**
     * 평면을 타원체로 변환
     * @param x
     * @param y
     * @param _point
     */
    private void Tm2Geo(double x, double y, Coordinate _point){
        double lon, lat;
        double con; // temporary angles
        double phi; // temporary angles
        double delta_Phi; // difference between longitudes
        long i; // counter variable
        double sin_phi, cos_phi, tan_phi; // sin cos and tangent values
        double c, cs, t, ts, n, r, d, ds; // temporary variables
        double f, h, g, temp; // temporary variables

        if (m_dSrcInd != 0)
        {
            f = Math.exp(x / (m_arMajor[m_eSrcEllips] * m_arScaleFactor[m_eSrcSystem]));
            g = 0.5 * (f - 1.0 / f);
            temp = m_arLatCenter[m_eSrcSystem] + y / (m_arMajor[m_eSrcEllips] * m_arScaleFactor[m_eSrcSystem]);
            h = Math.cos(temp);
            con = Math.sqrt((1.0 - h * h) / (1.0 + g * g));
            lat = asinz(con);//lat = asinz(con);

            if (temp < 0)
                lat *= -1;

            if ((g == 0) && (h == 0))
                lon = m_arLonCenter[m_eSrcSystem];
            else
                lon = Math.atan(g / h) + m_arLonCenter[m_eSrcSystem];
        }

        // TM to LL inverse equations from here

        x -= m_arFalseEasting[m_eSrcSystem];
        y -= m_arFalseNorthing[m_eSrcSystem];

        con = (m_dSrcMl0 + y / m_arScaleFactor[m_eSrcSystem]) / m_arMajor[m_eSrcEllips];
        phi = con;

        i = 0;
        while(true)
        {
            delta_Phi = ((con + m_dSrcE1 * Math.sin(2.0 * phi) - m_dSrcE2 * Math.sin(4.0 * phi) +
                          m_dSrcE3 * Math.sin(6.0 * phi)) / m_dSrcE0) - phi;
            phi = phi + delta_Phi;
            if (Math.abs(delta_Phi) <= EPSLN) break;

            if (i >= max_iter){
                //System.out.println("Conversion :: error ::Latitude failed to converge");
                return;
            }
            i++;
        }

        if (Math.abs(phi) < (Math.PI / 2))
        {
            sin_phi = Math.sin(phi);
            cos_phi = Math.cos(phi);
            tan_phi = Math.tan(phi);
            c = m_dSrcEsp * cos_phi * cos_phi;
            cs = c * c;
            t = tan_phi * tan_phi;
            ts = t * t;
            con = 1.0 - m_dSrcEs * sin_phi * sin_phi;
            n = m_arMajor[m_eSrcEllips] / Math.sqrt(con);
            r = n * (1.0 - m_dSrcEs) / con;
            d = x / (n * m_arScaleFactor[m_eSrcSystem]);
            ds = d * d;
            lat = phi - (n * tan_phi * ds / r) * (0.5 - ds / 24.0 * (5.0 + 3.0 * t + 10.0 * c - 4.0 * cs - 9.0 * m_dSrcEsp - ds / 30.0 * (61.0 + 90.0 * t + 298.0 * c + 45.0 * ts - 252.0 * m_dSrcEsp - 3.0 * cs)));
            lon = m_arLonCenter[m_eSrcSystem] + (d * (1.0 - ds / 6.0 * (1.0 + 2.0 * t + c - ds / 20.0 * (5.0 - 2.0 * c + 28.0 * t - 3.0 * cs + 8.0 * m_dSrcEsp + 24.0 * ts))) / cos_phi);
        }
        else
        {
            lat = Math.PI*0.5 * Math.sin(y);
            lon = m_arLonCenter[m_eSrcSystem];
        }
        _point.x = lon;
        _point.y = lat;
    }
    
    /**
     * 타원체를 평면으로 변환
     * @param dInLon
     * @param dInLat
     * @param _dout
     */
    private void DatumTrans(double dInLon, double dInLat, Coordinate _dout){//dOutLon, dOutLat
        double dRm, dRn;
        double dDeltaPhi, dDeltaLamda;
        //double dDeltaH;
        dRm = m_arMajor[m_eSrcEllips] * (1.0-m_dEsTemp) / Math.pow(1.0-m_dEsTemp*Math.sin(dInLat)*Math.sin(dInLat), 1.5);
        dRn = m_arMajor[m_eSrcEllips] / Math.sqrt(1.0 - m_dEsTemp*Math.sin(dInLat)*Math.sin(dInLat));
        dDeltaPhi = ((((-m_iDeltaX*Math.sin(dInLat)*Math.cos(dInLon) - m_iDeltaY*Math.sin(dInLat)*Math.sin(dInLon)) +
                       m_iDeltaZ*Math.cos(dInLat)) + m_dDeltaA*dRn*m_dEsTemp*Math.sin(dInLat)*Math.cos(dInLat)/m_arMajor[m_eSrcEllips]) +
                       m_dDeltaF*(dRm/m_dTemp+dRn*m_dTemp)*Math.sin(dInLat)*Math.cos(dInLat))/dRm;
        dDeltaLamda = (-m_iDeltaX * Math.sin(dInLon) + m_iDeltaY * Math.cos(dInLon)) / (dRn * Math.cos(dInLat));
        //dDeltaH = iDeltaX * cos(dInLat) * cos(dInLon) + iDeltaY * cos(dInLat) * sin(dInLon) + iDeltaZ * sin(dInLat) - dDeltaA * m_arMajor[eSrcEllips] / dRn + dDeltaF * dTemp * dRn * sin(dInLat) * sin(dInLat);
        //dOutLat = dInLat + dDeltaPhi;
        //dOutLon = dInLon + dDeltaLamda;
        _dout.x = (dInLon + dDeltaLamda);
        _dout.y = (dInLat + dDeltaPhi);
    }
    
    /**
     * 타원체를 평면으로 변환
     * @param lon
     * @param lat
     * @param _point
     */
    private void Geo2Tm(double lon, double lat, Coordinate _point){//double x, double y ??????
        double delta_lon; // Delta longitude (Given longitude - center longitude)
        double sin_phi, cos_phi; // sin and cos value
        double al, als; // temporary values
        double b, c, t, tq; // temporary values
        double con, n, ml; // cone constant, small m

        // LL to TM Forward equations from here
        delta_lon = lon - m_arLonCenter[m_eDstSystem];
        sin_phi = Math.sin(lat);
        cos_phi = Math.cos(lat);

        if (m_dDstInd != 0){
            b = cos_phi * Math.sin(delta_lon);
            if ((Math.abs(Math.abs(b) - 1.0)) < 0.0000000001){
                //System.out.println("Conversion :: error :: ??????? ???? ?????? ?????");
                return;
            }
        }
        else
        {
            b = 0;
            _point.x = (0.5 * m_arMajor[m_eDstEllips] * m_arScaleFactor[m_eDstSystem] * Math.log((1.0 + b) / (1.0 - b)));
            con = Math.acos(cos_phi * Math.cos(delta_lon) / Math.sqrt(1.0 - b * b));
            if (lat < 0){
                con = -con;
                _point.y =(m_arMajor[m_eDstEllips] * m_arScaleFactor[m_eDstSystem] * (con - m_arLatCenter[m_eDstSystem]));
            }
        }

        al = cos_phi * delta_lon;
        als = al * al;
        c = m_dDstEsp * cos_phi * cos_phi;
        tq = Math.tan(lat);
        t = tq * tq;
        con = 1.0 - m_dDstEs * sin_phi * sin_phi;
        n = m_arMajor[m_eDstEllips] / Math.sqrt(con);
        ml = m_arMajor[m_eDstEllips] * mlfn(m_dDstE0, m_dDstE1, m_dDstE2, m_dDstE3, lat);

        _point.x=(m_arScaleFactor[m_eDstSystem] * n * al * (1.0 + als / 6.0 * (1.0 - t + c + als / 20.0 * (5.0 - 18.0 * t + t * t + 72.0 * c - 58.0 * m_dDstEsp))) + m_arFalseEasting[m_eDstSystem]);
        _point.y=(m_arScaleFactor[m_eDstSystem] * (ml - m_dDstMl0 + n * tq * (als * (0.5 + als / 24.0 * (5.0 - t + 9.0 * c + 4.0 * c * c + als / 30.0 * (61.0 - 58.0 * t + t * t + 600.0 * c - 330.0 * m_dDstEsp))))) + m_arFalseNorthing[m_eDstSystem]);
    }


    /**
     * 도단위를 도/분/초 단위로 변환
     * @param dInDecimalDegree
     * @param iOutDegree
     * @param iOutMinute
     * @param dOutSecond
     */
    private void D2Dms(double dInDecimalDegree, int iOutDegree, int iOutMinute, double dOutSecond){

        double dTmpMinute;

        iOutDegree = (int)dInDecimalDegree;
        dTmpMinute = (dInDecimalDegree - iOutDegree) * 60.0;
        iOutMinute = (int)dTmpMinute;
        dOutSecond = (dTmpMinute - iOutMinute) * 60.0;
        if ((dOutSecond+0.00001) >= 60.0)
        {
            if (iOutMinute == 59)
            {
                iOutDegree++;
                iOutMinute = 0;
                dOutSecond = 0.0;
            }
            else {
                iOutMinute++;
                dOutSecond = 0.0;
            }
        }
    }

    /**
     * 
     * @param x
     * @return
     */
    private double e0fn(double x)
    {
        return 1.0 - 0.25 * x * (1.0 + x / 16.0 * (3.0 + 1.25 * x));
    }
    /**
     * 
     * @param x
     * @return
     */

    private double e1fn(double x)
    {
        return 0.375 * x * (1.0 + 0.25 * x * (1.0 + 0.46875 * x));
    }
    /**
     * 
     * @param x
     * @return
     */

    private double e2fn(double x)
    {
        return 0.05859375 * x * x * (1.0 + 0.75 * x);
    }
    /**
     * 
     * @param x
     * @return
     */

    private double e3fn(double x)
    {
        return x * x * x * (35.0 / 3072.0);
    }
    /**
     * 
     * @param x
     * @return
     */

    private double e4fn(double x)
    {
        double con, com;

        con = 1.0 + x;
        com = 1.0 - x;
        return Math.sqrt(Math.pow(con, con) * Math.pow(com, com));
    }

    /**
     * 
     * @param x
     * @return
     */
    private double mlfn(double e0, double e1, double e2, double e3, double phi){
        return e0 * phi - e1 * Math.sin(2.0 * phi) + e2 * Math.sin(4.0 * phi) - e3 * Math.sin(6.0 * phi);
    }
    
    /**
     * 
     * @param x
     * @return
     */
    private double asinz(double value)
    {
        if (Math.abs(value) > 1.0)
            value = (value>0?1:-1);

        return Math.sin(value);
    }
    
    public static void main(String[] args){
		Projection prj = new Projection();
		
		prj.SetSrcType(GeoEllips.kGrs80, GeoSystem.kUtmKR);
		prj.SetDstType(GeoEllips.kBessel1984, GeoSystem.kKatec);
		
		Coordinate coord = new Coordinate();
		coord.x = 879731.6745;
		coord.y = 1868207.8485;
		Coordinate convC = prj.Conv(coord);
		
		//System.out.println("x1="+coord.x +", y1="+coord.y+", x2="+convC.x+", y2="+convC.y);

    }
}


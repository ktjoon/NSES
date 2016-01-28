package nses.common.coord;

import java.net.URLEncoder;
import java.nio.charset.Charset;

import nses.gis.projection.Coordinate;
import nses.gis.projection.GeoEllips;
import nses.gis.projection.GeoSystem;
import nses.gis.projection.Projection;

import org.apache.commons.codec.binary.Base64;


public class DataTypeUtil {
	
	//private final static Logger logger = Logger.getLogger("AVL");
	
	public static String toString(Object input) {
		if(input == null)
			return "";
		else
			return input.toString().trim();
	}
	
	public static String isNull(String input) {
		if(input == null) 
			return "";
		else
			return input.trim();
	}
	
	public static int parseInteger(String input) {
		try {
			return Integer.parseInt(input);
		} catch(Exception e) {
			return 0;
		}
	}
	
	public static double parseDouble(String input) {
		try {
			return Double.parseDouble(input);
		} catch(Exception e) {
			return 0;
		}
	}
	
	public static CoordPoint tmToWGS84(String x, String y) {
		return TransCoord.getTransCoord(
				new CoordPoint(parseDouble(x) - CommonProperties.EAST_TM_X_DIFF, parseDouble(y) - CommonProperties.EAST_TM_Y_DIFF), 
				TransCoord.COORD_TYPE_TM, 
				TransCoord.COORD_TYPE_WGS84);
	}
	
	public static CoordPoint eastTmToWGS84(double x, double y) {
		return TransCoord.getTransCoord(
				new CoordPoint(x - CommonProperties.EAST_TM_X_DIFF, y - CommonProperties.EAST_TM_Y_DIFF), 
				TransCoord.COORD_TYPE_TM_EAST, 
				TransCoord.COORD_TYPE_WGS84);
	}
	
	public static CoordPoint wgs84ToEastTm(double lat, double lon) {
		CoordPoint point = TransCoord.getTransCoord(
				new CoordPoint(lon, lat), 
				TransCoord.COORD_TYPE_WGS84, 
				TransCoord.COORD_TYPE_TM_EAST);
		point.x = roundDouble(point.x + CommonProperties.EAST_TM_X_DIFF, 4);
		point.y = roundDouble(point.y + CommonProperties.EAST_TM_Y_DIFF, 4);
		
		return point;
	}
	
	public static CoordPoint westTmToWGS84(double x, double y) {
		return TransCoord.getTransCoord(
				new CoordPoint(x - CommonProperties.WEST_TM_X_DIFF, y - CommonProperties.WEST_TM_Y_DIFF), 
				TransCoord.COORD_TYPE_TM_WEST, 
				TransCoord.COORD_TYPE_WGS84);
	}
	
	public static CoordPoint wgs84ToWestTm(double lat, double lon) {
		CoordPoint point = TransCoord.getTransCoord(
				new CoordPoint(lon, lat), 
				TransCoord.COORD_TYPE_WGS84, 
				TransCoord.COORD_TYPE_TM_WEST);
		point.x = roundDouble(point.x + CommonProperties.WEST_TM_X_DIFF, 4);
		point.y = roundDouble(point.y + CommonProperties.WEST_TM_Y_DIFF, 4);
		
		return point;
	}
	
	public static CoordPoint centerTmToWGS84(double x, double y) {
		return TransCoord.getTransCoord(
				new CoordPoint(x - CommonProperties.CENTER_TM_X_DIFF, y - CommonProperties.CENTER_TM_Y_DIFF), 
				TransCoord.COORD_TYPE_TM_CENTER, 
				TransCoord.COORD_TYPE_WGS84);
	}
	
	public static CoordPoint centerTmToWGS84NoDiff(double x, double y) {
		return TransCoord.getTransCoord(
				new CoordPoint(x, y), 
				TransCoord.COORD_TYPE_TM_CENTER, 
				TransCoord.COORD_TYPE_WGS84);
	}
	
	public static CoordPoint centerTmToWGS84PlusDiff(double x, double y){

		return TransCoord.getTransCoord(
				new CoordPoint(x + CommonProperties.CENTER_TM_X_DIFF, y + CommonProperties.CENTER_TM_Y_DIFF), 
				TransCoord.COORD_TYPE_TM_CENTER, 
				TransCoord.COORD_TYPE_WGS84);
		
//		CoordPoint point = TransCoord.getTransCoord(
//				new CoordPoint(lon + CommonProperties.CENTER_TM_X_DIFF, lat + CommonProperties.CENTER_TM_Y_DIFF),
//				TransCoord.COORD_TYPE_TM_CENTER,
//				TransCoord.COORD_TYPE_WGS84);
//		point.x = roundDouble(point.x , 4);
//		point.y = roundDouble(point.y , 4);
		
//		return point;

	}
	
	public static CoordPoint centerTmToWGS84WithNoDiff(double x, double y) {
		return TransCoord.getTransCoord(
				new CoordPoint(x + CommonProperties.CENTER_TM_X_DIFF, y), 
				TransCoord.COORD_TYPE_TM_CENTER, 
				TransCoord.COORD_TYPE_WGS84);
	}

	public static CoordPoint wgs84ToCenterTm(double lat, double lon) {
		CoordPoint point = TransCoord.getTransCoord(
				new CoordPoint(lon, lat), 
				TransCoord.COORD_TYPE_WGS84, 
				TransCoord.COORD_TYPE_TM_CENTER);
		point.x = roundDouble(point.x + CommonProperties.CENTER_TM_X_DIFF, 4);
		point.y = roundDouble(point.y + CommonProperties.CENTER_TM_Y_DIFF, 4);
		
		return point;
	}
	
	public static CoordPoint wgs84ToCenterTmWithNoDiff(double lat, double lon) {
		CoordPoint point = TransCoord.getTransCoord(
				new CoordPoint(lon, lat), 
				TransCoord.COORD_TYPE_WGS84, 
				TransCoord.COORD_TYPE_TM_CENTER);
		point.x = roundDouble(point.x, 4) - CommonProperties.CENTER_TM_X_DIFF;
		point.y = roundDouble(point.y, 4);
		
		return point;
	}
	
	public static CoordPoint wgs84ToCenterTmMinusDiff(double lat, double lon) {
		CoordPoint point = TransCoord.getTransCoord(
				new CoordPoint(lon, lat), 
				TransCoord.COORD_TYPE_WGS84, 
				TransCoord.COORD_TYPE_TM_CENTER);
		point.x = roundDouble(point.x - CommonProperties.CENTER_TM_X_DIFF, 4);
		point.y = roundDouble(point.y - CommonProperties.CENTER_TM_Y_DIFF, 4);
		
		return point;
	}
	
	public static CoordPoint ktmToWGS84(double x, double y) {
		return TransCoord.getTransCoord(
				new CoordPoint(x, y), 
				TransCoord.COORD_TYPE_KTM, 
				TransCoord.COORD_TYPE_WGS84);
	}
	
	public static CoordPoint wgs84ToKtm(double lat, double lon) {
		return TransCoord.getTransCoord(
				new CoordPoint(lon, lat), 
				TransCoord.COORD_TYPE_WGS84, 
				TransCoord.COORD_TYPE_KTM);
	}
	
	public static String stringToBase64(String input) {
		try {
			
//			logger.debug(input);
//			
//			logger.debug(new String(input.getBytes("KSC5601"), "EUC-KR"));
//			
//			logger.debug(Base64.encodeBase64String(new String(input.getBytes("KSC5601"), "EUC-KR").getBytes()));
			
//			return Base64.encodeBase64String(URLEncoder.encode(new String(input.getBytes("UTF-8"), "KSC5601"), "UTF-8").getBytes());
			return Base64.encodeBase64String(URLEncoder.encode(input, "UTF-8").getBytes());
//			return input;
//			return Base64.encodeBase64String(input.getBytes());
		} catch (Exception e) {
			return input;
		}
	}
	
	public static String mdbStringToBase64(String input) {
		try {
			return Base64.encodeBase64String(URLEncoder.encode(input, "UTF-8").getBytes());
		} catch (Exception e) {
			return input;
		}
	}
	
	public static String base64ToString(String input) {
		try {
			
//			return new String(Base64.decodeBase64(input), "UTF-8");
			return new String(Base64.decodeBase64(input));
		} catch (Exception e) {
			return input;
		}
	}
	
	public static String changeCharset(String input, String orgType, String outType) {
		byte[] inputBytes = { (byte)0xc3, (byte)0xa2, 0x61, 0x62, 0x63, 0x64 };
	    Charset inputCharset = Charset.forName(orgType);
	    Charset outputCharset = Charset.forName(outType);

	    String string = new String ( inputBytes, inputCharset );

	    byte[] iso88591bytes = string.getBytes(outputCharset);

	    // "then create a new string with the bytes in ISO-8859-1 encoding"
	    return new String ( iso88591bytes, outputCharset );
	}
	
	public static int convertWgs84ToNumber(double input) {
		return (int) (input * 360000);
	}
	
	public static double convertCenterTmToNumber(double input){
		return (double) (input/360000);
	}
	
	public static String ksc5601ToUtf8(String input) {
		return input;
//		if(input == null)return "";
//		try {
//			return new String( input.getBytes( "KSC5601"), "UTF-8");
//		} catch(Exception e) {
//			return "";
//		}
//		return changeCharset(input, "KSC5601", "UTF-8");
	}
	
	public static String utf8ToKsc5601(String input) {
		
		return input;
		
//		if(input == null)return "";
//		try {
//			return new String( input.getBytes( "UTF-8"), "KSC5601");
//		} catch(Exception e) {
//			return "";
//		}
//		return changeCharset(input, "UTF-8", "KSC5601");
	}
	
	public static double roundDouble(double input, int digit) {
		try {
			return Double.parseDouble(String.format("%." + digit + "f", input));
		} catch (Exception e) {
			return input;
		}
	}
	
	public static float roundFloat(float input, int digit) {
		try {
			return Float.parseFloat(String.format("%." + digit + "f", input));
		} catch(Exception e) {
			return input;
		}
	}
	
	public static CoordPoint centerTmToWGS84_NSES(double x, double y) {
		/*return TransCoord.getTransCoord(
				new CoordPoint(x - CommonProperties.CENTER_TM_X_DIFF, y - CommonProperties.CENTER_TM_Y_DIFF), 
				TransCoord.COORD_TYPE_TM_CENTER, 
				TransCoord.COORD_TYPE_WGS84);*/
		Projection 	prj = new Projection();
		Coordinate 	coord = new Coordinate(x, y);

		prj.SetSrcType(GeoEllips.kGrs80, GeoSystem.kTmMid);
		prj.SetDstType(GeoEllips.kWgs84, GeoSystem.kUtmKR);

		Coordinate 	conv1 = prj.Conv(coord);

		conv1.x	+= CommonProperties.CENTER_TM_X_DIFF;
		conv1.y	+= CommonProperties.CENTER_TM_Y_DIFF;
		prj.SetSrcType(GeoEllips.kGrs80, GeoSystem.kUtmKR);
		prj.SetDstType(GeoEllips.kWgs84, GeoSystem.kGeographic);

		Coordinate 	convR = prj.Conv(conv1);
		
		return new CoordPoint(convR.x, convR.y);
	}
	public static CoordPoint centerTmToWGS84_ACCT(double x, double y) {
		Projection 	prj = new Projection();
		Coordinate 	coord = new Coordinate(x, y);

		prj.SetSrcType(GeoEllips.kGrs80, GeoSystem.kTmMid);
		prj.SetDstType(GeoEllips.kWgs84, GeoSystem.kUtmKR);

		Coordinate 	conv1 = prj.Conv(coord);

		conv1.x	+= CommonProperties.CENTER_TM_X_DIFF;
		conv1.y	+= CommonProperties.CENTER_TM_Y_DIFF;
		prj.SetSrcType(GeoEllips.kGrs80, GeoSystem.kUtmKR);
		prj.SetDstType(GeoEllips.kWgs84, GeoSystem.kGeographic);

		Coordinate 	convR = prj.Conv(conv1);
		
		return new CoordPoint(convR.x, convR.y);
	}
	public static CoordPoint centerTmToWGS84_TRACE(double x, double y) {
		double lon = DataTypeUtil.convertCenterTmToNumber(x) - CommonProperties.WGS84_DIFF;
		double lat = DataTypeUtil.convertCenterTmToNumber(y);

		CoordPoint point = DataTypeUtil.wgs84ToCenterTmWithNoDiff(lat, lon);

		return DataTypeUtil.centerTmToWGS84PlusDiff(point.x, point.y);
	}
	
}

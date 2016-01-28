package nses.common.xml;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import nses.gis.projection.Coordinate;
import nses.gis.projection.GeoEllips;
import nses.gis.projection.GeoSystem;
import nses.gis.projection.Projection;

public class XmlResData {
	private	Map<String, Object>			m_mapData;

	//
	public XmlResData() {
		m_mapData = new HashMap<String, Object>();
	}

	// ---------------------------------------------------------------------
	// 외부에서 호출하는 함수들
	// ---------------------------------------------------------------------
	public void SetValue(String sName, Object oValue) {
		m_mapData.put(sName.toLowerCase(), oValue);
	}
	public void SetValue(String sName, String sValue) {
		m_mapData.put(sName.toLowerCase(), sValue);
	}
	public void SetValue(String sName, int nValue) {
		m_mapData.put(sName.toLowerCase(), new Integer(nValue));
	}
	public String getString(String sName) {
		String	sValue = null;
		Object	oValue = m_mapData.get(sName);

		if (oValue != null) {
			if (oValue.getClass() == String.class)
				sValue = (String)oValue; 
			else
				sValue = oValue.toString();
		}

		return sValue; 
	}
	public int getInteger(String sName) {
		int		nValue = 0;
		Object	oValue = m_mapData.get(sName);

		if (oValue != null) {
			try {
				if (oValue.getClass() == Integer.class)
					nValue = (Integer)oValue;
				else if (oValue.getClass() == String.class)
					nValue = Integer.parseInt((String)oValue);
			} catch (Exception ex) {}
		}

		return nValue; 
	}
	public int getCount() {
		return m_mapData.size();
	}
	public void clear() {
		m_mapData.clear();
	}
	public Map<String, Object> getData() {
		return m_mapData;
	}
	public void convCoord() {

		String[]	coords = getString("status_desc").split(" ");
		
		if (coords != null && coords.length == 2) {
			double		x = toDouble(coords[0]);
			double		y = toDouble(coords[1]);
			if (x > 0.0 && y > 0.0) {
				Projection 	prj = new Projection();
				prj.SetSrcType(GeoEllips.kWgs84, GeoSystem.kGeographic);
				prj.SetDstType(GeoEllips.kGrs80, GeoSystem.kUtmKR);

				Coordinate coord = new Coordinate();
				coord.x = x;
				coord.y = y;
				Coordinate convC = prj.Conv(coord);
				
				m_mapData.put("gis_x", x);
				m_mapData.put("gis_y", y);
				m_mapData.put("coord_x", convC.x);
				m_mapData.put("coord_y", convC.y);
			}
		}
	}

	@Override
	public String toString() {
		Iterator<Entry<String, Object>>		iter = m_mapData.entrySet().iterator();

		String			sRetVal = "";
		int				nInx = 0;

		while (iter.hasNext()) {
			Entry<String, Object>	ent = iter.next();
			if (nInx > 0)
				sRetVal += "&";

			sRetVal += (ent.getKey() + "=" + UrlEncode(ent.getValue().toString()));
			nInx ++;
		}

		return sRetVal;
	}
	/*
	public void toJson(JSONWriter builder) throws JSONException {
		Iterator<Entry<String, Object>>		iter = m_mapData.entrySet().iterator();

		builder.object();
		while (iter.hasNext()) {
			Entry<String, Object>	ent = iter.next();

			// System.out.println("KEY:" + ent.getKey() + ", VAL:" + ent.getValue());
			builder.key(ent.getKey()).value(ent.getValue());
		}
		builder.endObject();
	}
	*/
	
	// ---------------------------------------------------------------------
	// 내부에서 사용되는 함수들
	// ---------------------------------------------------------------------
	private String UrlEncode(String sStr) {
		String	sRet = "";
		try {
			sRet = URLEncoder.encode(sStr, "UTF-8");
		} catch(Exception e) { }
		return sRet;
	}
//	private double toDouble(String sKey) {
//		double 	dbRet = 0.0;
//		try {
//			dbRet = Double.parseDouble(m_mapData.get(sKey).toString());
//		} catch(Exception e) { }
//		return dbRet;
//	}
	private double toDouble(String sDbVal) {
		double 	dbRet = 0.0;
		try {
			dbRet = Double.parseDouble(sDbVal);
		} catch(Exception e) { }
		return dbRet;
	}
}

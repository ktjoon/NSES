package nses.common.utils;

import nses.common.coord.GeoPoint;
import nses.common.coord.GeoTrans;



public class DataConvertor {
	
	/**
	 * 좌표형식 변환
	 * @param longitude : 경도
	 * @param latitude  : 위도
	 * @param dest      : GeoTrans 
	 * @param target    : GeoTrans
	 * @return
	 */
	public static GeoPoint convertGeoTrans(Object longitude, Object latitude, int dest, int target){
		GeoPoint geoPoint = new GeoPoint(Double.parseDouble(longitude.toString()), Double.parseDouble(latitude.toString()));
		GeoPoint convertPoint = GeoTrans.convert(dest, target, geoPoint);
		
		return convertPoint;
	}
	public static GeoPoint convertGeoTrans(double x, double y, int dest, int target){
		GeoPoint geoPoint = new GeoPoint(x, y);
		return GeoTrans.convert(dest, target, geoPoint);
	}
}

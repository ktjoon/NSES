/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nses.ons.vo;

import nses.common.coord.CoordPoint;
import nses.common.coord.DataTypeUtil;
import nses.common.utils.ComStr;

/**
 * 
 * @Modification Information @ @ 수정일 수정자 수정내용 @ --------- ---------
 *               ------------------------------- @ 2014.12.23 최초생성
 * 
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2014.12.23
 * @version 1.0
 * @see Copyright (C) by NSES All right reserved.
 */
public class AccdWcarInfoVO {

	private String dsr_seq;
	private String car_id;
	private double lat;
	private double lon;
	private String stat_datetime;
	private String input_datetime;
	private String car_no;
	private String car_type_cd;
	private String car_type_name;
	private String car_stat_cd;
	private String car_stat_name;
	private String car_gis_x;
	private String car_gis_y;

	//
	private double gis_x;
	private double gis_y;
	//
	private String cam_yn = "N";
	private String cam_hostport;
	private String cam_auth_data;
	private String cam_url;
	private String cctv_seqno;

	public String getDsr_seq() {
		return dsr_seq;
	}

	public void setDsr_seq(String dsr_seq) {
		this.dsr_seq = dsr_seq;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getStat_datetime() {
		return stat_datetime;
	}

	public void setStat_datetime(String stat_datetime) {
		this.stat_datetime = stat_datetime;
	}

	public String getInput_datetime() {
		return input_datetime;
	}

	public void setInput_datetime(String input_datetime) {
		this.input_datetime = input_datetime;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public String getCar_type_cd() {
		return car_type_cd;
	}
	public void setCar_type_cd(String car_type_cd) {
		this.car_type_cd = car_type_cd;
	}

	public String getCar_type_name() {
		return car_type_name;
	}
	public void setCar_type_name(String car_type_name) {
		this.car_type_name = car_type_name;
	}


	public String getCar_stat_cd() {
		return car_stat_cd;
	}
	public void setCar_stat_cd(String car_stat_cd) {
		this.car_stat_cd = car_stat_cd;
	}

	public String getCar_stat_name() {
		return car_stat_name;
	}
	public void setCar_stat_name(String car_stat_name) {
		this.car_stat_name = car_stat_name;
	}

	public String getCar_gis_x() {
		return car_gis_x;
	}

	public void setCar_gis_x(String car_gis_x) {
		this.car_gis_x = car_gis_x;
	}

	public String getCar_gis_y() {
		return car_gis_y;
	}

	public void setCar_gis_y(String car_gis_y) {
		this.car_gis_y = car_gis_y;
	}
	
	
	//
	public double getGis_x() {
		return gis_x;
	}

	public void setGis_x(double gis_x) {
		this.gis_x = gis_x;
	}

	public double getGis_y() {
		return gis_y;
	}

	public void setGis_y(double gis_y) {
		this.gis_y = gis_y;
	}

	public String getCam_yn() {
		return cam_yn;
	}

	public void setCam_yn(String cam_yn) {
		this.cam_yn = cam_yn;
	}

	public String getCam_hostport() {
		return cam_hostport;
	}

	public void setCam_hostport(String cam_hostport) {
		this.cam_hostport = cam_hostport;
	}

	public String getCam_url() {
		return cam_url;
	}

	public void setCam_url(String cam_url) {
		this.cam_url = cam_url;
	}

	public String getCam_auth_data() {
		return cam_auth_data;
	}

	public void setCam_auth_data(String cam_auth_data) {
		this.cam_auth_data = cam_auth_data;
	}

	public String getCctv_seqno() {
		return cctv_seqno;
	}

	public void setCctv_seqno(String cctv_seqno) {
		this.cctv_seqno = cctv_seqno;
	}

	public void convertCoord() {
		CoordPoint pt;
		
		if (car_gis_x != null && car_gis_y != null) {
			pt = DataTypeUtil.centerTmToWGS84_TRACE(ComStr.toDouble(car_gis_x), ComStr.toDouble(car_gis_y));
		} else {
			pt = DataTypeUtil.centerTmToWGS84_NSES(lat, lon);
		}
		
		gis_x = pt.x;
		gis_y = pt.y;
	}
	public boolean isEmpty_Car_no() {
		return ComStr.isEmpty(car_no);
	}

}

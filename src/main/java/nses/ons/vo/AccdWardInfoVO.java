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


/**
 * 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.12.23              최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2014.12.23
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSES All right reserved.
 */
public class AccdWardInfoVO {
	
	private String			dsr_seq; 
	private String			ward_id;
	private String			ward_name;
	private	double			gis_x;				// 경도 LNG
	private	double			gis_y;				// 위도 LAT
	
	
	
	public String getDsr_seq() {
		return dsr_seq;
	}
	public void setDsr_seq(String dsr_seq) {
		this.dsr_seq = dsr_seq;
	}
	public String getWard_id() {
		return ward_id;
	}
	public void setWard_id(String ward_id) {
		this.ward_id = ward_id;
	}

	public String getWard_name() {
		return ward_name;
	}
	public void setWard_name(String ward_name) {
		this.ward_name = ward_name;
	}
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
	public void convertCoord() {
		CoordPoint p = DataTypeUtil.centerTmToWGS84_NSES(gis_x, gis_y);

		gis_x = p.x;
		gis_y = p.y;
	}
}

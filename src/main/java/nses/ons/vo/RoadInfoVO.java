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
public class RoadInfoVO {
	
	private	int				road_seq;
	private	String			road_title;
	private	double			poi_x;			// UTMK-X
	private	double			poi_y;			// UTMK-Y
	private int				degree_type;	// 통제정도
	private	String			road_type;
	private	String			road_start_dt;
	private	String			road_end_dt;
	private	String			reg_dt;
	private	String			use_stat;
	private	String			user_id;
	
	
	
	public int getDegree_type() {
		return degree_type;
	}
	public void setDegree_type(int degree_type) {
		this.degree_type = degree_type;
	}
	public int getRoad_seq() {
		return road_seq;
	}
	public void setRoad_seq(int road_seq) {
		this.road_seq = road_seq;
	}
	public String getRoad_title() {
		return road_title;
	}
	public void setRoad_title(String road_title) {
		this.road_title = road_title;
	}
	public double getPoi_x() {
		return poi_x;
	}
	public void setPoi_x(double poi_x) {
		this.poi_x = poi_x;
	}
	public double getPoi_y() {
		return poi_y;
	}
	public void setPoi_y(double poi_y) {
		this.poi_y = poi_y;
	}
	public String getRoad_type() {
		return road_type;
	}
	public void setRoad_type(String road_type) {
		this.road_type = road_type;
	}
	public String getRoad_start_dt() {
		return road_start_dt;
	}
	public void setRoad_start_dt(String road_start_dt) {
		this.road_start_dt = road_start_dt;
	}
	public String getRoad_end_dt() {
		return road_end_dt;
	}
	public void setRoad_end_dt(String road_end_dt) {
		this.road_end_dt = road_end_dt;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getUse_stat() {
		return use_stat;
	}
	public void setUse_stat(String use_stat) {
		this.use_stat = use_stat;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}

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
public class CarOwnerVO {
	//
	private String	own_car_no;
	private String	own_name;
	private String	tel_no;
	private String	reg_dt;
	private String	mod_dt;
	private String	sch_dt;
	private int		use_stat;
	
	public String getOwn_car_no() {
		return own_car_no;
	}
	public void setOwn_car_no(String own_car_no) {
		this.own_car_no = own_car_no;
	}
	public String getOwn_name() {
		return own_name;
	}
	public void setOwn_name(String own_name) {
		this.own_name = own_name;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getMod_dt() {
		return mod_dt;
	}
	public void setMod_dt(String mod_dt) {
		this.mod_dt = mod_dt;
	}
	public int getUse_stat() {
		return use_stat;
	}
	public void setUse_stat(int use_stat) {
		this.use_stat = use_stat;
	}
	public String getSch_dt() {
		return sch_dt;
	}
	public void setSch_dt(String sch_dt) {
		this.sch_dt = sch_dt;
	}
	
}

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
public class AccidentInfoVO {
	
	private String 			dsr_seq;
	private	String			dsr_knd_cd;
	private	String			dsr_knd_name;		// 재난유형명: 화재, 구조, 구급, 기타
	private	String			dsr_cls_cd;
	private	String			dsr_cls_name;		// 긴급구조분류명
	private	String			point_accr_cls;
	private	String			point_accr_name;	// 재난위치유형
	private	String			reg_user_id;
	private	String			reg_user_name;
	private	String			reg_dtime;
	private	String			call_name;
	private	String			call_tel;
	private	String			radio_name;
	private	double			gis_x;				// 경도 LNG
	private	double			gis_y;				// 위도 LAT
	private	String			dsr_addr;
	private	String			call_content;
	private	String			proc_cd;
	private	String			acc_stat;
	
	
	public String getDsr_seq() {
		return dsr_seq;
	}
	public void setDsr_seq(String dsr_seq) {
		this.dsr_seq = dsr_seq;
	}
	public String getDsr_knd_cd() {
		return dsr_knd_cd;
	}
	public void setDsr_knd_cd(String dsr_knd_cd) {
		this.dsr_knd_cd = dsr_knd_cd;
	}
	public String getDsr_knd_name() {
		return dsr_knd_name;
	}
	public void setDsr_knd_name(String dsr_knd_name) {
		this.dsr_knd_name = dsr_knd_name;
	}
	public String getDsr_cls_cd() {
		return dsr_cls_cd;
	}
	public void setDsr_cls_cd(String dsr_cls_cd) {
		this.dsr_cls_cd = dsr_cls_cd;
	}
	public String getDsr_cls_name() {
		return dsr_cls_name;
	}
	public void setDsr_cls_name(String dsr_cls_name) {
		this.dsr_cls_name = dsr_cls_name;
	}
	public String getPoint_accr_cls() {
		return point_accr_cls;
	}
	public void setPoint_accr_cls(String point_accr_cls) {
		this.point_accr_cls = point_accr_cls;
	}
	public String getPoint_accr_name() {
		return point_accr_name;
	}
	public void setPoint_accr_name(String point_accr_name) {
		this.point_accr_name = point_accr_name;
	}
	public String getReg_user_id() {
		return reg_user_id;
	}
	public void setReg_user_id(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public String getReg_user_name() {
		return reg_user_name;
	}
	public void setReg_user_name(String reg_user_name) {
		this.reg_user_name = reg_user_name;
	}
	public String getReg_dtime() {
		return reg_dtime;
	}
	public void setReg_dtime(String reg_dtime) {
		this.reg_dtime = reg_dtime;
	}
	public String getCall_name() {
		return call_name;
	}
	public void setCall_name(String call_name) {
		this.call_name = call_name;
	}
	public String getCall_tel() {
		return call_tel;
	}
	public void setCall_tel(String call_tel) {
		this.call_tel = call_tel;
	}
	public String getRadio_name() {
		return radio_name;
	}
	public void setRadio_name(String radio_name) {
		this.radio_name = radio_name;
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
	public String getDsr_addr() {
		return dsr_addr;
	}
	public void setDsr_addr(String dsr_addr) {
		this.dsr_addr = dsr_addr;
	}
	public String getCall_content() {
		return call_content;
	}
	public void setCall_content(String call_content) {
		this.call_content = call_content;
	}
	public String getProc_cd() {
		return proc_cd;
	}
	public void setProc_cd(String proc_cd) {
		this.proc_cd = proc_cd;
	}
	public String getAcc_stat() {
		return acc_stat;
	}
	public void setAcc_stat(String acc_stat) {
		this.acc_stat = acc_stat;
	}

	public void convertCoord() {
		CoordPoint p = DataTypeUtil.centerTmToWGS84_ACCT(gis_x, gis_y);

		gis_x = p.x;
		gis_y = p.y;
	}
}

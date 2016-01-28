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
public class MsgInfoVO {
	private int 	msg_seq;
	private String	car_id;
	private String	car_no;
	private String	msg_type;
	private String	msg_contents;
	private String	msg_extra;
	private String	cctv_type;
	private String	cctv_dbkey;
	private String	snd_dt;
	private String	snd_stat;
	private String	reg_dt;
	private String	user_id;
	private String	dsr_seq;
	private String	img_url;
	
	
	public int getMsg_seq() {
		return msg_seq;
	}
	public void setMsg_seq(int msg_seq) {
		this.msg_seq = msg_seq;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getMsg_contents() {
		return msg_contents;
	}
	public void setMsg_contents(String msg_contents) {
		this.msg_contents = msg_contents;
	}
	public String getMsg_extra() {
		return msg_extra;
	}
	public void setMsg_extra(String msg_extra) {
		this.msg_extra = msg_extra;
	}
	public String getCctv_type() {
		return cctv_type;
	}
	public void setCctv_type(String cctv_type) {
		this.cctv_type = cctv_type;
	}
	public String getCctv_dbkey() {
		return cctv_dbkey;
	}
	public void setCctv_dbkey(String cctv_dbkey) {
		this.cctv_dbkey = cctv_dbkey;
	}
	public String getSnd_dt() {
		return snd_dt;
	}
	public void setSnd_dt(String snd_dt) {
		this.snd_dt = snd_dt;
	}
	public String getSnd_stat() {
		return snd_stat;
	}
	public void setSnd_stat(String snd_stat) {
		this.snd_stat = snd_stat;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	//
	public String getDsr_seq() {
		return dsr_seq;
	}
	public void setDsr_seq(String dsr_seq) {
		this.dsr_seq = dsr_seq;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	
	
	
	
	
	
}

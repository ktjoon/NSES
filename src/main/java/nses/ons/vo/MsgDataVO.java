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
public class MsgDataVO {

	private int 	msg_seq;
	private int 	cur_state;
	private String 	req_date;
	private String 	call_to;
	private String 	call_from;
	private String 	sms_txt;
	private int 	msg_type;
	public int getMsg_seq() {
		return msg_seq;
	}
	public void setMsg_seq(int msg_seq) {
		this.msg_seq = msg_seq;
	}
	public int getCur_state() {
		return cur_state;
	}
	public void setCur_state(int cur_state) {
		this.cur_state = cur_state;
	}
	public String getReq_date() {
		return req_date;
	}
	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}
	public String getCall_to() {
		return call_to;
	}
	public void setCall_to(String call_to) {
		this.call_to = call_to;
	}
	public String getCall_from() {
		return call_from;
	}
	public void setCall_from(String call_from) {
		this.call_from = call_from;
	}
	public String getSms_txt() {
		return sms_txt;
	}
	public void setSms_txt(String sms_txt) {
		this.sms_txt = sms_txt;
	}
	public int getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}
	
	
}

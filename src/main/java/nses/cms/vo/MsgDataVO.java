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
package nses.cms.vo;

import nses.common.utils.ComStr;
import nses.common.vo.BasePageVO;


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
public class MsgDataVO extends BasePageVO{
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = -4652143275962860910L;
	
	//
	private String	msg_seq;
	private String	cur_state;
	private String	req_date;
	private String	call_to;
	private String	call_from;
	private String	sms_txt;
	private String	msg_type;
	public String getMsg_seq() {
		return msg_seq;
	}
	public void setMsg_seq(String msg_seq) {
		this.msg_seq = msg_seq;
	}
	public String getCur_state() {
		return cur_state;
	}
	public void setCur_state(String cur_state) {
		String sCurStat	= "";
		switch (ComStr.toInt(msg_type)) {
		case 0 :
			sCurStat	= "발송요청";
			break;
		case 1 :
			sCurStat	= "전송중";
			break;
		case 2 :
			sCurStat	= "전송";
			break;
		case 3 :
			sCurStat	= "결과수신";
			break;
		}
		
		this.cur_state = sCurStat;
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
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		String sMsgType	= "";
		
		switch (ComStr.toInt(msg_type)) {
		case 4 :
			sMsgType	= "SMS";
			break;
		case 5 :
			sMsgType	= "URL";
			break;
		case 6 :
			sMsgType	= "MMS";
			break;
		}
		this.msg_type = sMsgType;
	}
	
	
}
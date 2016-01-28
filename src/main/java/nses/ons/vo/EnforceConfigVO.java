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
public class EnforceConfigVO {
	private String	enforce_time;
	private String	msg_contents; 
	private	String	org_user_tel;
	private String	sender_tel;
	private int		msg_auto_send;
	
	private int		crack_stat;
	private String	enforce_time_over;
	
	public String getMsg_contents() {
		return msg_contents;
	}
	public void setMsg_contents(String msg_contents) {
		this.msg_contents = msg_contents;
	}
	public String getOrg_user_tel() {
		return org_user_tel;
	}
	public void setOrg_user_tel(String org_user_tel) {
		this.org_user_tel = org_user_tel;
	}
	
	public String getSender_tel() {
		return sender_tel;
	}
	public void setSender_tel(String sender_tel) {
		this.sender_tel = sender_tel;
	}
	public String getEnforce_time() {
		return enforce_time;
	}
	public void setEnforce_time(String enforce_time) {
		this.enforce_time = enforce_time;
	}
	public int getCrack_stat() {
		return crack_stat;
	}
	public void setCrack_stat(int crack_stat) {
		this.crack_stat = crack_stat;
	}
	public String getEnforce_time_over() {
		return enforce_time_over;
	}
	public void setEnforce_time_over(String enforce_time_over) {
		this.enforce_time_over = enforce_time_over;
	}
	public int getMsg_auto_send() {
		return msg_auto_send;
	}
	public void setMsg_auto_send(int msg_auto_send) {
		this.msg_auto_send = msg_auto_send;
	}
	
	
}

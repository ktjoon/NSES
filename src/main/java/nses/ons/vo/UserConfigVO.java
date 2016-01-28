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
public class UserConfigVO {
	//
	private	String		user_id;
	private	String		sch_type;
	private	String		cctv_mat;
	private	String		login_dt;
	//
	public	int			save_sec;
	

	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getSch_type() {
		return sch_type;
	}
	public void setSch_type(String sch_type) {
		this.sch_type = sch_type;
	}
	public String getCctv_mat() {
		return cctv_mat;
	}
	public void setCctv_mat(String cctv_mat) {
		this.cctv_mat = cctv_mat;
	}
	public String getLogin_dt() {
		return login_dt;
	}
	public void setLogin_dt(String login_dt) {
		this.login_dt = login_dt;
	}
	public int getSave_sec() {
		return save_sec;
	}
	public void setSave_sec(int save_sec) {
		this.save_sec = save_sec;
	}
}

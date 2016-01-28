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

import java.util.ArrayList;
import java.util.List;


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
public class RecogImgVO {
	
	private List<?> 	items;
	
	private String		dsr_seq;
	private String		own_car_no;
	private String		enforce_stat;
	private String		enforce_dt;	
	private String		user_id;
	private String		reg_dt;
	
	private int			recog_flag;			// 1: 저장, 2 : 단속처리, 3 : 메세지 전송
	
	public RecogImgVO() {
		if (items == null)
			items = new ArrayList<RecogDetailVO>();
	}

	public String getDsr_seq() {
		return dsr_seq;
	}
	public void setDsr_seq(String dsr_seq) {
		this.dsr_seq = dsr_seq;
	}
	public String getOwn_car_no() {
		return own_car_no;
	}
	public void setOwn_car_no(String own_car_no) {
		this.own_car_no = own_car_no;
	}
	public String getEnforce_stat() {
		return enforce_stat;
	}
	public void setEnforce_stat(String enforce_stat) {
		this.enforce_stat = enforce_stat;
	}
	public String getEnforce_dt() {
		return enforce_dt;
	}
	public void setEnforce_dt(String enforce_dt) {
		this.enforce_dt = enforce_dt;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public List<?> getItems() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;
	}
	
	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}

	public int getRecog_flag() {
		return recog_flag;
	}
	public void setRecog_flag(int recog_flag) {
		this.recog_flag = recog_flag;
	}
	
}

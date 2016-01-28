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

import nses.common.vo.BaseResVO;


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
public class RecogVO extends BaseResVO{
	
	private static final long serialVersionUID = 3358280218558479801L;
	
	private String		own_car_no;
	private String		own_name;
	private String		tel_no;
	private String		sch_dt;

	private RecogImgVO	itemVO;

	private String		enforce_time;					// EnforceConfigVO 단속타임 
	private String		enforce_time_over;
	private int			crack_stat;						// 0: 단속불가,	1: 단속가능
	private int			msg_auto_send;
	
	
	public RecogVO() {
		if (itemVO == null)
			itemVO	= new RecogImgVO();
	}
	
	public int getMsg_auto_send() {
		return msg_auto_send;
	}
	public void setMsg_auto_send(int msg_auto_send) {
		this.msg_auto_send = msg_auto_send;
	}
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
	public String getSch_dt() {
		return sch_dt;
	}
	public void setSch_dt(String sch_dt) {
		this.sch_dt = sch_dt;
	}
	public RecogImgVO getItemVO() {
		return itemVO;
	}
	public void setItemVO(RecogImgVO itemVO) {
		this.itemVO = itemVO;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

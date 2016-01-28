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
public class OwnerHistVO {
	//
	public static final String		INSERT 	= "I";
	public static final String		UPDATE 	= "U";
	public static final String		DELETE 	= "D";
	public static final String		SELECT 	= "S";
	public static final String		VIEW 	= "V";

	//
	private int 	own_seq;
	private String	acc_kind;
	private String	acc_name;
	private String	own_car_no;
	private String	reg_dt;
	private String	user_id;
	
	//
	public int getOwn_seq() {
		return own_seq;
	}
	public void setOwn_seq(int own_seq) {
		this.own_seq = own_seq;
	}
	public String getAcc_kind() {
		return acc_kind;
	}
	public void setAcc_kind(String acc_kind) {
		this.acc_kind = acc_kind;
	}
	public String getAcc_name() {
		return acc_name;
	}
	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}
	public String getOwn_car_no() {
		return own_car_no;
	}
	public void setOwn_car_no(String own_car_no) {
		this.own_car_no = own_car_no;
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
	
	public static OwnerHistVO createVO(CarOwnerVO vo, String accKind, String sUserId) {
		OwnerHistVO		retVO = new OwnerHistVO();
		
		retVO.setAcc_kind(accKind);
		if (vo != null)
			retVO.setOwn_car_no(vo.getOwn_car_no());
		retVO.setUser_id(sUserId);
		return retVO;
	}
}

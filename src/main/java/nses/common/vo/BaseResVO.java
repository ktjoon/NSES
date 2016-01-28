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
package nses.common.vo;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.context.MessageSource;

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
public class BaseResVO implements Serializable {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 2514788582956612731L;
	
	//
	public static final int		RET_OK				= 200;
	
	//
	public static final int		ERR_APP_WEBCOMMON		= 601;
	public static final int		ERR_APP_EXCEPTION		= 602;
	public static final int		ERR_DB_EXCEPTION		= 603;
	public static final int		ERR_DATA_NOT_FOUND		= 604;
	public static final int		ERR_SESSION_NOT_EXIST	= 605;
	public static final int		ERR_DB_DUPLICATE_KEY	= 606;
	//
	public static final int		ERR_ID_NOT_FOUND		= 611;
	public static final int		ERR_PWD_NOT_MATCH		= 612;

	//
	public static final int		ERR_CCTV_CAPTURE_FAIL	= 620;
	public static final int		ERR_CARNO_DETECT_FAIL	= 620;
	public static final int		ERR_REQ_CAPTURE_FAIL	= 621;
	public static final int		ERR_REQ_RECORD_FAIL		= 622;
	public static final int		ERR_REQ_RECORD_EXIST	= 623;
	
	public static final int		ERR_INSERT_FAIL			= 631;
	public static final int		ERR_UPDATE_FAIL			= 632;
	public static final int		ERR_DELETE_FAIL			= 633;
	
	// RecogImg
	public static final int		ERR_RECOG_CARNO_NOT_FOUND	= 641;
	
	
	//
	private	int				retCode;
	private	String			retMsg;
	
	//
	public boolean isRetCode() {
		return retCode == RET_OK;
	}

	//
	public int getRetCode() {
		return retCode;
	}
	public void setRetCode(int retCode) {
		setRetCode(retCode, null);
	}
	public void setRetCode(int retCode, MessageSource messageSource) {
		this.retCode = retCode;
		
		if (this.retCode == RET_OK) {
			retMsg = "OK";
		}
		else {
			if (messageSource == null) {
				retMsg = "에러가 발생하였습니다 - 에러코드:" + this.retCode;
			}
			else {
				try {
					String	sMsgCode = "errmsg.rcode-" + retCode;
					retMsg = messageSource.getMessage(sMsgCode, null, Locale.getDefault());
				} catch(Exception e) {
					retMsg = "에러가 발생하였습니다 - 에러코드:" + this.retCode;
				}
			}
		}
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
}

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
public class ResultVO extends BaseResVO {
	
    private static final long serialVersionUID = 1L;
    
    /** 데이터카운트 */
    private int		rowCount 	= 0;
    private String	resKey;
    private String	extraData;
    
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	public String getResKey() {
		return resKey;
	}
	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public String getExtraData() {
		return extraData;
	}
	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	//
	public static ResultVO create(int nRetCode) {
		ResultVO	vo = new ResultVO();
		vo.setRetCode(nRetCode);
		return vo;
	}
	public static ResultVO create(int nRetCode, MessageSource messageSource) {
		ResultVO	vo = new ResultVO();
		vo.setRetCode(nRetCode, messageSource);
		return vo;
	}

}

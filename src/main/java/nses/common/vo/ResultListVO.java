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

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;

import nses.common.utils.ComDate;
import nses.ons.vo.RoadInfoVO;

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
public class ResultListVO extends BaseResVO {
	
	private static final long serialVersionUID = 1L;
    
	/** 데이터카운트 */
	private	List<?>		items;
	private	String		curr_date;
    
	public ResultListVO() {
		items		= new ArrayList<RoadInfoVO>();
		curr_date	= ComDate.makeDispDateTimeString();
	}
	
	public List<?> getItems() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;

		if (this.items == null)
			this.items = new ArrayList<String>();
	}

	public String getCurr_date() {
		return curr_date;
	}
	public void setCurr_date(String curr_date) {
		this.curr_date = curr_date;
	}

	//
	public static ResultListVO create(int nRetCode) {
		ResultListVO	vo = new ResultListVO();
		vo.setRetCode(nRetCode);
		return vo;
	}
	public static ResultListVO create(int nRetCode, MessageSource messageSource) {
		ResultListVO	vo = new ResultListVO();
		vo.setRetCode(nRetCode, messageSource);
		return vo;
	}
}

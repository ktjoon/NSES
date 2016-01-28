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

import nses.common.vo.BaseResVO;

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
public class RtsaResVO extends BaseResVO {
	
	private static final long serialVersionUID = 5155565289794945874L;

	/** 데이터카운트 */
	private	String		car_no;
	private	List<?>		routes;

	
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public List<?> getRoutes() {
		return routes;
	}
	public void setRoutes(List<?> routes) {
		this.routes = routes;
		
		if (this.routes == null)
			this.routes = new ArrayList<String>();
	}

	//
	public static RtsaResVO create(int nRetCode) {
		RtsaResVO	vo = new RtsaResVO();
		vo.setRetCode(nRetCode);
		return vo;
	}
	public static RtsaResVO create(int nRetCode, MessageSource messageSource) {
		RtsaResVO	vo = new RtsaResVO();
		vo.setRetCode(nRetCode, messageSource);
		return vo;
	}
}

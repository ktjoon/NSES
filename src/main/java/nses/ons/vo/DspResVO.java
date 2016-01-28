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
public class DspResVO extends BaseResVO {
	
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 4750235491697626753L;

	private	List<?>		wards;
	private	List<?>		wcars;
	
	public DspResVO() {
		if (wards == null)
			wards	= new ArrayList<AccdWardInfoVO>();
		if (wcars == null)
			wcars	= new ArrayList<AccdWcarInfoVO>();
	}

	public List<?> getWards() {
		return wards;
	}

	public void setWards(List<?> wards) {
		this.wards = wards;
	}

	public List<?> getWcars() {
		return wcars;
	}

	public void setWcars(List<?> wcars) {
		this.wcars = wcars;
	}
	
	//
	//
	public static DspResVO create(int nRetCode) {
		DspResVO	vo = new DspResVO();
		vo.setRetCode(nRetCode);
		return vo;
	}
}

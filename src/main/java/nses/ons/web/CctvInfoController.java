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
package nses.ons.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.common.utils.ComUtils;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultListVO;
import nses.ons.service.CctvInfoService;
import nses.ons.vo.CctvInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

@Controller("onsCctvInfoController")
public class CctvInfoController {

	private static final Logger 	logger = LoggerFactory.getLogger(CctvInfoController.class);

	/** CctvInfoService */
	@Resource(name = "cctvInfoService")
	private CctvInfoService cctvInfoService;

	/** Validator */
//	@Resource(name = "beanValidator")
//	protected DefaultBeanValidator beanValidator;


	@RequestMapping("/ons/cctv/list_ajax.do")
	public void cctvInfolistAjax( @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/cctv/list_ajax.do");
		CctvInfoVO cctvVO	= new CctvInfoVO();
		
		int				nResCode = BaseResVO.ERR_DATA_NOT_FOUND;
		List<?>			lstData	= cctvInfoService.selectListData(cctvVO);
		ResultListVO	resVO;
		
		if (lstData != null)
			nResCode = BaseResVO.RET_OK;

		resVO = ResultListVO.create(nResCode);
		resVO.setItems(lstData);
		
		ComUtils.responseJson(request, response, resVO);
	}

}

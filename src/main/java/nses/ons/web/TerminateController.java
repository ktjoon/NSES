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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.vo.BaseResVO;
import nses.ons.service.TerminateService;
import nses.ons.vo.RecogImgVO;
import nses.ons.vo.RecogVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

@Controller
public class TerminateController {

	private static final Logger 	logger = LoggerFactory.getLogger(TerminateController.class);

	
	@Resource(name = "terminateService")
	private TerminateService terminateService;

	@Autowired
	private	MessageSource messageSource;
	
	@RequestMapping("/ons/terminate/list_ajax.do")
	public void terminatelistAction(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/terminate/list_ajax.do");
		RecogVO recogVO			= new RecogVO();
		RecogImgVO recogImgVO	= new RecogImgVO();
		
		recogImgVO.setDsr_seq(ComStr.toStr(params.get("dsr_seq")));
		
		List<?> 		lstTerminateDetail = new ArrayList<RecogImgVO>();
		
		lstTerminateDetail = terminateService.selectListData(recogImgVO);
		
		recogImgVO.setItems(lstTerminateDetail);
		recogVO.setItemVO(recogImgVO);
		
		recogVO.setRetCode(BaseResVO.RET_OK, messageSource);
		
		
		ComUtils.responseJson(request, response, recogVO);
	}
	

}

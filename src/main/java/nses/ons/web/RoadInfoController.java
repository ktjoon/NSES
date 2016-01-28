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

import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.session.UserSessInfo;
import nses.common.utils.ComUtils;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultListVO;
import nses.common.vo.ResultVO;
import nses.ons.service.RoadInfoService;
import nses.ons.vo.RoadInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class RoadInfoController {

	private static final Logger 	logger = LoggerFactory.getLogger(RoadInfoController.class);

	/** OnsMainService */
	@Resource(name = "roadInfoService")
	private RoadInfoService roadInfoService;

	/** Validator */
//	@Resource(name = "beanValidator")
//	protected DefaultBeanValidator beanValidator;


	@RequestMapping("/ons/road/list_ajax.do")
	public void roadInfolistAjax( @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/road/list_ajax.do");
		RoadInfoVO roadVO	= new RoadInfoVO();
		
		int				nResCode = BaseResVO.ERR_DATA_NOT_FOUND;
		List<?>			lstData	= roadInfoService.selectListData(roadVO);
		ResultListVO	resVO;
		
		if (lstData != null)
			nResCode = BaseResVO.RET_OK;

		resVO = ResultListVO.create(nResCode);
		resVO.setItems(lstData);
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	@RequestMapping("/ons/road/reg_action.do")
	public void roadInfoRegAction(@ModelAttribute("roadVO") RoadInfoVO roadVO, @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/road/reg_action.do");
		
		ResultVO resVO 		= null;
		
		/* add by ktjoon 2016-01 */		
		BaseSessInfo info	= SessionUtility.getLoginForUser(request);
		
		if (info == null) {
			//return "redirect:/ons/login.do";
			UserSessInfo	us = new UserSessInfo();			
			us.setUserId("admin");
			us.setUserName("1111");
			SessionUtility.setLoginForUser(request, us);
			
			info	= SessionUtility.getLoginForUser(request);
		}	
		
		roadVO.setUser_id(info.getUserId());
		resVO	= roadInfoService.insertData(roadVO);
		
		ComUtils.responseJson(request, response, resVO);
	}

	@RequestMapping("/ons/road/del_action.do")
	public void roadInfoDelAction(@ModelAttribute("roadVO") RoadInfoVO roadVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/road/del_action.do");
		
		ResultVO resVO 		= null;
		
		resVO		= roadInfoService.updateData(roadVO);
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	@RequestMapping("/ons/road/clear_action.do")
	public void roadInfoClearAction(@ModelAttribute("roadVO") RoadInfoVO roadVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/road/clear_action.do");
		
		ResultVO resVO 		= null;
		
		resVO		= roadInfoService.clearData(roadVO);
		
		ComUtils.responseJson(request, response, resVO);
	}
	

}

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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.session.UserSessInfo;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultListVO;
import nses.common.vo.ResultVO;
import nses.ons.service.MsgInfoService;
import nses.ons.vo.MsgInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
public class MsgInfoController {

	private static final Logger 	logger = LoggerFactory.getLogger(MsgInfoController.class);

	/** OnsMainService */
	@Resource(name = "msgInfoService")
	private MsgInfoService msgInfoService;

	/** Validator */
//	@Resource(name = "beanValidator")
//	protected DefaultBeanValidator beanValidator;
	
	@Autowired
	private	MessageSource messageSource;

	@RequestMapping("/ons/carmsg/list_ajax.do")
	public void loadInfolistAjax(@ModelAttribute("msgVO") MsgInfoVO msgVO, @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/carmsg/list_ajax.do");
		//MsgInfoVO msgVO	= new MsgInfoVO();
		
		int nResCode	= BaseResVO.ERR_DATA_NOT_FOUND;
		List<?> lstData		= msgInfoService.selectListData(msgVO);
		
		ResultListVO	resVO;
		
		if (lstData != null)
			nResCode = BaseResVO.RET_OK;

		resVO = ResultListVO.create(nResCode);
		resVO.setItems(lstData);
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	// add by ktjoon 2016-01
	@RequestMapping("/ons/carmsg/smsList_ajax.do")
	public void loadSmsListAjax(@ModelAttribute("msgVO") MsgInfoVO msgVO,  @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/carmsg/smsList_ajax.do");
		//MsgInfoVO msgVO	= new MsgInfoVO();
		
		int nResCode	= BaseResVO.ERR_DATA_NOT_FOUND;
		List<?> lstData		= msgInfoService.selectSMSList(msgVO);
		
		ResultListVO	resVO;
		
		if (lstData != null)
			nResCode = BaseResVO.RET_OK;
		resVO = ResultListVO.create(nResCode);
		resVO.setItems(lstData);
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	@RequestMapping("/ons/carmsg/reg_action.do")
	public void loadInfoRegAction(@ModelAttribute("msgVO") MsgInfoVO msgVO, @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/carmsg/reg_action.do");
		
		final int TYPE_CCTV						= 1;
		final int TYPE_CCTV_CARCAM				= 3;
		
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
		
		/*if (info == null) {
			resVO	= ResultVO.create(ResultVO.ERR_SESSION_NOT_EXIST, messageSource);
			ComUtils.responseJson(request, response, resVO);
			return;
		}*/
		
		msgVO.setUser_id(info.getUserId());
		
		if (ComStr.toInt(msgVO.getMsg_type()) == 1) {
			resVO	= msgInfoService.insertData(msgVO);
			
		} else if (ComStr.toInt(msgVO.getMsg_type()) == 2) {
			
			if (ComStr.toInt(msgVO.getCctv_type()) == TYPE_CCTV) {
				String sCctvKey	= ComStr.toStr(params.get("cctv_key"));

				msgVO.setCctv_dbkey(sCctvKey);
				
			} else if (ComStr.toInt(msgVO.getCctv_type()) == TYPE_CCTV_CARCAM) {
				msgVO.setCctv_dbkey(msgVO.getCar_no());
			} 
			
			resVO	= msgInfoService.insertCctvData(msgVO);
		}
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	@RequestMapping("/ons/carmsg/updateMessageList.do")
	public void updateMessageList(@ModelAttribute("msgVO") MsgInfoVO msgVO, @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("updateMessageList ============> ");
		ResultVO resVO 		= null;
		
		//String msg_seq	= ComStr.toStr(params.get("msg_seq"));
		String[] arr_msg_seq	= request.getParameterValues("arr_msg_seq[]");
		//System.out.println("arr_msg_seq ============ "+ Arrays.toString(arr_msg_seq));
		//msgVO.setMsg_seq(msg_seq);
		
		String msg_seq = "";
		//msg_seq = Arrays.toString(arr_msg_seq);
		for(int i=0; i<arr_msg_seq.length; i++) {
			msg_seq += "'" + arr_msg_seq[i] + "',";
		}
		//System.out.println("before msg_seq ============ "+ msg_seq);
		msg_seq = msg_seq.substring(0, msg_seq.length()-1);
		//System.out.println("after msg_seq ============ "+ msg_seq);
		
		msgVO.setArr_msgseq(msg_seq);
		
		int nRes	= 0;
		nRes	= msgInfoService.updateMessageList(msgVO);
				
		ComUtils.responseJson(request, response, resVO);
	}

	
	

}

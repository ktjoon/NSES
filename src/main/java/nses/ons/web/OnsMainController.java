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

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nses.common.WebConstants;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.session.UserSessInfo;
import nses.common.utils.ComCrypto;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultVO;
import nses.ons.service.OnsMainService;
import nses.ons.vo.LoginVO;
import nses.ons.vo.UserConfigVO;
import nses.ons.vo.UserInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.fdl.cryptography.EgovARIACryptoService;
import egovframework.rte.fdl.property.EgovPropertyService;

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
public class OnsMainController {

	private static final Logger 	logger = LoggerFactory.getLogger(OnsMainController.class);

	/** OnsMainService */
	@Resource(name = "onsMainService")
	private OnsMainService onsMainService;

	/** Validator */
//	@Resource(name = "beanValidator")
//	protected DefaultBeanValidator beanValidator;

	@Autowired
	private	MessageSource messageSource;
	
	@Resource(name="ARIACryptoService")
	private EgovARIACryptoService cryptoService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 로그인 화면을 보여준다.
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping("/ons/login.do")
	public String loginForm() throws Exception {
		logger.info("login.do");
		return "ons/login";
	}

	/**
	 * 로그인을 처리한다.
	 * @param loginVO - 사용자 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("resultVO") - 결과 정보
	 * @exception Exception
	 */
	@RequestMapping("/ons/login_action.do")
	public void loginAction(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("login_action.do");
		LoginVO		reqVO = new LoginVO();
		ResultVO	resVO = null;

		reqVO.setUser_id(params.get("user_id").toString());
		reqVO.setUser_pwd(ComCrypto.encode(cryptoService, params.get("user_pwd").toString()));
		
		UserInfoVO	retVO = onsMainService.selectUserLogin(reqVO);

		if (retVO == null) {
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND, messageSource);
		}
		else if (retVO.isUserAuth(reqVO.getUser_pwd()) == false) {
			resVO = ResultVO.create(BaseResVO.ERR_PWD_NOT_MATCH, messageSource);
		}
		else {
			resVO = ResultVO.create(BaseResVO.RET_OK);
		}

		if (resVO.isRetCode()) {
			UserSessInfo	info = new UserSessInfo();
			SessionUtility.setTimeOut(request, WebConstants.USER_SESSION_TIMEOUT);

			logger.info("user_id   : " + retVO.getUser_id());
			logger.info("user_name : " + retVO.getUser_name());
			
			info.setUserId(retVO.getUser_id());
			info.setUserName(retVO.getUser_name());
			
			SessionUtility.setLoginForUser(request, info);
			
			//
			int nRes	= 0;
			UserConfigVO vo	= new UserConfigVO();
			vo.setUser_id(retVO.getUser_id());
			
			nRes	= onsMainService.updateLoginData(vo);
			
			if (nRes <= 0) {
				onsMainService.insertConfigData(vo);
			}
		}
		
		ComUtils.responseJson(request, response, resVO);
	}

	/**
	 * 로그아웃 처리를 수행한다.
	 * @return "ons/login"
	 * @exception Exception
	 */
	@RequestMapping("/ons/logout.do")
	public String logoutForm(HttpServletRequest request) throws Exception {
		logger.info("logout.do");

		SessionUtility.clearLoginForUser(request);

		return "redirect:/ons/login.do";
	}

	/**
	 * 메인 화면을 보여준다.
	 * @param model
	 * @return "ons/main"
	 * @exception Exception
	 */
	@RequestMapping("/ons/main.do")
	public String mainForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("/ons/main.do");
		
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
		
		
		UserConfigVO vo		= new UserConfigVO();
		
		vo.setUser_id(info.getUserId());
		vo = onsMainService.selectConfigData(vo);
		
		vo.save_sec	= onsMainService.selectRecSaveData();
		
		if (vo.save_sec <= 0)
			vo.save_sec = 60;
		
		model.addAttribute("userConfigVO", vo);
		
		return "ons/main";
	}
	
	@RequestMapping("/ons/manual.do")
	public String manualForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("/ons/manual.do");
		
		return "ons/manual";
	}

	@RequestMapping(value="/ons/login_action2.do", method=RequestMethod.POST)
	public void loginAction2(@RequestBody String vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("VO : " + vo);
//		LoginVO		reqVO = new LoginVO();
		ResultVO	resVO = new ResultVO();

		
//		reqVO.setUser_id(vo.getUser_id());
//		reqVO.setUser_pwd(vo.getUser_pwd());
//		reqVO.setUser_id(params.get("user_id").toString());
//		reqVO.setUser_pwd(params.get("user_pwd").toString());

//		resVO = onsMainService.selectUserLogin(reqVO);
		ComUtils.responseJson(request, response, resVO);
	}
	
	
	@RequestMapping("/ons/user/update_action.do")
	public void updateUserConfig(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserConfigVO vo		= new UserConfigVO();
		ResultVO resVO		= null;
		
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
		
		String sSchType	= ComStr.toStr(params.get("sch_type"));
		String sCctvMat	= ComStr.toStr(params.get("cctv_mat"));
		
		if (sSchType.length() > 0) {
			vo.setSch_type(sSchType);
		}
		if (sCctvMat.length() > 0) {
			vo.setCctv_mat(sCctvMat);
		}
		
		vo.setUser_id(info.getUserId());
		
		int nRes	= onsMainService.updateConfigData(vo);
		
		if (nRes > 0) {
			resVO	= ResultVO.create(BaseResVO.RET_OK);
			resVO.setRowCount(nRes);
		} else {
			resVO	= ResultVO.create(BaseResVO.ERR_APP_WEBCOMMON, messageSource);
		}
		
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	
	/**
	 * 팝업 CCTV 메트릭스 화면을 보여준다.
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping("/ons/pop_matrix.do")
	public String popupMatrix() throws Exception {
		logger.info("/ons/pop_matrix.do");
		return "ons/pop_matrix";
	}

	@RequestMapping("/ons/pop_cardetail.do")
	public String popupCardetail() throws Exception {
		logger.info("/ons/pop_cardetail.do");
		return "ons/pop_cardetail";
	}

	@RequestMapping("/ons/pop_sendcourse.do")
	public String popupSendcourse() throws Exception {
		logger.info("/ons/pop_sendcourse.do");
		return "ons/pop_sendcourse";
	}

	@RequestMapping("/ons/install/actx_install.do")
	public String installActxForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/install/actx_install.do");
		String			sDnURL = propertiesService.getString("vlcDownloadURL");

		model.addAttribute("vlc_url", sDnURL);
		
		return "ons/install/actx_install";
	}

	@RequestMapping("/ons/install/actx_check.do")
	public String actxCheckForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/install/actx_check.do");
		return "ons/install/actx_check";
	}
}

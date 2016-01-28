package nses.cms.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.cms.service.CmsMainService;
import nses.cms.vo.LoginVO;
import nses.cms.vo.UserInfoVO;
import nses.common.WebConstants;
import nses.common.session.AdminSessInfo;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.utils.ComCrypto;
import nses.common.utils.ComUtils;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.fdl.cryptography.EgovARIACryptoService;

/**
 * 
 * @Modification Information
 * @
 * @ 수정일			수정자              수정내용
 * @ ---------		---------		-------------------------------
 * @ 2014.12.23						최초생성
 *
 * @author	개발프레임웍크 실행환경 개발팀
 * @since	2014.12.23
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSES All right reserved.
 */

@Controller
public class CmsMainController {

	private static final Logger 	logger = LoggerFactory.getLogger(CmsMainController.class);

	/** OnsMainService */
	@Resource(name = "cmsMainService")
	private CmsMainService cmsMainService;

	@Autowired
	private	MessageSource messageSource;

	/** Validator */
//	@Resource(name = "beanValidator")
//	protected DefaultBeanValidator beanValidator;
	
	@Resource(name="ARIACryptoService")
	private EgovARIACryptoService cryptoService;

	/**
	 * 로그인 화면을 보여준다.
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping("/cms/login.do")
	public String loginForm() throws Exception {
		logger.info("login.do");
		return "cms/main/login";
	}

	/**
	 * 로그인을 처리한다.
	 * @param loginVO - 사용자 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("resultVO") - 결과 정보
	 * @exception Exception
	 */
	@RequestMapping("/cms/login_action.do")
	public void loginAction(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("login_action.do");
		
		LoginVO		reqVO = new LoginVO();
		ResultVO	resVO = null;

		reqVO.setUser_id(params.get("user_id").toString());
		reqVO.setUser_pwd(ComCrypto.encode(cryptoService, params.get("user_pwd").toString()));
		
		UserInfoVO	retVO = cmsMainService.selectUserLogin(reqVO);

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
			AdminSessInfo	info = new AdminSessInfo();
			SessionUtility.setTimeOut(request, WebConstants.USER_SESSION_TIMEOUT);

			logger.info("user_id   : " + retVO.getUser_id());
			logger.info("user_name : " + retVO.getUser_name());
			logger.info("user_email : " + retVO.getUser_email());
			
			
			info.setUserId(retVO.getUser_id());
			info.setUserName(retVO.getUser_name());
			info.setUserEmail(retVO.getUser_email());
			
			SessionUtility.setLoginForAdmin(request, info);
		}
		
		ComUtils.responseJson(request, response, resVO);
	}

	/**
	 * 로그아웃 처리를 수행한다.
	 * @return "cms/login"
	 * @exception Exception
	 */
	@RequestMapping("/cms/logout.do")
	public String logoutForm(HttpServletRequest request) throws Exception {
		logger.info("logout.do");
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		SessionUtility.clearLoginForAdmin(request);

		return "redirect:/cms/login.do";
	}

	/**
	 * 메인 화면을 보여준다.
	 * @param model
	 * @return "cms/main"
	 * @exception Exception
	 */
	@RequestMapping("/cms/main.do")
	public String mainForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("/cms/main.do");
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		return "cms/main/main";
	}

	@RequestMapping(value="/cms/login_action2.do", method=RequestMethod.POST)
	public void loginAction2(@RequestBody String vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("VO : " + vo);
//		LoginVO		reqVO = new LoginVO();
		ResultVO	resVO = new ResultVO();

		
//		reqVO.setUser_id(vo.getUser_id());
//		reqVO.setUser_pwd(vo.getUser_pwd());
//		reqVO.setUser_id(params.get("user_id").toString());
//		reqVO.setUser_pwd(params.get("user_pwd").toString());

//		resVO = cmsMainService.selectUserLogin(reqVO);
		ComUtils.responseJson(request, response, resVO);
	}


}

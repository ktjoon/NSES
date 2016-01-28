package nses.cms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.cms.service.UserInfoService;
import nses.cms.vo.UserInfoVO;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.utils.ComCrypto;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.utils.DbUtils;
import nses.common.vo.BasePageVO;
import nses.common.vo.ResultVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.fdl.cryptography.EgovARIACryptoService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : UserInfoController.java
 * @Description : UserInfo Controller Class
 * @Modification Information
 * @
 * @ 수정일			수정자		수정내용
 * @ ----------		---------	-------------------------------
 * @ 2009.03.16					최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
@RequestMapping(value="/cms/user")
public class UserInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(CctvInfoController.class);
	
	@Resource(name = "userinfoService")
	private UserInfoService userinfoService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name="ARIACryptoService")
	private EgovARIACryptoService cryptoService;
	
	@Autowired
	private	MessageSource messageSource;
	
	/**
	 * 처리 내용 : 사용자 리스트를 화면에 출력
	 * 처리 날짜 : 2014.12.26
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String userListForm(@ModelAttribute("pageVO") BasePageVO pageVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		pageVO.setPageUnit(propertiesService.getInt("pageUnit"));
		pageVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(pageVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(pageVO.getPageUnit());
		paginationInfo.setPageSize(pageVO.getPageSize());

		pageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		pageVO.setLastIndex(paginationInfo.getLastRecordIndex());
		pageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<?> lstData = userinfoService.selectListData(pageVO);
		model.addAttribute("resultList", lstData);
		model.addAttribute("pageVO", pageVO);
		
		int totCnt = userinfoService.selectListCount(pageVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "cms/user/list";
	}
	
	/**
	 * 처리 내용 : 사용자 등록 페이지 이동
	 * 처리 날짜 : 2014.12.26
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reg.do")
	public String userRegForm(@ModelAttribute("userInfoVO") UserInfoVO userVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		model.addAttribute("userInfoVO", userVO);
		
		return "cms/user/reg";
	}
	
	/**
	 * 처리 내용 : 사용자 수정 페이지로 이동 처리 및 정보 표시
	 * 처리 날짜 : 2015.01.06
	 * 
	 * @param userVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit.do")
	public String userEditForm(@ModelAttribute("pageVO") BasePageVO pageVO, @ModelAttribute("userInfoVO") UserInfoVO userVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		userVO	= userinfoService.selectData(userVO);
		
		model.addAttribute("userInfoVO", userVO);
		model.addAttribute("pageVO", pageVO);
		
		return "cms/user/edit";
	}
	
	/**
	 * 처리 내용 : 사용자 등록 정보에 대한 데이터 처리
	 * 처리 날짜 : 2014.12.26
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/reg_action.do")
	public void userRegAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfoVO	dataVO	= new UserInfoVO();
		ResultVO resVO		= new ResultVO();
		
		String sUserTelno	= ComStr.toStr(mapParams.get("user_tel01")) + "-" + ComStr.toStr(mapParams.get("user_tel02")) + "-" + ComStr.toStr(mapParams.get("user_tel03"));
		
		dataVO.setUser_id(ComStr.toStr(mapParams.get("user_id")));
		dataVO.setUser_pwd(ComCrypto.encode(cryptoService, ComStr.toStr(mapParams.get("user_pwd"))));
		dataVO.setUser_name(ComStr.toStr(mapParams.get("user_name")));
		dataVO.setUser_email(ComStr.toStr(mapParams.get("user_email")));
		dataVO.setUser_telno(ComStr.toStr(sUserTelno));
		
		try {
			userinfoService.insertData(dataVO);
			resVO.setRetCode(ResultVO.RET_OK, messageSource);
		} catch (DataAccessException e) {
			if (DbUtils.getErrorCode(e) == DbUtils.ERR_DB_DUPLICATE_KEY) {
				resVO.setRetCode(ResultVO.ERR_DB_DUPLICATE_KEY, messageSource);
			}
			else {
				resVO.setRetCode(ResultVO.ERR_APP_WEBCOMMON, messageSource);
			}
			
			logger.debug("/cms/user/reg_action.do, Insert Error...");
		}
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	
	/**
	 * 처리 내용 : 사용자 수정 정보에 대한 데이터 처리
	 * 처리 날짜 : 2015.01.06
	 * 
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/edit_action.do")
	public void userEditAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfoVO	dataVO	= new UserInfoVO();
		
		int		nRetCode	= 0;
		String sUserTelno	= ComStr.toStr(mapParams.get("user_tel01")) + "-" + ComStr.toStr(mapParams.get("user_tel02")) + "-" + ComStr.toStr(mapParams.get("user_tel03"));
		
		dataVO.setUser_id(ComStr.toStr(mapParams.get("user_id")));
		dataVO.setUser_pwd(ComCrypto.encode(cryptoService, ComStr.toStr(mapParams.get("user_pwd"))));
		dataVO.setUser_name(ComStr.toStr(mapParams.get("user_name")));
		dataVO.setUser_email(ComStr.toStr(mapParams.get("user_email")));
		dataVO.setUser_telno(ComStr.toStr(sUserTelno));
		
		nRetCode = userinfoService.updateData(dataVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		
		ComUtils.responseJson(request, response, map);
	}
	
	@RequestMapping("/del_action.do")
	public void userDelAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfoVO	dataVO	= new UserInfoVO();
		
		int		nRetCode	= 0;
		String sUserId		= ComStr.toStr(mapParams.get("user_id"));
		
		dataVO.setUser_id(sUserId);
		
		nRetCode = userinfoService.deleteData(dataVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		
		ComUtils.responseJson(request, response, map);
	}
	
}

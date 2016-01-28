package nses.cms.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.cms.service.CarOwnerService;
import nses.cms.vo.CarOwnerVO;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.utils.ComCrypto;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.utils.DbUtils;
import nses.common.vo.BasePageVO;
import nses.common.vo.BaseResVO;
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
@RequestMapping(value="/cms/carown")
public class CarOwnerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CarOwnerController.class);
	
	/** EgovSampleService */
	@Resource(name = "carOwnerService")
	private CarOwnerService carOwnerService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name="ARIACryptoService")
	private EgovARIACryptoService cryptoService;
	
	@Autowired
	private	MessageSource messageSource;
	
	
	/**
	 * 처리 내용 : 차주정보 리스트를 화면에 출력
	 * 처리 날짜 : 2015.01.08
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String carOwnListForm(@ModelAttribute("pageVO") BasePageVO pageVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		pageVO.setPageUnit(propertiesService.getInt("pageUnit"));
		pageVO.setPageSize(propertiesService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(pageVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(pageVO.getPageUnit());
		paginationInfo.setPageSize(pageVO.getPageSize());

		pageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		pageVO.setLastIndex(paginationInfo.getLastRecordIndex());
		pageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		
		
		List<CarOwnerVO> lstData 	= null;
		try {
			lstData = carOwnerService.selectListData(pageVO, info.getUserId());
			
			for(int i = 0; i < lstData.size(); i++) {
				CarOwnerVO vo = new CarOwnerVO();
				vo = lstData.get(i);
				vo.setTel_no(ComCrypto.decode(cryptoService, lstData.get(i).getTel_no()));
				lstData.set(i, vo);
				
				int totCnt = carOwnerService.selectListCount(pageVO);
				paginationInfo.setTotalRecordCount(totCnt);
				
			}
		} catch (DataAccessException e) {
			logger.error("/cms/sms/list.do, selectListData | selectListCount Error...");
		}
		catch (Exception e) {
			logger.error("/cms/sms/list.do, selectListData | selectListCount Error...");
		}
		
		if (lstData == null)
			lstData	= new ArrayList<CarOwnerVO>();
		
		model.addAttribute("resultList", lstData);
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "cms/carown/list";
	}
	
	/**
	 * 처리 내용 : 차주정보 등록 페이지 이동
	 * 처리 날짜 : 2015.01.08
	 * 
	 * @param pageVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reg.do")
	public String carOwnRegForm(@ModelAttribute("pageVO") BasePageVO pageVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		model.addAttribute("pageVO", pageVO);
		
		return "cms/carown/reg";
	}
	
	/**
	 * 처리 내용 : 차주정보 수정 페이지로 이동 및 정보 표시
	 * 처리 날짜 : 2015.01.08
	 * 
	 * @param pageVO
	 * @param carOwnerVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit.do")
	public String carOwnEditForm(@ModelAttribute("pageVO") BasePageVO pageVO, @ModelAttribute("userInfoVO") CarOwnerVO carOwnerVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		try {
			carOwnerVO	= carOwnerService.selectData(carOwnerVO, info.getUserId());
			
		} catch (DataAccessException e) {
			logger.error("/cms/carown/edit.do, selectData Error...");
		}
		catch (Exception e) {
			logger.error("/cms/carown/edit.do, selectData Error...");
		}

		carOwnerVO.setTel_no(ComCrypto.decode(cryptoService, carOwnerVO.getTel_no()));
		model.addAttribute("carOwnerVO", carOwnerVO);
		model.addAttribute("pageVO", pageVO);
		
		return "cms/carown/edit";
	}
	
	/**
	 * 처리 내용 : 차주정보 등록에 대한 데이터 처리
	 * 처리 날짜 : 2015.01.08
	 * 
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/reg_action.do")
	public void carOwnRegAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			ComUtils.responseJson(request, response, ResultVO.create(BaseResVO.ERR_SESSION_NOT_EXIST));
			return;
		}

		CarOwnerVO	dataVO	= new CarOwnerVO();
		ResultVO	resVO	= new ResultVO();
		
		String sOwnCarNo	= ComStr.toStr(mapParams.get("own_car_no"));
		String sOwnName		= ComStr.toStr(mapParams.get("own_name"));
		String sOwnTelno	= ComStr.toStr(mapParams.get("own_tel01")) + "-" + ComStr.toStr(mapParams.get("own_tel02")) + "-" + ComStr.toStr(mapParams.get("own_tel03"));
		
		dataVO.setOwn_car_no(sOwnCarNo);
		dataVO.setOwn_name(sOwnName);
		dataVO.setTel_no(ComCrypto.encode(cryptoService, sOwnTelno));

		try {
			carOwnerService.insertData(dataVO, info.getUserId());
			resVO.setRetCode(ResultVO.RET_OK, messageSource);
			
		} catch (DataAccessException e) {
			if (DbUtils.getErrorCode(e) == DbUtils.ERR_DB_DUPLICATE_KEY) {
				resVO.setRetCode(ResultVO.ERR_DB_DUPLICATE_KEY, messageSource);
			}
			else {
				resVO.setRetCode(ResultVO.ERR_INSERT_FAIL, messageSource);
			}
			
			logger.debug("/cms/carown/reg_action.do, Insert Error...");
		} 
		catch (Exception e) {
			resVO.setRetCode(ResultVO.ERR_INSERT_FAIL, messageSource);
			logger.debug("/cms/carown/reg_action.do, Insert Error...");
		}
		ComUtils.responseJson(request, response, resVO);
	}
	
	
	/**
	 * 처리 내용 : 차주정보 수정에 대한 데이터 처리
	 * 처리 날짜 : 2015.01.08
	 * 
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/edit_action.do")
	public void carOwnEditAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			ComUtils.responseJson(request, response, ResultVO.create(BaseResVO.ERR_SESSION_NOT_EXIST));
			return;
		}

		CarOwnerVO	dataVO	= new CarOwnerVO();
		ResultVO	resVO	= new ResultVO();
		
		int nRet			= 0;
		String sUserTelno	= ComStr.toStr(mapParams.get("own_tel01")) + "-" + ComStr.toStr(mapParams.get("own_tel02")) + "-" + ComStr.toStr(mapParams.get("own_tel03"));
		
		dataVO.setOwn_car_no(ComStr.toStr(mapParams.get("own_car_no")));
		dataVO.setOwn_name(ComStr.toStr(mapParams.get("own_name")));
		dataVO.setTel_no(ComCrypto.encode(cryptoService, sUserTelno));
		
		try {
			nRet = carOwnerService.updateData(dataVO, info.getUserId());
			
			if (nRet > 0) {
				resVO.setRetCode(ResultVO.RET_OK, messageSource);
				
			} else {
				resVO.setRetCode(ResultVO.ERR_UPDATE_FAIL, messageSource);
			}
			
		} catch (DataAccessException e) {
			logger.error("/cms/carown/edit_action.do, Update Error...");
			resVO.setRetCode(ResultVO.ERR_APP_WEBCOMMON, messageSource);
		} catch (Exception e) {
			logger.error("/cms/carown/edit_action.do, Update Error...");
			resVO.setRetCode(ResultVO.ERR_APP_WEBCOMMON, messageSource);
		}
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	/**
	 * 처리 내용 : 차주정보 삭제에 대한 데이터 처리
	 * 처리 날짜 : 2015.01.08
	 * 
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/del_action.do")
	public void carOwnDelAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			ComUtils.responseJson(request, response, ResultVO.create(BaseResVO.ERR_SESSION_NOT_EXIST));
			return;
		}

		CarOwnerVO	dataVO	= new CarOwnerVO();
		ResultVO	resVO	= new ResultVO();
		
		int		nRet	= 0;
		
		dataVO.setOwn_car_no(ComStr.toStr(mapParams.get("own_car_no")));
		
		try {
			nRet = carOwnerService.deleteData(dataVO, info.getUserId());
			
			if (nRet > 0) {
				resVO.setRetCode(ResultVO.RET_OK, messageSource);
				
			} else {
				resVO.setRetCode(ResultVO.ERR_DELETE_FAIL, messageSource);
			}
			
		} catch (DataAccessException e) {
			logger.error("/cms/carown/del_action.do, Delete Error...");
			resVO.setRetCode(ResultVO.ERR_APP_WEBCOMMON, messageSource);
		} catch (Exception e) {
			logger.error("/cms/carown/del_action.do, Delete Error...");
			resVO.setRetCode(ResultVO.ERR_APP_WEBCOMMON, messageSource);
		}
		
		ComUtils.responseJson(request, response, resVO);
	}

	/**
	 * 처리 내용 : 차주정보 리스트를 화면에 출력
	 * 처리 날짜 : 2015.01.08
	 * 
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/hist.do")
	public String carOwnHistForm(@ModelAttribute("pageVO") BasePageVO pageVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		pageVO.setPageUnit(propertiesService.getInt("pageUnit"));
		pageVO.setPageSize(propertiesService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(pageVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(pageVO.getPageUnit());
		paginationInfo.setPageSize(pageVO.getPageSize());

		pageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		pageVO.setLastIndex(paginationInfo.getLastRecordIndex());
		pageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<?> lstData = carOwnerService.selectHistData(pageVO);
		model.addAttribute("resultList", lstData);
		model.addAttribute("searchData", pageVO);
		
		int totCnt = carOwnerService.selectHistCount(pageVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "cms/carown/hist";
	}
}

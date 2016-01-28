package nses.cms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.cms.service.FirecarInfoService;
import nses.cms.vo.FirecarInfoVO;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.vo.BasePageVO;
import nses.common.vo.BaseResVO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : FirecarInfoController.java
 * @Description : FirecarInfo Controller Class
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
@RequestMapping(value="/cms/firecar")
public class FirecarInfoController {
	/** EgovSampleService */
	@Resource(name = "firecarinfoService")
	private FirecarInfoService firecarinfoService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * 처리 내용 : 차량정보 리스트를 화면에 출력
	 * 처리 날짜 : 2014.12.26
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String firecarListForm(@ModelAttribute("pageVO") BasePageVO pageVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		List<?> lstData = firecarinfoService.selectListData(pageVO);
		model.addAttribute("resultList", lstData);
		model.addAttribute("searchData", pageVO);
		
		int totCnt = firecarinfoService.selectListCount(pageVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "cms/firecar/list";
	}
	
	/**
	 * 처리 내용 : 차량정보 등록 페이지 이동
	 * 처리 날짜 : 2014.12.26
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reg.do")
	public String firecarRegForm(@ModelAttribute("pageVO") FirecarInfoVO pageVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		return "cms/firecar/reg";
	}
	
	/**
	 * 처리 내용 : 차량정보 수정 페이지로 이동 처리 및 정보 표시
	 * 처리 날짜 : 2015.01.06
	 * 
	 * @param cctvVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit.do")
	public String firecarEditForm(@ModelAttribute("pageVO") FirecarInfoVO pageVO, @ModelAttribute("cctvInfoVO") FirecarInfoVO firecarVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		firecarVO	= firecarinfoService.selectData(firecarVO);
		model.addAttribute("resultDataVO", firecarVO);
		model.addAttribute("pageVO", pageVO);
		
		return "cms/firecar/edit";
	}
	
	/**
	 * 처리 내용 : 차량정보 등록 정보에 대한 데이터 처리
	 * 처리 날짜 : 2014.12.26
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/reg_action.do")
	public void firecarRegAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FirecarInfoVO	dataVO	= new FirecarInfoVO();
		
		int		nRetCode	= 0;
		
		
		dataVO.setCar_id(ComStr.toStr(mapParams.get("car_id")));
		dataVO.setCar_no(ComStr.toStr(mapParams.get("car_no")));
		dataVO.setCar_type(ComStr.toStr(mapParams.get("car_type")));
		dataVO.setCam_yn(ComStr.toStr(mapParams.get("cam_yn")));
		dataVO.setCam_hostport(ComStr.toStr(mapParams.get("cam_hostport")));
		dataVO.setModel_name(ComStr.toStr(mapParams.get("cam_uri")));
		dataVO.setModel_name(ComStr.toStr(mapParams.get("cctv_seqno")));
		dataVO.setModel_name(ComStr.toStr(mapParams.get("model_name")));
		dataVO.setModel_number(ComStr.toStr(mapParams.get("model_number")));
		dataVO.setSerial_number(ComStr.toStr(mapParams.get("serial_number")));
		dataVO.setMunufact_dt(ComStr.toStr(mapParams.get("munufact_dt")));
		dataVO.setUse_stat(ComStr.toStr(mapParams.get("use_stat")));
		dataVO.setCam_auth_data(ComStr.toStr(mapParams.get("cam_auth_data")));
		dataVO.setCam_url(ComStr.toStr(mapParams.get("cam_url")));
		dataVO.setUser_id(SessionUtility.getLoginForAdmin(request).getUserId());
		
		nRetCode = firecarinfoService.insertData(dataVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		
		ComUtils.responseJson(request, response, map);
	}
	
	/**
	 * 처리 내용 : 차량번호를 통한 차량 ID조회처리 
	 * 처리 날짜 : 2014.12.26
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/id_action.do")
	public void firecarIdAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int		nRetCode	= 0;
		
		String car_no = ComStr.toStr(mapParams.get("car_no"));
		
		Map<String, String> carMap = firecarinfoService.selectCarId(car_no);
		String				sCarId = "";
		String				sCarType = "";
		
		if (carMap != null) {
			sCarId 		= ComStr.toStr(carMap.get("carId"));
			sCarType 	= ComStr.toStr(carMap.get("carType"));
			if(sCarId.length() > 0) {
				nRetCode = BaseResVO.RET_OK;
			} 
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		map.put("car_id", sCarId);
		map.put("car_type", sCarType);
		
		ComUtils.responseJson(request, response, map);
	}
	
	/**
	 * 처리 내용 : 차량정보 수정 정보에 대한 데이터 처리
	 * 처리 날짜 : 2015.01.06
	 * 
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/edit_action.do")
	public void firecarEditAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FirecarInfoVO	dataVO	= new FirecarInfoVO();
		
		int		nRetCode	= 0;
		
		dataVO.setCar_id(ComStr.toStr(mapParams.get("car_id")));
		dataVO.setCar_no(ComStr.toStr(mapParams.get("car_no")));
		dataVO.setCar_type(ComStr.toStr(mapParams.get("car_type")));
		dataVO.setCam_yn(ComStr.toStr(mapParams.get("cam_yn")));
		dataVO.setCam_hostport(ComStr.toStr(mapParams.get("cam_hostport")));
		dataVO.setModel_name(ComStr.toStr(mapParams.get("cam_uri")));
		dataVO.setModel_name(ComStr.toStr(mapParams.get("cctv_seqno")));
		dataVO.setModel_name(ComStr.toStr(mapParams.get("model_name")));
		dataVO.setModel_number(ComStr.toStr(mapParams.get("model_number")));
		dataVO.setSerial_number(ComStr.toStr(mapParams.get("serial_number")));
		dataVO.setMunufact_dt(ComStr.toStr(mapParams.get("munufact_dt")));
		dataVO.setUse_stat(ComStr.toStr(mapParams.get("use_stat")));
		dataVO.setCam_auth_data(ComStr.toStr(mapParams.get("cam_auth_data")));
		dataVO.setCam_url(ComStr.toStr(mapParams.get("cam_url")));
		dataVO.setUser_id(SessionUtility.getLoginForAdmin(request).getUserId());
		
		
		nRetCode = firecarinfoService.updateData(dataVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		
		ComUtils.responseJson(request, response, map);
	}
	
	/**
	 * 처리 내용 : 차량정보 삭제 정보에 대한 데이터 처리
	 * 처리 날짜 : 2015.01.06
	 * 
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete_action.do")
	public void firecarDeleteAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FirecarInfoVO	dataVO	= new FirecarInfoVO();
		
		int		nRetCode	= 0;
		
		dataVO.setCar_id(ComStr.toStr(mapParams.get("car_id")));
		nRetCode = firecarinfoService.deleteData(dataVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		
		ComUtils.responseJson(request, response, map);
	}
	
}

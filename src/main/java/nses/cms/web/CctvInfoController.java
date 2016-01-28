package nses.cms.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.cms.service.CctvInfoService;
import nses.cms.vo.CctvInfoVO;
import nses.common.coord.GeoPoint;
import nses.common.coord.GeoTrans;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.utils.DataConvertor;
import nses.common.utils.ExcelUtil;
import nses.common.vo.BasePageVO;
import nses.common.vo.BaseResVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : CctvInfoController.java
 * @Description : CctvInfo Controller Class
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
@RequestMapping(value="/cms/cctv")
public class CctvInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(CctvInfoController.class);
	
	
	/** EgovSampleService */
	@Resource(name = "cctvinfoService")
	private CctvInfoService cctvinfoService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	ServletContext context;
	
	/**
	 * 처리 내용 : CCTV 리스트를 화면에 출력
	 * 처리 날짜 : 2014.12.26
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String cctvListForm(@ModelAttribute("pageVO") BasePageVO pageVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		List<?> lstData = cctvinfoService.selectListData(pageVO);
		model.addAttribute("resultList", lstData);
		model.addAttribute("searchData", pageVO);
		
		int totCnt = cctvinfoService.selectListCount(pageVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "cms/cctv/list";
	}
	
	/**
	 * 처리 내용 : CCTV 등록 페이지 이동
	 * 처리 날짜 : 2014.12.26
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reg.do")
	public String cctvRegForm(@ModelAttribute("pageVO") BasePageVO pageVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		return "cms/cctv/reg";
	}
	
	/**
	 * 처리 내용 : CCTV 수정 페이지로 이동 처리 및 정보 표시
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
	public String cctvEditForm(@ModelAttribute("pageVO") BasePageVO pageVO, @ModelAttribute("cctvInfoVO") CctvInfoVO cctvVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		cctvVO	= cctvinfoService.selectData(cctvVO);
		model.addAttribute("resultDataVO", cctvVO);
		model.addAttribute("pageVO", pageVO);
		
		return "cms/cctv/edit";
	}
	
	/**
	 * 처리 내용 : CCTV 등록 정보에 대한 데이터 처리
	 * 처리 날짜 : 2014.12.26
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/reg_action.do")
	public void cctvRegAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CctvInfoVO	dataVO	= new CctvInfoVO();
		
		int		nRetCode	= 0;
		
		String uuid = UUID.randomUUID().toString();
		
		dataVO.setCctv_guid(uuid); 
		dataVO.setCctv_name(ComStr.toStr(mapParams.get("cctv_name")));
		dataVO.setCctv_lat(ComStr.toDouble(mapParams.get("cctv_lat")));
		dataVO.setCctv_lng(ComStr.toDouble(mapParams.get("cctv_lng")));
		dataVO.setCctv_kind(ComStr.toStr(mapParams.get("cctv_kind")));
		dataVO.setCctv_model(ComStr.toStr(mapParams.get("cctv_model")));
		dataVO.setTel_no(ComStr.toStr(mapParams.get("tel_no")));
		dataVO.setCctv_hostport(ComStr.toStr(mapParams.get("cctv_hostport")));
		dataVO.setCctv_seqno(ComStr.toStr(mapParams.get("cctv_seqno")));
		dataVO.setCam_auth_data(ComStr.toStr(mapParams.get("cam_auth_data")));
		dataVO.setCam_url(ComStr.toStr(mapParams.get("cam_url")));
		
		GeoPoint geoPoint = DataConvertor.convertGeoTrans(Double.valueOf(dataVO.getCctv_lng()), Double.valueOf(dataVO.getCctv_lat()), GeoTrans.GEO, GeoTrans.UTMK);
		
		dataVO.setUtmk_x(geoPoint.getX());
		dataVO.setUtmk_y(geoPoint.getY());
		
		dataVO.setUser_id(SessionUtility.getLoginForAdmin(request).getUserId());
		
		nRetCode = cctvinfoService.insertData(dataVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		
		ComUtils.responseJson(request, response, map);
	}
	
	/**
	 * 처리내용 : CCTV 이름 조회
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/name_action.do")
	public void cctvIdAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int		nRetCode	= 0;
		
		String cctv_name = ComStr.toStr(mapParams.get("cctv_name"));
		
		int count = cctvinfoService.selectCctvName(cctv_name);
		
		
		nRetCode = BaseResVO.RET_OK;
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		map.put("dataCount", count);
		
		ComUtils.responseJson(request, response, map);
	}
	
	
	/**
	 * 처리 내용 : CCTV 수정 정보에 대한 데이터 처리
	 * 처리 날짜 : 2015.01.06
	 * 
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/edit_action.do")
	public void cctvEditAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CctvInfoVO	dataVO	= new CctvInfoVO();
		
		int		nRetCode	= 0;
		
		
		dataVO.setCctv_guid(ComStr.toStr(mapParams.get("cctv_guid")));
		dataVO.setCctv_name(ComStr.toStr(mapParams.get("cctv_name")));
		dataVO.setCctv_lat(ComStr.toDouble(mapParams.get("cctv_lat")));
		dataVO.setCctv_lng(ComStr.toDouble(mapParams.get("cctv_lng")));
		dataVO.setCctv_kind(ComStr.toStr(mapParams.get("cctv_kind")));
		dataVO.setCctv_model(ComStr.toStr(mapParams.get("cctv_model")));
		dataVO.setTel_no(ComStr.toStr(mapParams.get("tel_no")));
		dataVO.setCctv_hostport(ComStr.toStr(mapParams.get("cctv_hostport")));
		dataVO.setCctv_seqno(ComStr.toStr(mapParams.get("cctv_seqno")));
		dataVO.setCam_auth_data(ComStr.toStr(mapParams.get("cam_auth_data")));
		dataVO.setCam_url(ComStr.toStr(mapParams.get("cam_url")));
		
		GeoPoint geoPoint = DataConvertor.convertGeoTrans(Double.valueOf(dataVO.getCctv_lng()), Double.valueOf(dataVO.getCctv_lat()), GeoTrans.GEO, GeoTrans.UTMK);
		
		dataVO.setUtmk_x(geoPoint.getX());
		dataVO.setUtmk_y(geoPoint.getY());
		
		dataVO.setUser_id(SessionUtility.getLoginForAdmin(request).getUserId());
		
		nRetCode = cctvinfoService.updateData(dataVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		
		ComUtils.responseJson(request, response, map);
	}
	
	/**
	 * 처리 내용 : CCTV 삭제 정보에 대한 데이터 처리
	 * 처리 날짜 : 2015.01.06
	 * 
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete_action.do")
	public void cctvDeleteAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CctvInfoVO	dataVO	= new CctvInfoVO();
		//ResultVO resVO		= new ResultVO();
		
		int		nRetCode	= 0;
		
		dataVO.setCctv_guid(ComStr.toStr(mapParams.get("cctv_guid")));
		
		nRetCode = cctvinfoService.deleteData(dataVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		
		ComUtils.responseJson(request, response, map);
	}
	
	
	/**
	 * 처리 내용 : CCTV 엑셀 정보에 대한 데이터 처리
	 * @param multiRequest
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/excel_upload.do")
	public String popExcelUpload(MultipartHttpServletRequest multiRequest, ModelMap model, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		List<MultipartFile> excel = multiRequest.getFiles("excel");
		String userid = SessionUtility.getLoginForAdmin(request).getUserId();
		
		if(excel.get(0).getSize() > 1048576) {
			throw new ServletException();
		}
		
		String filename = excel.get(0).getOriginalFilename().toLowerCase();
		
		if(filename != null) {
			if(filename.endsWith(".xlsx") | filename.endsWith(".xls")) {
				try {
					String savePath = context.getRealPath("") + "/upload_excel";
					File saveFolder = new File(savePath);
					if (!saveFolder.exists() || saveFolder.isFile()) {
						saveFolder.mkdirs();
					}
					String filePath = savePath + "/"+ excel.get(0).getOriginalFilename();
					
					File file = new File(filePath);
					excel.get(0).transferTo(file);
					List<String[]> list = new ArrayList<String[]>();
					
					list = ExcelUtil.readExcel(filePath, 1, true, 0);
					
					List<CctvInfoVO> lstData = new ArrayList<CctvInfoVO>();
					
					for (String[] row : list) {
						CctvInfoVO vo = new CctvInfoVO();
						
						if (row[4].equals("")) {
							break;
						}
						
						vo.setCctv_guid(row[1]);
						vo.setCctv_seqno(row[2]);
						vo.setCctv_name(row[3]);
						vo.setCctv_lng(Double.valueOf(row[4]));
						vo.setCctv_lat(Double.valueOf(row[5]));
							
						GeoPoint geoPoint = DataConvertor.convertGeoTrans(Double.valueOf(row[4]), Double.valueOf(row[5]), GeoTrans.GEO, GeoTrans.UTMK);
						
						vo.setUtmk_x(geoPoint.getX());
						vo.setUtmk_y(geoPoint.getY());
						vo.setCctv_hostport(row[8]);
						vo.setCctv_seqno(row[2]);
						vo.setCctv_kind(row[6]);
						vo.setTel_no(row[7]);
						vo.setUser_id(userid);
						
						if(cctvinfoService.selectData(vo) == null){
							lstData.add(vo);
						}
					}
					
					if(lstData.size() > 0) {
						cctvinfoService.insertExcelData(lstData);
					}
					model.addAttribute("uploadYN", "Y");
				} catch (DataAccessException e) {
					logger.error("/cms/cctv/excel_upload.do, popExcelUpload Error...");
				}
			} else {
				throw new ServletException();
			}
		}
		
		return "cms/cctv/reg";
	}
	
}

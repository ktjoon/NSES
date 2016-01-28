package nses.cms.web;

import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.cms.service.RecogInfoService;
import nses.cms.vo.RecogDetailVO;
import nses.cms.vo.RecogListVO;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.utils.ComCrypto;
import nses.common.vo.BasePageVO;
import nses.ons.service.RecogImgService;
import nses.ons.vo.CarOwnerVO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.cryptography.EgovARIACryptoService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : RecogInfoController.java
 * @Description : RecogInfo Controller Class
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
@RequestMapping(value="/cms/recog")
public class RecogInfoController {
	/** EgovSampleService */
	@Resource(name = "recoginfoService")
	private RecogInfoService recoginfoService;
	
	@Resource(name = "recogImgService")
	private RecogImgService recogImgService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name="ARIACryptoService")
	private EgovARIACryptoService cryptoService;
	
	/**
	 * 처리 내용 : 단속정보 리스트를 화면에 출력
	 * 처리 날짜 : 2014.12.26
	 * @param searchVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String recogListForm(@ModelAttribute("pageVO") BasePageVO pageVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		List<RecogListVO> lstData = recoginfoService.selectListData(pageVO);
		
		for(int i=0; i<lstData.size(); i++) {
			RecogListVO vo = new RecogListVO();
			vo = lstData.get(i);
			vo.setTel_no(ComCrypto.decode(cryptoService, lstData.get(i).getTel_no()));
			lstData.set(i, vo);
		}
		
		
		model.addAttribute("resultList", lstData);
		model.addAttribute("searchData", pageVO);
		
		int totCnt = recoginfoService.selectListCount(pageVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "cms/recog/list";
	}
	
	/**
	 * 처리 내용 : 단속정보  정보 표시
	 * 처리 날짜 : 2015.01.06
	 * 
	 * @param recogVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit.do")
	public String recogEditForm(@ModelAttribute("pageVO") BasePageVO pageVO, @ModelAttribute("recogDetailVO") RecogDetailVO recogVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		List<?> lstData	= recoginfoService.selectData(recogVO);
		CarOwnerVO carVO = new CarOwnerVO();
		
		String carno =  recogVO.getOwn_car_no();
		carVO = recogImgService.selectCarOwnInfo(carno);
		
		model.addAttribute("car_no", carno);
		model.addAttribute("uenc_car_no", URLEncoder.encode(carno, "UTF-8"));
		if(carVO != null) {
			model.addAttribute("own_name", carVO.getOwn_name());
			model.addAttribute("tel_no", ComCrypto.decode(cryptoService, carVO.getTel_no()));
		}
		
		model.addAttribute("resultList", lstData);
		
		return "cms/recog/edit";
	}
	
	
	//
	@RequestMapping("/sendermsg_list.do")
	public String recogSenderMsgList(@ModelAttribute("pageVO") BasePageVO pageVO, @ModelAttribute("recogDetailVO") RecogDetailVO recogVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		
		List<?> lstData	= recoginfoService.selectSenderMsgData(pageVO);
		
		model.addAttribute("resultList", lstData);
		model.addAttribute("searchData", pageVO);
		
		int totCnt = recoginfoService.selectSenderMsgCount(pageVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		return "cms/recog/sendermsg_list";
	}
	
}

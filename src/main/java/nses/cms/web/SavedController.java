package nses.cms.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.cms.service.SavedService;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.vo.BasePageVO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping(value="/cms/saved")
public class SavedController {
	/** EgovSampleService */
	@Resource(name = "savedService")
	private SavedService savedService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * 처리 내용 : 저장영상 리스트를 화면에 출력
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
		
		List<?> lstData = savedService.selectListData(pageVO);
		model.addAttribute("resultList", lstData);
		model.addAttribute("pageVO", pageVO);
		
		int totCnt = savedService.selectListCount(pageVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "cms/saved/list";
	}
	
	/**
	 * 처리 내용 : 저장영상 팝업페이지 Popup
	 * 처리 날짜 : 2015.01.08
	 * 
	 * @param pageVO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pop_player.do")
	public String userRegForm(@RequestParam Map<String,Object> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		model.addAttribute("params", params);
		return "cms/saved/pop_player";
	}
	
}

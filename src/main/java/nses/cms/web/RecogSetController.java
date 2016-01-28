package nses.cms.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.cms.service.RecogSetService;
import nses.cms.vo.RecogSetVO;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : RecogSetController.java
 * @Description : RecogSet Controller Class
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
@RequestMapping(value="/cms/recogset")
public class RecogSetController {
	
	/** EgovSampleService */
	@Resource(name = "recogsetService")
	private RecogSetService recogsetService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * 처리 내용 : 단속정보 수정 페이지로 이동 처리 및 정보 표시
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
	public String cctvEditForm( @ModelAttribute("recogSetVO") RecogSetVO recogsetVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		RecogSetVO		resVO;
		int				showSaveSec = ComStr.toInt(request.getParameter("show_save_sec"));
		
		resVO = recogsetService.selectData(recogsetVO);
		
		model.addAttribute("recogsetVO", resVO);
		model.addAttribute("show_save_sec", showSaveSec);
		
		return "cms/recogset/edit";
	}
	
	
	/**
	 * 처리 내용 : 단속정보 수정 정보에 대한 데이터 처리
	 * 처리 날짜 : 2015.01.06
	 * 
	 * @param mapParams
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/edit_action.do")
	public void cctvEditAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RecogSetVO	dataVO	= new RecogSetVO();
		
		int		nRetCode	= 0;
		
		String startTime = ComStr.toStr(mapParams.get("enforce_start_hh")) + ":" + ComStr.toStr(mapParams.get("enforce_start_mm"));
		String endTime = ComStr.toStr(mapParams.get("enforce_end_hh")) + ":" + ComStr.toStr(mapParams.get("enforce_end_mm"));
		
		dataVO.setEnforce_time(ComStr.toStr(mapParams.get("enforce_time")));
		dataVO.setEnforce_start_time(startTime);
		dataVO.setEnforce_end_time(endTime);
		dataVO.setMsg_contents(ComStr.toStr(mapParams.get("msg_contents")));
		dataVO.setMsg_auto_send(ComStr.toStr(mapParams.get("msg_auto_send")).equals("on") ? "1": "0");
		dataVO.setOrg_user_name(ComStr.toStr(mapParams.get("org_user_name")));
		dataVO.setOrg_user_tel(ComStr.toStr(mapParams.get("org_user_tel")));
		dataVO.setSender_tel(ComStr.toStr(mapParams.get("sender_tel")));
		dataVO.setRec_save_sec(ComStr.toStr(mapParams.get("rec_save_sec")));
		dataVO.setUser_id(SessionUtility.getLoginForAdmin(request).getUserId());
		
		nRetCode = recogsetService.updateData(dataVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", nRetCode);
		
		ComUtils.responseJson(request, response, map);
	}
	
	
}

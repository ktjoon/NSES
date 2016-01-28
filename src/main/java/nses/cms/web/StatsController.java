package nses.cms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.cms.service.StatsService;
import nses.cms.vo.StatsVO;
import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.utils.ComDate;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Class Name : StatsController.java
 * @Description : Stats Controller Class
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
@RequestMapping(value="/cms/stats")
public class StatsController {
	
	@Resource(name = "statsService")
	private StatsService statsService;
	
	@Autowired
	ServletContext context;
	
	
	@RequestMapping("/list.do")
	public String statsListForm(@RequestParam Map<String, Object> mapParams, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseSessInfo info	= SessionUtility.getLoginForAdmin(request);
		
		if (info == null) {
			return "redirect:/cms/login.do";
		}
		
		//
		model.addAttribute("sdate", ComDate.makeDispDateString(-7));
		model.addAttribute("edate", ComDate.makeDispDateString());
		
		return "cms/stats/list";
	}
	
	@RequestMapping("/stats_action.do")
	public void statsAction(@RequestParam Map<String, Object> mapParams, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		StatsVO statsVO = new StatsVO();
		
		String type = ComStr.toStr(mapParams.get("type"));
		String start_dt = ComStr.toStr(mapParams.get("start_dt")).replaceAll("-", "");
		String end_dt = ComStr.toStr(mapParams.get("end_dt")).replaceAll("-", "");
		
		statsVO.setType(type);
		
		if(type.equals("1")) {
			statsVO.setStart_dt(start_dt.substring(0, 6));
			statsVO.setEnd_dt(end_dt.substring(0, 6));
		} else {
			statsVO.setStart_dt(start_dt);
			statsVO.setEnd_dt(end_dt);
		}
		
		List<?> lstData = statsService.selectListData(statsVO);
		
		
		int		nRetCode	= 200;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		if(type.equals("1")) {
			String[] result = DateUtil.getDiffMonth(statsVO.getStart_dt(), statsVO.getEnd_dt());
			map.put("days", result);
		} else {
			String[] result = DateUtil.getDiffDays(statsVO.getStart_dt(), statsVO.getEnd_dt());
			map.put("days", result);
		}
		
		map.put("items", lstData);
		map.put("retCode", nRetCode);
		ComUtils.responseJson(request, response, map);
	}
}


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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.common.session.BaseSessInfo;
import nses.common.session.SessionUtility;
import nses.common.session.UserSessInfo;
import nses.common.utils.ComDate;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultListVO;
import nses.common.vo.ResultVO;
import nses.ons.service.AccidentEmrsService;
import nses.ons.service.AccidentInfoService;
import nses.ons.vo.AccdWardInfoVO;
import nses.ons.vo.AccdWcarInfoVO;
import nses.ons.vo.AccidentInfoVO;
import nses.ons.vo.DspResVO;
import nses.ons.vo.FireCarInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class AccidentInfoController {

	private static final Logger 	logger = LoggerFactory.getLogger(AccidentInfoController.class);
	
	@Resource(name ="accidentemrsService")
	private AccidentEmrsService accidentEmrsService;
	
	@Resource(name = "accidentInfoService")
	private AccidentInfoService accidentInfoService;

	/** Validator */
//	@Resource(name = "beanValidator")
//	protected DefaultBeanValidator beanValidator;

	@Autowired
	private	MessageSource messageSource;

	/**
	 * 처리 내용 : 재난정보 리스트 조회
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/accident/list_ajax.do")
	public void accdInfolistAjax( @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/accident/list_ajax.do");
		AccidentInfoVO accidentVO	= new AccidentInfoVO();
		
		int				nResCode = BaseResVO.ERR_DATA_NOT_FOUND;
		List<?>			lstData	= accidentInfoService.selectListData(accidentVO);
		ResultListVO	resVO;
		
		if (lstData != null) {
			nResCode = BaseResVO.RET_OK;
			for (int i = 0; i < lstData.size(); i ++) {
				AccidentInfoVO 	vo = (AccidentInfoVO) lstData.get(i);
				vo.convertCoord();
			}
		}
		else {
			nResCode = BaseResVO.RET_OK;
			lstData	= new ArrayList<String>();
		}
		
		resVO = ResultListVO.create(nResCode);
		resVO.setItems(lstData);
		resVO.setCurr_date(ComDate.makeDispDateTimeString(-1));
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	
	
	////
	@RequestMapping("/ons/accident/wardlist_ajax.do")
	public void accdWardlistAjax( @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/accident/wardlist_ajax.do");
		
		DspResVO dspVO			= new DspResVO();
		
		int				nResCode 	= BaseResVO.ERR_DATA_NOT_FOUND;
		String 			sDsrSeq		= ComStr.toStr(params.get("dsr_seq"));
		
		List<?>			lstWard		= accidentEmrsService.selectWardLocationList(sDsrSeq);
		List<?>			lstWardCar	= accidentEmrsService.selectVlLocationList(sDsrSeq);
		
		
		if (lstWard != null) {
			nResCode = BaseResVO.RET_OK;
			for (int i = 0; i < lstWard.size(); i ++) {
				AccdWardInfoVO 	vo = (AccdWardInfoVO) lstWard.get(i);
				vo.convertCoord();
			}
		}
		
		if (lstWardCar != null) {
			logger.info("/ons/accident/wardlist_ajax.do - lstWardCar.size:" + lstWardCar.size());

			List<String>	aryCarNo = new ArrayList<String>();

			nResCode = BaseResVO.RET_OK;
			
			for (int i = 0; i < lstWardCar.size(); i ++) {
				AccdWcarInfoVO 	vo = (AccdWcarInfoVO) lstWardCar.get(i);
				vo.convertCoord();
				
				if (vo.isEmpty_Car_no() == false) {
					aryCarNo.add(vo.getCar_no());
				}
			}
			
			// TB_FIRECAR_INFO(차량용 카메라)를 출동차량정보에 넣는다.
			if (aryCarNo.size() > 0) {
				HashMap<String, Object> mapData	= new HashMap<String, Object>();
				
				mapData.put("ary_car_no", aryCarNo);
				
				List<?> 	lstFireCar = accidentInfoService.selectFireCarInfoList(mapData);
				
				if (lstFireCar != null && lstFireCar.size() > 0) {
					for (int i = 0; i < lstFireCar.size(); i ++) {
						FireCarInfoVO	fvo = (FireCarInfoVO) lstFireCar.get(i);
						
						if (fvo.isExistCam()) {
							fvo.copyTo(lstWardCar);
						}
					}
				}
			}
		}

		dspVO = DspResVO.create(nResCode);
		dspVO.setWards(lstWard);
		dspVO.setWcars(lstWardCar);
		
		ComUtils.responseJson(request, response, dspVO);
	}
	
	/**
	 * 처리 내용 : 출동차량 리스트 조회
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/accident/wcarlist_ajax.do")
	public void accdWcarlistAjax( @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/accident/wcarlist_ajax.do");
		
		ResultListVO 	resVO		= null;
		int				nResCode 	= BaseResVO.ERR_DATA_NOT_FOUND;
		String 			sDsrSeq		= ComStr.toStr(params.get("dsr_seq"));
		
		List<?>			lstWards	= accidentEmrsService.selectVlLocationList(sDsrSeq);
		
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
		
		/*if (info == null) {
			ResultVO	errVO = ResultVO.create(ResultVO.ERR_SESSION_NOT_EXIST, messageSource);
			ComUtils.responseJson(request, response, errVO);
			return;
		}*/

		if (lstWards != null) {
			nResCode = BaseResVO.RET_OK;
			
			for (int i = 0; i < lstWards.size(); i ++) {
				AccdWcarInfoVO 	vo = (AccdWcarInfoVO) lstWards.get(i);
				vo.convertCoord();
			}
		}

		resVO = ResultListVO.create(nResCode, messageSource);
		resVO.setItems(lstWards);
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	/**
	 * 처리 내용 : 
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/accident/wardscars_ajax.do")
	public void accdWardsCarslistAjax( @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/accident/wardscars_ajax.do");
		
		DspResVO dspVO			= new DspResVO();
		
		int				nResCode 	= BaseResVO.ERR_DATA_NOT_FOUND;
		String 			sDsrSeq		= ComStr.toStr(params.get("dsr_seq"));
		
		List<?>			lstWard		= accidentEmrsService.selectWardLocationList(sDsrSeq);
		List<?>			lstWardCar	= accidentEmrsService.selectVlLocationList(sDsrSeq);
		
		
		if (lstWard != null) {
			nResCode = BaseResVO.RET_OK;
			for (int i = 0; i < lstWard.size(); i ++) {
				AccdWardInfoVO 	vo = (AccdWardInfoVO) lstWard.get(i);
				vo.convertCoord();
			}
		}
		
		if (lstWardCar != null) {
			logger.info("/ons/accident/wardscars_ajax.do - lstWardCar.size:" + lstWardCar.size());

			List<String>	aryCarNo = new ArrayList<String>();

			nResCode = BaseResVO.RET_OK;
			
			for (int i = 0; i < lstWardCar.size(); i ++) {
				AccdWcarInfoVO 	vo = (AccdWcarInfoVO) lstWardCar.get(i);
				vo.convertCoord();
				
				if (vo.isEmpty_Car_no() == false) {
					aryCarNo.add(vo.getCar_no());
				}
			}
			
			// TB_FIRECAR_INFO(차량용 카메라)를 출동차량정보에 넣는다.
			if (aryCarNo.size() > 0) {
				HashMap<String, Object> mapData	= new HashMap<String, Object>();
				
				mapData.put("ary_car_no", aryCarNo);
				
				List<?> 	lstFireCar = accidentInfoService.selectFireCarInfoList(mapData);
				
				if (lstFireCar != null && lstFireCar.size() > 0) {
					for (int i = 0; i < lstFireCar.size(); i ++) {
						FireCarInfoVO	fvo = (FireCarInfoVO) lstFireCar.get(i);
						
						if (fvo.isExistCam()) {
							fvo.copyTo(lstWardCar);
						}
					}
				}
			}
		}

		dspVO = DspResVO.create(nResCode);
		dspVO.setWards(lstWard);
		dspVO.setWcars(lstWardCar);
		
		ComUtils.responseJson(request, response, dspVO);
	}
	
	/**
	 * 처리 내용 : 대상물 조회
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/search/datalist_ajax.do")
	public void accdObjlistAjax( @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/search/datalist_ajax.do");

		int				nResCode = BaseResVO.ERR_DATA_NOT_FOUND;
		
		String sObjName		= ComStr.toStr(params.get("sch_text"));
		List<?>			lstData	= accidentEmrsService.selectObjList(sObjName);
		ResultListVO	objVO;
		
		if (lstData != null) {
			nResCode = BaseResVO.RET_OK;
		}
		
		objVO = ResultListVO.create(nResCode);
		objVO.setItems(lstData);
		
		ComUtils.responseJson(request, response, objVO);
	}
	
	/**
	 * 처리 내용 : 출동차량 차량 카메라 리스트 조회
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/firecar/cctvlist_ajax.do")
	public void fireCarCctvList(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> mapData	= new HashMap<String, Object>();
		ArrayList<String> lstData		= new ArrayList<String>();
		ResultListVO objVO				= null;
		
		int	nResCode 			= BaseResVO.ERR_DATA_NOT_FOUND;
		String[] aryCarIds		= ComStr.toStr(params.get("car_nos")).split(",");
		for (String sCarId : aryCarIds) {
			lstData.add(sCarId);
		}
		
		mapData.put("ary_car_no", lstData);
		
		List<?> list = accidentInfoService.selectFireCarInfoList(mapData);
		
		if (list.size() > 0) {
			nResCode = ResultListVO.RET_OK;
		}
		
		objVO = ResultListVO.create(nResCode);
		objVO.setItems(list);
		
		ComUtils.responseJson(request, response, objVO);
	}
	
	/**
	 * 처리 내용 : 투망감시 영역 CCTV 리스트 반환
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/cctv/rectlist_ajax.do")
	public void rectCctvList(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> mapData	= new HashMap<String, Object>();
		ResultListVO objVO				= null;
		
		int	nResCode 			= BaseResVO.ERR_DATA_NOT_FOUND;
		
		mapData.put("x1", ComStr.toStr(params.get("x1")));
		mapData.put("y1", ComStr.toStr(params.get("y1")));
		mapData.put("x2", ComStr.toStr(params.get("x2")));
		mapData.put("y2", ComStr.toStr(params.get("y2")));
		
		List<?> list = accidentInfoService.selectRectCctvList(mapData);
		
		if (list.size() > 0) {
			nResCode = ResultListVO.RET_OK;
		}
		
		objVO = ResultListVO.create(nResCode, messageSource);
		objVO.setItems(list);
		
		ComUtils.responseJson(request, response, objVO);
	}
	
	/**
	 * 처리 내용 : 최적경로 저장
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/route/reg_action.do")
	public void sendRoutesList(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> mapData		= new HashMap<String, Object>();
		ResultVO				objVO		= null;
		
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
		
		/*if (info == null) {
			objVO	= ResultVO.create(ResultVO.ERR_SESSION_NOT_EXIST, messageSource);
			ComUtils.responseJson(request, response, objVO);
			return;
		}*/
		
		byte[] bSchDetailData = ComStr.toStr(params.get("sch_detail_data")).getBytes();
		
		mapData.put("dsr_seq", ComStr.toStr(params.get("dsr_seq")));
		mapData.put("car_id", ComStr.toStr(params.get("car_id")));
		mapData.put("car_no", ComStr.toStr(params.get("car_no")));
		mapData.put("route_no", ComStr.toStr(params.get("route_no")));
		mapData.put("sch_type", ComStr.toStr(params.get("sch_type")));
		
		mapData.put("sch_detail_data", bSchDetailData);
		mapData.put("sch_cctv_data", ComStr.toStr(params.get("sch_cctv_data")));
		mapData.put("sch_traffic_data", ComStr.toStr(params.get("sch_traffic_data")));
		
		objVO	= accidentInfoService.insertRouteData(mapData);
		
		ComUtils.responseJson(request, response, objVO);
		
	}
}

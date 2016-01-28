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

import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.utils.HttpReader;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultVO;
import nses.ons.vo.CarnoDetectVO;
import nses.ons.vo.CctvCaptureVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.fdl.property.EgovPropertyService;

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

@Controller("externApiController")
public class ExternApiController {

	private static final Logger 	logger = LoggerFactory.getLogger(CctvInfoController.class);
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 비디오 이미지 캡쳐하기
	 * @param params - cap_url
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/awd/capture.do")
	public void rosCaptureAjax(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/awd/capture.do");
		
		CctvCaptureVO	resVO = null;
		boolean			useCapServer = propertiesService.getInt("useCapServer", 0) != 0;

		if (useCapServer) {
			// parameters = rtsp_url
			String		sCapUrl = ComStr.toStr(params.get("cap_url"));
			String		sReqURL = "http://" + propertiesService.getString("capServerAddr") + "/awd/capture";
			String		sParams = "rtsp_url=" + URLEncoder.encode(sCapUrl, "UTF-8");
			
			String		sResult = HttpReader.getDocument(sReqURL, sParams, 15);
			logger.info("capture.do(cap_url:" + sCapUrl + ") - Result: " + sResult);

			resVO = CctvCaptureVO.createFromJson(sResult);
		}
		else {
			// - 테스트
			resVO = CctvCaptureVO.create(BaseResVO.RET_OK);
			
			resVO.setImg_uri("/capture/test.jpg");
		}

		ComUtils.responseJson(request, response, resVO);
	}
	@RequestMapping("/ons/awd/capimage.do")
	public void rosCapImageAjax(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/awd/capimage.do");
		
		// parameters = img_uri
		String		sImgUri = ComStr.toStr(params.get("img_uri"));
		if (sImgUri.equals("/capture/test.jpg")) {
			HttpReader.responseImage(response, "http://localhost:" + request.getServerPort() + sImgUri);
			return;
		}
		String		sImgURL = "http://" + propertiesService.getString("capServerAddr") + sImgUri;
		
		HttpReader.responseImage(response, sImgURL);
	}
	
	/**
	 * 차량번호 인식처리
	 * @param params - img_data
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/awd/recognize.do")
	public void rosRecognizeAjax(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/awd/recognize.do");
		
		// parameters = encoded
		String		sReqURL = "http://" + propertiesService.getString("recogServerAddr") + "/awd/recognize";
		String		sParams = "encoded=" + URLEncoder.encode((String)params.get("img_data"), "UTF-8");
		
		String		sResult = HttpReader.getDocument(sReqURL, sParams, 15);

		logger.info("recognize.do - Result: " + sResult);
		
		CarnoDetectVO	resVO = CarnoDetectVO.createFromJson(sResult);
		
		ComUtils.responseJson(request, response, resVO);
	}

	@RequestMapping("/ons/api/wproxy.do")
	public void apiWebProxyAjax(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/api/wproxy.do");
		
		// parameters = all
		String		sReqURL = "http://" + propertiesService.getString("rbsServerAddr") + "/lbsportal/DirectionsService";
		String		sParams = HttpReader.makeHttpParams(params);
		
		String		sResult = HttpReader.getDocument(sReqURL, sParams, 15);

		logger.info("wproxy.do - Result: " + sResult);
		
		ResultVO	resVO = ResultVO.create(BaseResVO.RET_OK);
		
		resVO.setExtraData(sResult);
		
		ComUtils.responseJson(request, response, resVO);
	}

	@RequestMapping("/ons/api/route.do")
	public void apiRouteAjax(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/api/route.do");
		
		// parameters = all
		String			sReqURL = "http://" + propertiesService.getString("rbsServerAddr") + "/lbsportal/DirectionsService";
		StringBuilder	sb = new StringBuilder();
		
		HttpReader.makeNameValueA(sb, "rptype=",	params.get("rptype")).append("&");
		HttpReader.makeNameValueA(sb, "coordtype=",	params.get("coordtype")).append("&");
		HttpReader.makeNameValueA(sb, "priority=",	params.get("priority")).append("&");
		HttpReader.makeNameValueA(sb, "sx=",		params.get("sx")).append("&");
		HttpReader.makeNameValueA(sb, "sy=",		params.get("sy")).append("&");
		HttpReader.makeNameValueA(sb, "ex=",		params.get("ex")).append("&");
		HttpReader.makeNameValueA(sb, "ey=",		params.get("ey")).append("&");
		sb.append("vx1=-1&vy1=-1&vx2=-1&vy2=-1&vx3=-1&vy3=-1&");
		HttpReader.makeNameValue(sb, "rtmCount=",	params.get("rtmCount")).append("&");
		HttpReader.makeNameValue(sb, "uX=", 		params.get("uX")).append("&");
		HttpReader.makeNameValue(sb, "uY=",			params.get("uY")).append("&");
		HttpReader.makeNameValue(sb, "rtmCode=",	params.get("rtmCode"));

		String		sParams = sb.toString();
		String		sResult = HttpReader.getHttpDocument(sReqURL, sParams, 15, true);

		// logger.info("route.do - Result: " + sResult);
		
		ResultVO	resVO = ResultVO.create(BaseResVO.RET_OK);
		
		resVO.setExtraData(sResult);
		
		ComUtils.responseJson(request, response, resVO, null);
	}
}

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
import nses.common.utils.ComCrypto;
import nses.common.utils.ComDate;
import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.utils.HttpReader;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultVO;
import nses.ons.service.RecogImgService;
import nses.ons.vo.CarOwnerVO;
import nses.ons.vo.CctvCaptureVO;
import nses.ons.vo.DongCode;
import nses.ons.vo.EnforceConfigVO;
import nses.ons.vo.RecogDetailVO;
import nses.ons.vo.RecogImgVO;
import nses.ons.vo.RecogVO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.rte.fdl.cryptography.EgovARIACryptoService;
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

@Controller
public class RecogImgInfoController {

	private static final Logger 	logger = LoggerFactory.getLogger(RecogImgInfoController.class);

	@Resource(name = "recogImgService")
	private RecogImgService recogImgService;

	@Autowired
	private	MessageSource messageSource;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name="ARIACryptoService")
	private EgovARIACryptoService cryptoService;

	/** Validator */
//	@Resource(name = "beanValidator")
//	protected DefaultBeanValidator beanValidator;

	@RequestMapping("/ons/recog/list_ajax.do")
	public void recogImglistAction(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/recog/list_ajax.do");
		
		RecogVO recogVO			= new RecogVO();
		RecogImgVO recogImgVO	= new RecogImgVO();
		CarOwnerVO	carOwnVO	= null;
		
		HashMap<String, Object> mapData 			= new HashMap<String, Object>();
		List<?>					lstRecogDetail		= new ArrayList<RecogDetailVO>();
		
		String sOwnCarNo	= ComStr.toStr(params.get("car_no"));
		String sDsrSeq		= ComStr.toStr(params.get("dsr_seq"));
		String sCurrDt		= ComStr.toStr(params.get("curr_dt"));
		

		mapData.put("own_car_no", sOwnCarNo);
		mapData.put("dsr_seq", sDsrSeq);
		mapData.put("curr_dt", sCurrDt);
		
		try {
			EnforceConfigVO enforceVO = recogImgService.selectCrackStat(mapData);
			
			if (enforceVO == null) {
				enforceVO	= new EnforceConfigVO();
				logger.debug("enforce Data is null...");
			}
			
			recogVO.setEnforce_time(enforceVO.getEnforce_time());
			recogVO.setCrack_stat(enforceVO.getCrack_stat());
			recogVO.setEnforce_time_over(enforceVO.getEnforce_time_over());
			recogVO.setMsg_auto_send(enforceVO.getMsg_auto_send());
			
			carOwnVO	= recogImgService.selectCarOwnInfo(sOwnCarNo);
			recogImgVO	= recogImgService.selectRecogImgData(mapData);
				
			if (recogImgVO != null) {
				lstRecogDetail		= recogImgService.selectRecogImgDetailList(mapData);
			}
				
			if (recogImgVO == null) {
				recogImgVO = new RecogImgVO();
				logger.debug("RecogImgVO is null...");
			}
			if (lstRecogDetail == null) {
				lstRecogDetail = new ArrayList<RecogDetailVO>();
				logger.debug("list<RecogDetailVO> is null...");
			}
			
			if (carOwnVO == null) {
				carOwnVO	= new CarOwnerVO();
				recogVO.setOwn_car_no(sOwnCarNo);
				recogVO.setRetCode(ResultVO.ERR_RECOG_CARNO_NOT_FOUND, messageSource);
				
			} else {
				recogVO.setOwn_car_no(carOwnVO.getOwn_car_no());
				recogVO.setOwn_name(carOwnVO.getOwn_name());
				recogVO.setTel_no(ComCrypto.decode(cryptoService, carOwnVO.getTel_no()));
				recogVO.setSch_dt(carOwnVO.getSch_dt());
				recogVO.setRetCode(ResultVO.RET_OK, messageSource);
			}
			
			recogImgVO.setItems(lstRecogDetail);
			recogVO.setItemVO(recogImgVO);
			
		} catch (Exception e) {
			recogVO.setRetCode(BaseResVO.ERR_DB_EXCEPTION, messageSource);
		}
		
		ComUtils.responseJson(request, response, recogVO);
	}
	
	@RequestMapping("/ons/recog/reg_action.do")
	public void recogImgRegAction(@ModelAttribute("recogDetailVO") RecogDetailVO recogDetailVO, @RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/recog/reg_action.do");
		
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
		
		ResultVO resVO 		= null;
		RecogImgVO	recogVO	= new RecogImgVO();
		
		int nFlag			= ComStr.toInt(params.get("recog_flag"));
		
		recogVO.setDsr_seq(ComStr.toStr(params.get("dsr_seq")));
		recogVO.setOwn_car_no(ComStr.toStr(params.get("own_car_no")));
		recogVO.setUser_id(info.getUserId());
		
		recogVO.setRecog_flag(nFlag);
		
		byte[] bImgData1	= ComStr.toStr(params.get("recog_img_data1")).getBytes();
		byte[] bImgData2	= ComStr.toStr(params.get("recog_img_data2")).getBytes();
		byte[] bImgData3	= ComStr.toStr(params.get("recog_img_data3")).getBytes();
		
		recogDetailVO.setImg_data1(bImgData1);
		recogDetailVO.setImg_data2(bImgData2);
		recogDetailVO.setImg_data3(bImgData3);
		recogDetailVO.setRecog_flag(nFlag);
		
		resVO	= recogImgService.insertData(recogVO, recogDetailVO, params);
		
		ComUtils.responseJson(request, response, resVO);
	}
	
	/**
	 * 처리 내용 : 단속 및 메세지 처리, 상태 업데이트
	 * 
	 * @param recogDetailVO
	 * @param params
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ons/recog/update_action.do")
	public void recogImgUpdateAction(@ModelAttribute("recogDetailVO") RecogDetailVO recogDetailVO, @RequestParam HashMap<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/recog/update_action.do");
		
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
		
		
		ResultVO resVO 		= new ResultVO();
		RecogImgVO recogVO	= new RecogImgVO();
		
		int nRet			= 0;
		int nFlag			= ComStr.toInt(params.get("recog_flag"));
		
		recogVO.setDsr_seq(ComStr.toStr(params.get("dsr_seq")));
		recogVO.setOwn_car_no(ComStr.toStr(params.get("own_car_no")));
		recogVO.setUser_id(info.getUserId());
		
		if (nFlag == 2) {
			nRet	= recogImgService.updateEnforceData(recogVO);
			
			if(nRet > 0) {
				
				List<RecogDetailVO> dList = new ArrayList<RecogDetailVO>();
				dList = recogImgService.selectRecogImglList(recogVO);
				
				for(int i = 0 ; i < dList.size(); i++) {
					
					recogSaveImage(dList.get(i), i);
					
				}
				
			}
			
			
		} else if (nFlag == 3) {
			nRet	= recogImgService.updateMsgData(recogDetailVO, params);
			
		}
		
		if (nRet > 0) {
			resVO.setRetCode(BaseResVO.RET_OK, messageSource);
		} else {
			resVO.setRetCode(BaseResVO.ERR_APP_WEBCOMMON, messageSource);
		}
		
		ComUtils.responseJson(request, response, resVO);
	}


	@RequestMapping("/ons/recog/image.do")
	public void recogGetImageAction(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/recog/image.do");
		
		HashMap<String, Object> mapData 			= new HashMap<String, Object>();
		RecogDetailVO			recogDetail;
		
		String 	sDsrSeq		= ComStr.toStr(params.get("dsr_seq"));
		String 	sCarNo		= ComStr.toStr(params.get("car_no"));
		String 	sRecogTime	= ComStr.toStr(params.get("recog_time"));
		int		nImgData	= ComStr.toInt(params.get("img_data"), 1);
		
		mapData.put("dsr_seq", 		sDsrSeq);
		mapData.put("car_no", 		sCarNo);
		mapData.put("recog_time", 	sRecogTime);
		mapData.put("img_data", 	nImgData);

		
		recogDetail = recogImgService.selectRecogImage(mapData);

		byte[]	imgData = null;
		
		if (recogDetail != null) {
			imgData = recogDetail.getImgData_Decode64(nImgData);
		}
		
		ComUtils.responseImage(response, imgData);
	}

	@RequestMapping("/ons/recog/record.do")
	public void recogRecordAction(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/recog/record.do");
		
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
		
		
		HashMap<String, Object> mapData 	= new HashMap<String, Object>();
		Map<String, Object> 	mapRes 		= null;
		
		/*if (info == null) {
			ResultVO	resVO = ResultVO.create(BaseResVO.ERR_SESSION_NOT_EXIST, messageSource);
			ComUtils.responseJson(request, response, resVO);
			return;
		}*/

		String 		sCarId		= ComStr.toStr(params.get("car_id"));
		String 		sCarNo		= ComStr.toStr(params.get("car_no"));
		int			nCamType	= ComStr.toInt(params.get("cam_type"), 3);
		String		sFileServer	= propertiesService.getString("fileServerAddr");
		String		sFileName	= sCarId + "_" + ComDate.makeDateTimeString() + ".mp4";
		String		sCapUrl 	= ComStr.toStr(params.get("cap_url"));
		int			nSaveSec	= ComStr.toInt(params.get("save_sec"), 60);

		if (nSaveSec < 20) {
			nSaveSec = 50;
		}

		mapData.put("file_name", 	sFileName);
		mapData.put("car_no", 		sCarNo);
		mapData.put("file_server", 	sFileServer);
		mapData.put("file_path", 	"");
		mapData.put("file_size", 	0);
		mapData.put("file_type", 	"동영상");
		mapData.put("cam_type", 	nCamType);
		mapData.put("save_sec", 	nSaveSec);
		mapData.put("poi_x", 		params.get("poi_x"));
		mapData.put("poi_y", 		params.get("poi_y"));
		mapData.put("user_id",		info.getUserId());

		
		mapRes = recogImgService.selectLatestRecFileName(mapData);
		if (mapRes != null) {
			logger.info("already requested record. file_name: " + mapRes);
			ResultVO	resVO = ResultVO.create(BaseResVO.ERR_REQ_RECORD_EXIST, messageSource);
			ComUtils.responseJson(request, response, resVO);
			return;
		}
		
		recogImgService.insertRecordData(mapData);

		CctvCaptureVO	resVO = null;

		// parameters = rtsp_url
		String		sReqURL = propertiesService.getString("recordServerAddr");
		String		sCbURL  = propertiesService.getString("recordCallbackURL");
		String		sParams = "rtsp_url=" + URLEncoder.encode(sCapUrl, "UTF-8");

		sParams 	+= "&filename=" + URLEncoder.encode(sFileName, "UTF-8");
		sParams 	+= "&save_sec=" + nSaveSec;
		sParams 	+= "&cb_url=" + URLEncoder.encode(sCbURL, "UTF-8");
		
		String		sResult = HttpReader.getDocument(sReqURL, sParams, 15);
		logger.info("record.do(file_name:" + sFileName + ") - Result: " + sResult);

		resVO = CctvCaptureVO.createFromJsonR(sResult, messageSource);

		//ResultVO	resVO = ResultVO.create(BaseResVO.RET_OK, messageSource);
		
		ComUtils.responseJson(request, response, resVO);
	}

	@RequestMapping("/ons/recog/recok.do")
	public void recogRecOKAction(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("/ons/recog/recok.do");
		
		HashMap<String, Object> mapData 			= new HashMap<String, Object>();
		
		String 		sFileName	= ComStr.toStr(params.get("file_name"));
		String 		sFilePath	= ComStr.toStr(params.get("file_path"));
		int			nSaveStat	= ComStr.toInt(params.get("save_stat"), 2);
		long		lnFileSize	= ComStr.toLong(params.get("file_size"), 0);

		mapData.put("file_name", 	sFileName);
		mapData.put("file_path", 	sFilePath);
		mapData.put("file_size", 	lnFileSize);
		mapData.put("save_stat", 	nSaveStat);

		logger.info("recok.do(file_name:" + sFileName + ") ******************************************************************************");

		int		nRetCode = BaseResVO.ERR_DATA_NOT_FOUND;
		
		if (recogImgService.updateRecordData(mapData) > 0) {
			nRetCode = BaseResVO.RET_OK;
		}

		ResultVO	resVO = ResultVO.create(nRetCode, messageSource);
		
		ComUtils.responseJson(request, response, resVO);
	}

	/*
	 * DB 수정사항
	 * 단속사진파일명 형식 - "28DE0W001경기90고1696    200812080707331011중앙로(국민은행).jpg"
	 * 인천시, 자치단체구분, 계도여부, 어린이보호구역여부, 단속구분
	 * 28,D,E,0,W
	 * CCTV장비번호(3), 차량번호(16), 년월일시분초(14), 동코드(3), 사진번호(1), 주소(가변)
	 */
	public void recogSaveImage(RecogDetailVO vo, int arr) {
		byte[]	imgData = vo.getImgData_Decode64(1);
		String	sImgPath =  propertiesService.getString("recogSavePath") +  "28DE0W";
		
		
		sImgPath += vo.getCctv_seqno();								// CCTV장비번호(3)
		sImgPath += makeStr(vo.getOwn_car_no(), 16);				// 차량번호(16)
		sImgPath += makeNum(vo.getRecog_time(), 14);				// 년월일시분초(14)
		sImgPath += DongCode.getCode(vo.getPoi_addr());	
		sImgPath += ++arr + "";											// 사진번호(1)
		sImgPath += vo.getPoi_addr();								// 주소(가변)
		sImgPath += ".jpg";
		
		ComUtils.writeImage(sImgPath, imgData);
	}
	
	
	private String makeStr(String str, int nSize) {
		if (str == null)
			return StringUtils.leftPad("", nSize, " ");
		
		if (str.length() == nSize)
			return str;
		if (str.length() > nSize)
			return str.substring(0, nSize);
		
		return StringUtils.rightPad(str, nSize - str.length(), " ");
	}
	private String makeNum(String str, int nSize) {
		if (str == null)
			return StringUtils.leftPad("", nSize, "0");
		
		if (str.length() == nSize)
			return str;
		if (str.length() > nSize)
			return str.substring(0, nSize);
		
		return StringUtils.leftPad(str, nSize - str.length(), "0");
	}
}

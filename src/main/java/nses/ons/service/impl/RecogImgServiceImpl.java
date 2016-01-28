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
package nses.ons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import nses.common.utils.ComStr;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultVO;
import nses.ons.dao.RecogImgDAO;
import nses.ons.service.RecogImgService;
import nses.ons.vo.CarOwnerVO;
import nses.ons.vo.EnforceConfigVO;
import nses.ons.vo.MsgDataVO;
import nses.ons.vo.RecogDetailVO;
import nses.ons.vo.RecogImgVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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

@Service("recogImgService")
public class RecogImgServiceImpl extends EgovAbstractServiceImpl implements RecogImgService {

	private static final Logger logger = LoggerFactory.getLogger(RecogImgServiceImpl.class);

	@Resource(name="recogImgDAO")
	private RecogImgDAO recogImgDAO;

	@Override
	public List<?> selectListData(RecogImgVO vo) throws Exception {
		return recogImgDAO.selectListData(vo);
	}

	@Override
	public CarOwnerVO selectCarOwnInfo(String sCarNo) throws Exception {
		return recogImgDAO.selectCarOwnInfo(sCarNo);
	}

	@Override
	public RecogImgVO selectRecogImgData(HashMap<String, Object> map) throws Exception {
		return recogImgDAO.selectRecogImgData(map);
	}

	@Override
	public List<?> selectRecogImgDetailList(HashMap<String, Object> map) throws Exception {
		return recogImgDAO.selectRecogImgDetailList(map);
	}

	@Override
	public RecogDetailVO selectRecogImage(HashMap<String, Object> map) throws Exception {
		return recogImgDAO.selectRecogImage(map);
	}

	
	@Override
	public int updateRecogImgData(RecogImgVO vo) throws Exception {
		return recogImgDAO.updateRecogImgData(vo);
	}

	@Override
	public EnforceConfigVO selectCrackStat(HashMap<String, Object> mapData) throws Exception {
		return recogImgDAO.selectCrackStat(mapData);
	}

	@Override
	public ResultVO insertData(RecogImgVO vo, RecogDetailVO detailVO, Map<String, Object> params) throws Exception {
		ResultVO resVO			= null;
		
		int nRet	= 0;
		try {
			nRet	= recogImgDAO.updateData(vo);
			
			if (nRet <= 0) {
				recogImgDAO.insertData(vo);
			}
			recogImgDAO.insertDetailData(detailVO);
			
			resVO = ResultVO.create(BaseResVO.RET_OK);
		} catch (DataAccessException e) {
			logger.error("insertData(), RecogImg Insert Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		} catch (Exception e) {
			logger.error("insertData(), RecogImg Insert Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		}
		
        return resVO;
	}
	
	@Override
	public int updateEnforceData(RecogImgVO vo) throws Exception {
		return recogImgDAO.updateEnforceData(vo);
	}

	@Override
	public int updateMsgData(RecogDetailVO vo, HashMap<String, Object> map) throws Exception {
		EnforceConfigVO enforceVO		= null;
		MsgDataVO msgVO					= null;
		
		String sMsgConts		= "";
		String sOrgUserTel		= "";
		String sSenderTel		= "";
		String sOwnCarTelno		= ComStr.toStr(map.get("own_tel_no")).replace("-", "");
		System.out.println("sOwnCarTelno : " + sOwnCarTelno);
		int nRet				= 0;
		
		nRet	= recogImgDAO.updateMsgData(vo);
		System.out.println("nRet : " + nRet);
		if (nRet > 0) {
			enforceVO		= recogImgDAO.selectMsgConfigData();
			msgVO			= new MsgDataVO();
			
			if (enforceVO != null) {
				sMsgConts	= ComStr.toStr(enforceVO.getMsg_contents().trim());
				sOrgUserTel	= ComStr.toStr(enforceVO.getOrg_user_tel());
				sSenderTel	= ComStr.toStr(enforceVO.getSender_tel());
				
			} else {
				enforceVO = new EnforceConfigVO();
			}
			System.out.println("sOrgUserTel : " + sOrgUserTel.length());
			if (sOrgUserTel.length() > 0) {
				msgVO.setCall_to(sOwnCarTelno);
				msgVO.setCall_from(sOrgUserTel);
				msgVO.setSms_txt(sMsgConts);
				msgVO.setMsg_type(4);
				
				recogImgDAO.insertMsgData(msgVO);
			}
			System.out.println("sOwnCarTelno : " + sOwnCarTelno.length());
			if (sOwnCarTelno.length() > 0) { 
				msgVO.setCall_to(sOwnCarTelno);
				msgVO.setCall_from(sSenderTel);
				msgVO.setSms_txt(ComStr.toStr(sMsgConts));
				msgVO.setMsg_type(4);
				
				recogImgDAO.insertMsgData(msgVO);
			}
		}
		
		return nRet;
	}

	@Override
	public void insertRecordData(Map<String, Object> params) throws Exception {
		recogImgDAO.insertRecordData(params);
	}
	
	@Override
	public int updateRecordData(Map<String, Object> params) throws Exception {
		return recogImgDAO.updateRecordData(params);
	}

	@Override
	public Map<String, Object> selectLatestRecFileName(Map<String, Object> params) throws Exception {
		return recogImgDAO.selectLatestRecFileName(params);
	}

	@Override
	public List<RecogDetailVO> selectRecogImglList(RecogImgVO recogVO)
			throws Exception {
		return recogImgDAO.selectRecogImglList(recogVO);
	}
	
	
}

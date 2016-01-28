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

import javax.annotation.Resource;

import nses.common.vo.ResultVO;
import nses.ons.dao.AccidentEmrsDAO;
import nses.ons.dao.AccidentInfoDAO;
import nses.ons.service.AccidentInfoService;
import nses.ons.vo.AccidentInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

@Service("accidentInfoService")
public class AccidentInfoServiceImpl extends EgovAbstractServiceImpl implements AccidentInfoService {

	private static final Logger logger = LoggerFactory.getLogger(AccidentInfoServiceImpl.class);
	
	@Resource(name="accidentInfoDAO")
	private AccidentInfoDAO accidentInfoDAO;

	@Resource(name="accidentemrsDAO")
	private AccidentEmrsDAO accidentEmrsDAO;
	
	@Autowired
	private	MessageSource messageSource;
	
	@Override
	public List<?> selectListData(AccidentInfoVO vo) throws Exception {
		logger.debug("AccidentInfoServiceImpl selectListData...");
		return accidentInfoDAO.selectListData(vo);
	}

	@Override
	public void insertListData(List<AccidentInfoVO> lstData) throws Exception {
		try {
			accidentInfoDAO.insertData(lstData);
			
		} catch (DataAccessException e) {
			logger.error("insertData(), AccidentInfo Insert Error... ", e);
		} catch (Exception e) {
			logger.error("insertData(), AccidentInfo Insert Error... ", e);
		}
	}

	@Override
	public List<?> selectWardList(String dsr_seq) throws Exception {
		return accidentEmrsDAO.selectWardList(dsr_seq);
	}

	@Override
	public List<?> selectFireCarInfoList(HashMap<String, Object> map) throws Exception {
		return accidentInfoDAO.selectFireCarInfoList(map);
	}

	@Override
	public List<?> selectRectCctvList(HashMap<String, Object> map) throws Exception {
		return accidentInfoDAO.selectRectCctvList(map);
	}

	@Override
	public ResultVO insertRouteData(HashMap<String, Object> map) throws Exception {
		ResultVO resVO		= new ResultVO();
		
		try {
			accidentInfoDAO.insertRouteData(map);
			resVO.setRetCode(ResultVO.RET_OK, messageSource);
			
		} catch (DataAccessException e) {
			logger.error("insertData(), RouteData Insert Error... ", e);
			resVO.setRetCode(ResultVO.ERR_APP_WEBCOMMON, messageSource);
		} catch (Exception e) {
			logger.error("insertData(), RouteData Insert Error... ", e);
			resVO.setRetCode(ResultVO.ERR_APP_WEBCOMMON, messageSource);
		}
		
		return resVO;
	}

	@Override
	public void updateListData(List<AccidentInfoVO> list) throws Exception {
		accidentInfoDAO.updateListData(list);
	}

	@Override
	public int selectData(AccidentInfoVO vo) throws Exception {
		return accidentInfoDAO.selectData(vo);
	}

	
	
}

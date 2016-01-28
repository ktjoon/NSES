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

import java.util.List;

import javax.annotation.Resource;

import nses.common.utils.ComDate;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultVO;
import nses.ons.dao.MsgInfoDAO;
import nses.ons.service.MsgInfoService;
import nses.ons.vo.MsgInfoVO;

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

@Service("msgInfoService")
public class MsgInfoServiceImpl extends EgovAbstractServiceImpl implements MsgInfoService {

	private static final Logger logger = LoggerFactory.getLogger(MsgInfoServiceImpl.class);

	@Resource(name="msgInfoDAO")
	private MsgInfoDAO msgInfoDAO;

	@Override
	public List<?> selectListData(MsgInfoVO vo) throws Exception {
		return msgInfoDAO.selectListData(vo);
	}
	
	@Override
	public List<?> selectSMSList(MsgInfoVO vo) throws Exception {
		return msgInfoDAO.selectSMSList(vo);
	}

	@Override
	public ResultVO insertData(MsgInfoVO vo) throws Exception {
		ResultVO resVO		= new ResultVO();
		
		try {
			msgInfoDAO.insertData(vo);
			
			resVO = ResultVO.create(BaseResVO.RET_OK);
			resVO.setExtraData(ComDate.makeDispDateTimeString());
			
		} catch (DataAccessException e) {
			logger.error("insertData(), MsgInfo Insert Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		} catch (Exception e) {
			logger.error("insertData(), MsgInfo Insert Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		}
		
        return resVO;
	}

	@Override
	public ResultVO insertCctvData(MsgInfoVO vo) throws Exception {
		ResultVO resVO		= new ResultVO();
		
		try {
			msgInfoDAO.insertCctvData(vo);
			resVO = ResultVO.create(BaseResVO.RET_OK);
			
		} catch (Exception e) {
			logger.error("insertCctvData(), MsgInfo Cctv Insert Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		}
		
        return resVO;
	}

	

}

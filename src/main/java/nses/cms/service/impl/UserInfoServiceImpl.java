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
package nses.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import nses.cms.dao.UserInfoDAO;
import nses.cms.service.UserInfoService;
import nses.cms.vo.UserInfoVO;
import nses.common.vo.BasePageVO;
import nses.common.vo.BaseResVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 
 * @Modification Information
 * @
 * @ 수정일			수정자              수정내용
 * @ ---------		---------		-------------------------------
 * @ 2014.12.23						최초생성
 *
 * @author 	개발프레임웍크 실행환경 개발팀
 * @since 	2014.12.23
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSES All right reserved.
 */

@Service("userinfoService")
public class UserInfoServiceImpl extends EgovAbstractServiceImpl implements UserInfoService {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

	@Resource(name="userinfoDAO")
	private UserInfoDAO userinfoDAO;

	@Override
	public void insertData(UserInfoVO vo) throws Exception {
		userinfoDAO.insertData(vo);
	}

	@Override
	public int updateData(UserInfoVO vo) throws Exception {
		int nRetCode	= 0;
		
		try {
			userinfoDAO.updateData(vo);
			nRetCode = BaseResVO.RET_OK;
			
		} catch (DataAccessException e) {
			logger.error("updateData(), UserInfo Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		} catch (Exception e) {
			logger.error("updateData(), UserInfo Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		}
		return nRetCode;
	}

	@Override
	public int deleteData(UserInfoVO vo) throws Exception {
		int nRetCode	= 0;
		
		try {
			userinfoDAO.deleteData(vo);
			nRetCode = BaseResVO.RET_OK;
			
		} catch (DataAccessException e) {
			logger.error("deleteData(), UserInfo Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		} catch (Exception e) {
			logger.error("deleteData(), UserInfo Delete Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		}
		return nRetCode;
	}

	@Override
	public UserInfoVO selectData(UserInfoVO vo) throws Exception {
		return userinfoDAO.selectData(vo);
	}

	@Override
	public List<?> selectListData(BasePageVO pageVO) throws Exception {
		return userinfoDAO.selectListData(pageVO);
	}

	@Override
	public int selectListCount(BasePageVO pageVO) throws Exception {
		return userinfoDAO.selectListCount(pageVO);
	}
}

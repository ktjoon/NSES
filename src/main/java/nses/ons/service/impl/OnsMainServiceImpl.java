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

import javax.annotation.Resource;

import nses.common.vo.BaseResVO;
import nses.ons.dao.UserInfoDAO;
import nses.ons.service.OnsMainService;
import nses.ons.vo.LoginVO;
import nses.ons.vo.UserConfigVO;
import nses.ons.vo.UserInfoVO;

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

@Service("onsMainService")
public class OnsMainServiceImpl extends EgovAbstractServiceImpl implements OnsMainService {

	private static final Logger logger = LoggerFactory.getLogger(OnsMainServiceImpl.class);

	/** UserInfoDAO */
	@Resource(name="userInfoDAO")
	private UserInfoDAO userInfoDAO;

	/**
	 * 사용자 로그인을 처리한다.
	 * @param vo - 사용자 정보가 담긴 LoginVO
	 * @return ResultVO
	 * @exception Exception
	 */
	public UserInfoVO selectUserLogin(LoginVO vo) throws Exception {
		UserInfoVO	retVO = userInfoDAO.selectData(vo);

		logger.info("selectUserLogin(" + vo.getUser_id() + ") - " + retVO);

		return retVO;
	}

	/**
	 * 사용자 설정정보를 반환한다.
	 */
	@Override
	public UserConfigVO selectConfigData(UserConfigVO vo) throws Exception {
		return userInfoDAO.selectConfigData(vo);
	}

	/**
	 * 영상 설정정보를 반환한다.
	 */
	@Override
	public int selectRecSaveData() throws Exception {
		return userInfoDAO.selectRecSaveData();
	}

	
	@Override
	public int insertConfigData(UserConfigVO vo) throws Exception {
		int nRetCode	= 0;
		
		try {
			userInfoDAO.insertConfigData(vo);
			nRetCode = BaseResVO.RET_OK;
			
		} catch (DataAccessException e) {
			logger.error("insertData(), RoadInfo clear Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		} catch (Exception e) {
			logger.error("insertConfigData(), UserConfig Insert Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		}
		return nRetCode;
	}

	@Override
	public int updateConfigData(UserConfigVO vo) throws Exception {
		return userInfoDAO.updateConfigData(vo);
	}

	@Override
	public int updateLoginData(UserConfigVO vo) throws Exception {
		return userInfoDAO.updateLoginData(vo);
	}

}

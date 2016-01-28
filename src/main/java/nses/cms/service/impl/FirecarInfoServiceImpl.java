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
import java.util.Map;

import javax.annotation.Resource;

import nses.cms.dao.FirecarEmrsInfoDAO;
import nses.cms.dao.FirecarInfoDAO;
import nses.cms.service.FirecarInfoService;
import nses.cms.vo.FirecarInfoVO;
import nses.common.vo.BasePageVO;
import nses.common.vo.BaseResVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : FirecarInfoServiceImpl.java
 * @Description : Sample Business Implement Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Service("firecarinfoService")
public class FirecarInfoServiceImpl extends EgovAbstractServiceImpl implements FirecarInfoService {

	private static final Logger logger = LoggerFactory.getLogger(FirecarInfoServiceImpl.class);

	/** FirecarInfoDAO */
	@Resource(name="firecarinfoDAO")
	private FirecarInfoDAO firecarInfoDAO;
	
	/** FirecarEmrsInfoDAO */
	@Resource(name="firecaremrsinfoDAO")
	private FirecarEmrsInfoDAO firecarEmrsInfoDAO;
	
	@Override
	public int insertData(FirecarInfoVO vo) throws Exception {
		int nRetCode	= 0;
		
		try {
			firecarInfoDAO.insertData(vo);
			nRetCode = BaseResVO.RET_OK;
			
		} catch (DataAccessException e) {
			logger.error("insertData(), FirecarInfo Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		} catch (Exception e) {
			logger.error("insertData(), FirecarInfo Insert Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		}
		
        return nRetCode;
	}

	@Override
	public int updateData(FirecarInfoVO vo) throws Exception {
		int nRetCode	= 0;
		
		try {
			firecarInfoDAO.updateData(vo);
			nRetCode = 200;
			
		} catch (DataAccessException e) {
			logger.error("updateData(), FirecarInfo Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		} catch (Exception e) {
			logger.error("updateData(), FirecarInfo Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		}
		return nRetCode;
	}

	@Override
	public int deleteData(FirecarInfoVO vo) throws Exception {
		return firecarInfoDAO.deleteData(vo);
	}

	@Override
	public FirecarInfoVO selectData(FirecarInfoVO vo) throws Exception {
		return firecarInfoDAO.selectData(vo);
	}

	@Override
	public List<?> selectListData(BasePageVO pageVO) throws Exception {
		return firecarInfoDAO.selectListData(pageVO);
	}

	@Override
	public int selectListCount(BasePageVO pageVO) {
		return firecarInfoDAO.selectListCount(pageVO);
	}

	@Override
	public Map<String, String> selectCarId(String car_no) throws Exception {
		return firecarEmrsInfoDAO.selectCarId(car_no);
	}
}

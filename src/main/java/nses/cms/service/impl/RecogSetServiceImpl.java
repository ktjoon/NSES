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


import javax.annotation.Resource;

import nses.cms.dao.RecogSetDAO;
import nses.cms.service.RecogSetService;
import nses.cms.vo.RecogSetVO;
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

@Service("recogsetService")
public class RecogSetServiceImpl extends EgovAbstractServiceImpl implements RecogSetService {

	private static final Logger logger = LoggerFactory.getLogger(RecogSetServiceImpl.class);

	@Resource(name="recogsetDAO")
	private RecogSetDAO recogsetDAO;

	@Override
	public int updateData(RecogSetVO vo) throws Exception {
		int nRetCode	= 0;
		
		try {
			recogsetDAO.updateData(vo);
			nRetCode = BaseResVO.RET_OK;
			
		} catch (DataAccessException e) {
			logger.error("insertData(), RecogSet Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		} catch (Exception e) {
			logger.error("updateData(), RecogSet Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		}
		return nRetCode;
	}

	@Override
	public RecogSetVO selectData(RecogSetVO vo) throws Exception {
		return recogsetDAO.selectData(vo);
	}
	
}

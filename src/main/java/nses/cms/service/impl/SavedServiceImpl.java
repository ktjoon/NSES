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

import nses.cms.dao.SavedDAO;
import nses.cms.service.SavedService;
import nses.cms.vo.SavedVO;
import nses.common.vo.BasePageVO;

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

@Service("savedService")
public class SavedServiceImpl extends EgovAbstractServiceImpl implements SavedService {

//	private static final Logger LOGGER = LoggerFactory.getLogger(SavedServiceImpl.class);

	@Resource(name="savedDAO")
	private SavedDAO savedDAO;



	@Override
	public SavedVO selectData(SavedVO vo) throws Exception {
		return savedDAO.selectData(vo);
	}
	
	@Override
	public List<?> selectListData(BasePageVO pageVO) throws Exception {
		return savedDAO.selectListData(pageVO);
	}

	@Override
	public int selectListCount(BasePageVO pageVO) throws Exception {
		return savedDAO.selectListCount(pageVO);
	}

	
}

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

import nses.cms.dao.RecogInfoDAO;
import nses.cms.service.RecogInfoService;
import nses.cms.vo.RecogDetailVO;
import nses.cms.vo.RecogListVO;
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

@Service("recoginfoService")
public class RecogInfoServiceImpl extends EgovAbstractServiceImpl implements RecogInfoService {

//	private static final Logger logger = LoggerFactory.getLogger(RecogInfoServiceImpl.class);

	@Resource(name="recoginfoDAO")
	private RecogInfoDAO recoginfoDAO;

	@Override
	public List<?> selectData(RecogDetailVO vo) throws Exception {
		return recoginfoDAO.selectData(vo);
	}
	
	@Override
	public List<RecogListVO> selectListData(BasePageVO pageVO) throws Exception {
		return recoginfoDAO.selectListData(pageVO);
	}

	@Override
	public int selectListCount(BasePageVO pageVO) throws Exception {
		return recoginfoDAO.selectListCount(pageVO);
	}
	
	/**
	 * 메세지 전송 결과 리스트 반환
	 */
	@Override
	public List<?> selectSenderMsgData(BasePageVO pageVO) {
		return recoginfoDAO.selectSenderMsgData(pageVO);
	}
	
	/**
	 * 메세지 전송 결과 리스트 카운트 반환
	 */
	@Override
	public int selectSenderMsgCount(BasePageVO pageVO) {
		return recoginfoDAO.selectSenderMsgCount(pageVO);
	}

	
}

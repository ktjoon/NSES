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

import nses.cms.dao.CctvInfoDAO;
import nses.cms.service.CctvInfoService;
import nses.cms.vo.CctvInfoVO;
import nses.common.vo.BasePageVO;
import nses.common.vo.BaseResVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : CctvnfoServiceImpl.java
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

@Service("cctvinfoService")
public class CctvInfoServiceImpl extends EgovAbstractServiceImpl implements CctvInfoService {

	private static final Logger logger = LoggerFactory.getLogger(CctvInfoServiceImpl.class);

	/** SampleDAO */
	@Resource(name="cctvinfoDAO")
	private CctvInfoDAO cctvInfoDAO;

	@Override
	public int insertData(CctvInfoVO vo) throws Exception {
		int nRetCode	= 0;
		
		try {
			cctvInfoDAO.insertData(vo);
			nRetCode = BaseResVO.RET_OK;
			
		} catch (DataAccessException e) {
			logger.error("insertData(), CctvInfo Insert Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		} catch (Exception e) {
			logger.error("insertData(), CctvInfo Insert Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		}
		
        return nRetCode;
	}

	@Override
	public int updateData(CctvInfoVO vo) throws Exception {
		int nRetCode	= 0;
		
		try {
			cctvInfoDAO.updateData(vo);
			nRetCode = BaseResVO.RET_OK;
			
		} catch (DataAccessException e) {
			logger.error("insertData(), CctvInfo Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		} catch (Exception e) {
			logger.error("updateData(), CctvInfo Update Error... ", e);
			nRetCode = BaseResVO.ERR_APP_WEBCOMMON;
		}
		return nRetCode;
	}

	@Override
	public int deleteData(CctvInfoVO vo) throws Exception {
		return cctvInfoDAO.deleteData(vo);
	}

	@Override
	public CctvInfoVO selectData(CctvInfoVO vo) throws Exception {
		return cctvInfoDAO.selectData(vo);
	}

	@Override
	public List<?> selectListData(BasePageVO pageVO) throws Exception {
		return cctvInfoDAO.selectListData(pageVO);
	}

	@Override
	public int selectListCount(BasePageVO pageVO) {
		return cctvInfoDAO.selectListCount(pageVO);
	}

	@Override
	public void insertExcelData(List<CctvInfoVO> lstData) throws Exception {
		cctvInfoDAO.insertExcelData(lstData);
	}

	@Override
	public int selectCctvName(String cctv_name) throws Exception {
		return cctvInfoDAO.selectCctvName(cctv_name);
	}
	
	
}

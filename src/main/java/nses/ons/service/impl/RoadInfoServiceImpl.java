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
import nses.common.utils.ComStr;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultVO;
import nses.ons.dao.RoadInfoDAO;
import nses.ons.service.RoadInfoService;
import nses.ons.vo.RoadInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

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

@Service("roadInfoService")
public class RoadInfoServiceImpl extends EgovAbstractServiceImpl implements RoadInfoService {

	private static final Logger logger = LoggerFactory.getLogger(RoadInfoServiceImpl.class);

	@Resource(name="roadInfoDAO")
	private RoadInfoDAO roadInfoDAO;

	/** ID Generation */
    @Resource(name="roadInfoIdGnrService")
    private EgovIdGnrService roadInfoIdGnrService;
	
	@Override
	public ResultVO insertData(RoadInfoVO vo) throws Exception {
		ResultVO resVO		= new ResultVO();
		
		int		nSeq		= roadInfoIdGnrService.getNextIntegerId();
		
		vo.setRoad_seq(nSeq);
		
		try {
			roadInfoDAO.insertData(vo);
			
			resVO = ResultVO.create(BaseResVO.RET_OK);
			resVO.setResKey(ComStr.toStr(nSeq));
			resVO.setExtraData(ComDate.makeDispDateTimeString());
			
		} catch (DataAccessException e) {
			logger.error("insertData(), RoadInfo Insert Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		} catch (Exception e) {
			logger.error("insertData(), RoadInfo Insert Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		}
		
        return resVO;
	}

	@Override
	public ResultVO updateData(RoadInfoVO vo) throws Exception {
		ResultVO resVO		= new ResultVO();
		
		try {
			roadInfoDAO.updateData(vo);
			resVO = ResultVO.create(BaseResVO.RET_OK);
			
		} catch (DataAccessException e) {
			logger.error("insertData(), RoadInfo Update Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		} catch (Exception e) {
			logger.error("updateData(), RoadInfo Update Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		}
		
        return resVO;
	}

	@Override
	public ResultVO clearData(RoadInfoVO vo) throws Exception {
		ResultVO resVO		= new ResultVO();
		
		try {
			roadInfoDAO.clearData(vo);
			resVO = ResultVO.create(BaseResVO.RET_OK);
			
		} catch (DataAccessException e) {
			logger.error("insertData(), RoadInfo clear Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		} catch (Exception e) {
			logger.error("updateData(), RoadInfo clear Error... ", e);
			resVO = ResultVO.create(BaseResVO.ERR_ID_NOT_FOUND);
		}
		
        return resVO;
	}

	@Override
	public List<?> selectListData(RoadInfoVO vo) throws Exception {
		return roadInfoDAO.selectListData(vo);
	}

	

}

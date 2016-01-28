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

import nses.cms.dao.CarOwnerDAO;
import nses.cms.dao.OwnerHistDAO;
import nses.cms.service.CarOwnerService;
import nses.cms.vo.CarOwnerVO;
import nses.cms.vo.OwnerHistVO;
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

@Service("carOwnerService")
public class CarOwnerServiceImpl extends EgovAbstractServiceImpl implements CarOwnerService {

	// private static final Logger logger = LoggerFactory.getLogger(CarOwnerServiceImpl.class);

	@Resource(name="carOwnerDAO")
	private CarOwnerDAO carOwnerDAO;
	
	@Resource(name="ownerHistDAO")
	private OwnerHistDAO ownerHistDAO;

	@Override
	public void insertData(CarOwnerVO vo, String sUserId) throws Exception {
        carOwnerDAO.insertData(vo);
        ownerHistDAO.insertData(OwnerHistVO.createVO(vo, OwnerHistVO.INSERT, sUserId));
	}

	@Override
	public int updateData(CarOwnerVO vo, String sUserId) throws Exception {
        ownerHistDAO.insertData(OwnerHistVO.createVO(vo, OwnerHistVO.UPDATE, sUserId));
		return carOwnerDAO.updateData(vo);
	}

	@Override
	public CarOwnerVO selectData(CarOwnerVO vo, String sUserId) throws Exception {
        ownerHistDAO.insertData(OwnerHistVO.createVO(vo, OwnerHistVO.VIEW, sUserId));
		return carOwnerDAO.selectData(vo);
	}
	
	@Override
	public List<CarOwnerVO> selectListData(BasePageVO pageVO, String sUserId) throws Exception {
        ownerHistDAO.insertData(OwnerHistVO.createVO(null, OwnerHistVO.SELECT, sUserId));
		return carOwnerDAO.selectListData(pageVO);
	}

	@Override
	public int selectListCount(BasePageVO pageVO) throws Exception {
		return carOwnerDAO.selectListCount(pageVO);
	}

	@Override
	public int deleteData(CarOwnerVO dataVO, String sUserId) throws Exception {
        ownerHistDAO.insertData(OwnerHistVO.createVO(dataVO, OwnerHistVO.DELETE, sUserId));
		return carOwnerDAO.deleteData(dataVO);
	}


    /**
	 * 차주 정보 히스토리 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
	@Override
	public List<?> selectHistData(BasePageVO pageVO) throws Exception {
		return ownerHistDAO.selectListData(pageVO);
	}

    /**
	 * 차주 정보 히스토리 총 카운트
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	@Override
	public int selectHistCount(BasePageVO pageVO) throws Exception {
		return ownerHistDAO.selectListCount(pageVO);
	}
}

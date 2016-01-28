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
package nses.cms.dao;

import java.util.List;

import nses.cms.vo.CctvInfoVO;
import nses.common.dao.EmrsAbstractDAO;
import nses.common.dao.NsesAbstractDAO;
import nses.common.vo.BasePageVO;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

/**
 * @Class Name : CctvInfoDAO.java
 * @Description : CctvInfo DAO Class
 * @Modification Information
 * @
 * @ 수정일			수정자			수정내용
 * @ ----------		---------   	-------------------------------
 * @ 2009.03.16						최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Repository("cctvinfoDAO")
public class CctvInfoDAO extends NsesAbstractDAO {

	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertData(CctvInfoVO vo) throws Exception {
        return (String)insert("cctvInfo.insertData", vo);
    }

    /**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
    public int updateData(CctvInfoVO vo) throws Exception {
        return update("cctvInfo.updateData", vo);
    }

    /**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
    public int deleteData(CctvInfoVO vo) throws Exception {
        return delete("cctvInfo.deleteData", vo);
    }

    /**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    public CctvInfoVO selectData(CctvInfoVO vo) throws Exception {
        return (CctvInfoVO) select("cctvInfo.selectData", vo);
    }

    /**
	 * 글 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 글 목록
	 * @exception Exception
	 */
    public List<?> selectListData(BasePageVO pageVO) throws Exception {
        return list("cctvInfo.selectListData", pageVO);
    }

    /**
	 * 글 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 글 총 갯수
	 * @exception
	 */
	public int selectListCount(BasePageVO pageVO) {
        return (Integer) select("cctvInfo.selectListCount", pageVO);
    }

	public void insertExcelData(List<CctvInfoVO> lstData) {
		insert("cctvInfo.insertExcelData", lstData);
	}

	public int selectCctvName(String cctv_name) {
		return  (Integer) select("cctvInfo.selectCctvName", cctv_name);
	}

}

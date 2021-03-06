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
package nses.ons.dao;

import java.util.HashMap;
import java.util.List;

import nses.common.dao.NsesAbstractDAO;
import nses.ons.vo.AccidentInfoVO;

import org.springframework.stereotype.Repository;

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

@Repository("accidentInfoDAO")
public class AccidentInfoDAO extends NsesAbstractDAO {

    /**
     * 처리 내용 : 긴급구조 정보 리스트 반환
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public List<?> selectListData(AccidentInfoVO vo) throws Exception {
        return list("accidentInfo.selectListData", vo);
    }

	public void insertData(List<AccidentInfoVO> lstData) throws Exception{
		insert("accidentInfo.insertData", lstData);
	}
	
	/**
	 * 처리 내용 : 소방차 CCTV 정보 반환
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<?> selectFireCarInfoList(HashMap<String, Object> map) throws Exception {
        return list("accidentInfo.selectFireCarInfoList", map);
    }
	
	/**
	 * 처리 내용 : 범위 내 CCTV 리스트 반환
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> selectRectCctvList(HashMap<String, Object> map) throws Exception {
        return list("accidentInfo.selectRectCctvList", map);
    }
	
	/**
	 * 처리 내용 : 최적경로정보 데이터 입력
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void insertRouteData(HashMap<String, Object> map) throws Exception{
		insert("accidentInfo.insertRouteData", map);
	}

	public void updateListData(List<AccidentInfoVO> list) {
		update("accidentInfo.updateListData", list);
	}

	public int selectData(AccidentInfoVO vo) {
		return (Integer) getSqlMapClientTemplate().queryForObject("accidentInfo.selectData", vo);
	}
	
}

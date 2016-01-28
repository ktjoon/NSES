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

import nses.cms.vo.OwnerHistVO;
import nses.common.dao.NsesAbstractDAO;
import nses.common.utils.ComStr;
import nses.common.vo.BasePageVO;

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

@Repository("ownerHistDAO")
public class OwnerHistDAO extends NsesAbstractDAO {

	/**
	 * 처리 내용 : 차주정보히스토리 추가
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    public int insertData(OwnerHistVO vo) throws Exception {
        return ComStr.toInt(insert("ownerHist.insertData", vo));
    }
    
    /**
     * 처리 내용 : 차주정보히스토리 리스트 반환
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public List<?> selectListData(BasePageVO pageVO) throws Exception {
        return list("ownerHist.selectListData", pageVO);
    }
    
    /**
     * 처리 내용 : 차주정보히스토리 리스트 건수
     * @param pageVO - 조회할 정보가 담긴 BasePageVO
     * @return 글 총 갯수
     * @throws Exception
     */
	public int selectListCount(BasePageVO pageVO) throws Exception {
        return (Integer) select("ownerHist.selectListCount", pageVO);
    }
}

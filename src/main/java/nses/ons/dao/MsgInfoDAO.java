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

import java.util.List;

import nses.common.dao.NsesAbstractDAO;
import nses.common.utils.ComStr;
import nses.ons.vo.MsgInfoVO;
import nses.ons.vo.UserConfigVO;

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

@Repository("msgInfoDAO")
public class MsgInfoDAO extends NsesAbstractDAO {

	/**
	 * 처리 내용 : 메세지 추가
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    public int insertData(MsgInfoVO vo) throws Exception {
        return ComStr.toInt(insert("msgInfo.insertData", vo));
    }
    
    /**
     * 처리 내용 : CCTV 정보 전송
     * 
     * @param vo
     * @return
     */
    public int insertCctvData(MsgInfoVO vo) {
		 return ComStr.toInt(insert("msgInfo.insertCctvData", vo));
	}
    
    /**
     * 처리 내용 : 메세지 리스트 반환
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public List<?> selectListData(MsgInfoVO vo) throws Exception {
        return list("msgInfo.selectListData", vo);
    }
    /**
     * 처리 내용 : 메세지 리스트 안보이기
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int updateMessageList(MsgInfoVO vo) throws Exception {
		return update("msgInfo.updateMessageList", vo);
	}
    
    /**
     * 처리 내용 : 수신 메세지 리스트 반환
     * 
     * @param 
     * @return
     * @throws Exception
     */
    public List<?> selectSMSList(MsgInfoVO vo) throws Exception {
        return list("msgInfo.selectSMSList");
    }

	
    
   
}

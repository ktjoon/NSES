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

import nses.common.utils.ComStr;
import nses.ons.vo.LoginVO;
import nses.ons.vo.UserConfigVO;
import nses.ons.vo.UserInfoVO;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

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

@Repository("userInfoDAO")
public class UserInfoDAO extends EgovAbstractDAO {

    /**
	 * 사용자정보를 조회한다.
	 * @param vo - 조회할 정보가 담긴 LoginVO
	 * @return 사용자정보
	 * @exception Exception
	 */
	public UserInfoVO selectData(LoginVO vo) throws Exception {
		return (UserInfoVO) select("userInfo.selectLoginData", vo);
	}
    
	
	public UserConfigVO selectConfigData(UserConfigVO vo) throws Exception {
		return (UserConfigVO) select("userInfo.selectConfigData", vo);
	}
	
	public int selectRecSaveData() throws Exception {
		return ComStr.toInt(select("userInfo.selectRecSaveData"));
	}
	
	public int insertConfigData(UserConfigVO vo) throws Exception {
		return ComStr.toInt(insert("userInfo.insertConfigData", vo));
	}
	
	public int updateLoginData(UserConfigVO vo) throws Exception {
		return update("userInfo.updateLoginData", vo);
	}
	
	public int updateConfigData(UserConfigVO vo) throws Exception {
		return update("userInfo.updateConfigData", vo);
	}
	
}

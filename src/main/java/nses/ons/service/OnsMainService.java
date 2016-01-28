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
package nses.ons.service;

import nses.ons.vo.LoginVO;
import nses.ons.vo.UserConfigVO;
import nses.ons.vo.UserInfoVO;

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
public interface OnsMainService {

    /**
	 * 사용자 로그인을 처리한다.
	 * @param vo - 사용자 정보가 담긴 LoginVO
	 * @return 결과VO - UserInfoVO
	 * @exception Exception
	 */
	UserInfoVO selectUserLogin(LoginVO vo) throws Exception;
	
	/**
	 * 사용자 설정정보를 반환한다.
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	UserConfigVO selectConfigData(UserConfigVO vo) throws Exception;
	
	/**
	 * 영상 설정정보를 반환한다.
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int selectRecSaveData() throws Exception;

	/**
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int insertConfigData(UserConfigVO vo) throws Exception;
	
	/**
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int updateConfigData(UserConfigVO vo) throws Exception;
	
	/**
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int updateLoginData(UserConfigVO vo) throws Exception;
	
}

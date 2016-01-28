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

import java.util.List;

import nses.common.vo.ResultVO;
import nses.ons.vo.MsgInfoVO;

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
public interface MsgInfoService {

	/**
	 * 메시지 정보 리스트 반환
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	 List<?> selectListData(MsgInfoVO vo) throws Exception;
	 
	 /**
		 * 수신 메세지 리스트 반환
		 * 
		 * @param vo
		 * @return
		 * @throws Exception
		 */
		 List<?> selectSMSList(MsgInfoVO vo) throws Exception;
	
	/**
	 * 메세지 정보를 등록한다.
	 * 
	 * @param vo - 등록할 정보가 담긴 RoadInfoVO
	 * @return 등록 결과
	 * @exception Exception
	 */
	ResultVO insertData(MsgInfoVO vo) throws Exception;
	
	/**
	 * CCTV 전송 정보를 등록한다.
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	ResultVO insertCctvData(MsgInfoVO vo) throws Exception;
	
	
}

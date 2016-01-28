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

import java.util.HashMap;
import java.util.List;

import nses.common.vo.ResultVO;
import nses.ons.vo.AccidentInfoVO;

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
public interface AccidentInfoService {

	/**
	 * 긴급구조 정보 리스트 반환
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	 List<?> selectListData(AccidentInfoVO vo) throws Exception;

	 /**
	  * 긴급구조 정보 입력
	  * 
	  * @param lstData
	  * @throws Exception
	  */
	 void insertListData(List<AccidentInfoVO> lstData) throws Exception;
	 
	 
	 /**
	  * 긴급구조 소방서,소 리스트 반환
	  * @param dsr_seq
	  * @return
	  * @throws Exception
	  */
	 List<?> selectWardList(String dsr_seq) throws Exception;

	 /**
	  * 소방차량 정보 리스트 반환
	  * 
	  * @param map
	  * @return
	  * @throws Exception
	  */
	 List<?> selectFireCarInfoList(HashMap<String, Object> map) throws Exception;
	 
	 /**
	  * 범위 내 CCTV 리스트 반환 (일반, 화재감시 포함)
	  * 
	  * @param map
	  * @return
	  * @throws Exception
	  */
	 List<?> selectRectCctvList(HashMap<String, Object> map) throws Exception;
	 
	 /**
	  * 최적경로정보 데이터 입력
	  * 
	  * @param map
	  * @throws Exception
	  */
	 ResultVO insertRouteData(HashMap<String, Object> map) throws Exception;

	 /**
	  * 긴급구조 업데이트
	  * @param list
	  * @throws Exception
	  */
	 void updateListData(List<AccidentInfoVO> list) throws Exception;
	 
	 /**
	  * 긴급구조 데이터 유무
	  * @param vo
	  * @return
	  */
	 int selectData(AccidentInfoVO vo) throws Exception;
	 
}

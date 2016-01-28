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
import nses.ons.vo.RoadInfoVO;

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
public interface RoadInfoService {

	/**
	 * 회피지점 정보 리스트 반환
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	 List<?> selectListData(RoadInfoVO vo) throws Exception;
	
	/**
	 * 회피지점 정보를 등록한다.
	 * 
	 * @param vo - 등록할 정보가 담긴 RoadInfoVO
	 * @return 등록 결과
	 * @exception Exception
	 */
	ResultVO insertData(RoadInfoVO vo) throws Exception;

    /**
	 * 회피지점 정보를 삭제한다.
	 * 
	 * @param vo - 수정할 정보가 담긴 RoadInfoVO
	 * @return void형
	 * @exception Exception
	 */
	ResultVO updateData(RoadInfoVO vo) throws Exception;

	/**
	 * 회피지점 정보를 초기화한다.
	 * 
	 * @param vo - 수정할 정보가 담긴 RoadInfoVO
	 * @return void형
	 * @exception Exception
	 */
	ResultVO clearData(RoadInfoVO vo) throws Exception;
}

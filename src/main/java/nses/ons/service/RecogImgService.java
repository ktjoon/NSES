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
import java.util.Map;

import nses.common.vo.ResultVO;
import nses.ons.vo.CarOwnerVO;
import nses.ons.vo.EnforceConfigVO;
import nses.ons.vo.RecogDetailVO;
import nses.ons.vo.RecogImgVO;

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
public interface RecogImgService {

	
	List<?> selectListData(RecogImgVO vo) throws Exception;
	
	
	ResultVO insertData(RecogImgVO vo, RecogDetailVO detailVO, Map<String, Object> params) throws Exception;
	RecogImgVO selectRecogImgData(HashMap<String, Object> map) throws Exception;
	
	List<?> selectRecogImgDetailList(HashMap<String, Object> map) throws Exception;
	
	RecogDetailVO selectRecogImage(HashMap<String, Object> map) throws Exception;
	
	
	/**
	 * 처리 내용 : 단속처리 가능여부 리턴		
	 * 0: 단속불가	1: 단속가능
	 * @param sCurrDt 
	 * 
	 * @param stat
	 * @return
	 * @throws Exception
	 */
	EnforceConfigVO selectCrackStat(HashMap<String, Object> mapData) throws Exception;
	
	/**
	 * 처리 내용 : 차주정보 리스트 반환
	 * @param sCarNo
	 * @return
	 * @throws Exception
	 */
	CarOwnerVO selectCarOwnInfo(String sCarNo)	throws Exception;
	
	/**
	 * 처리 내용 : 단속정보를 업데이트 한다.
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int updateRecogImgData(RecogImgVO vo) throws Exception;

	/**
	 * 처리 내용 : 단속정보 및 상태 수정.
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int updateEnforceData(RecogImgVO vo) throws Exception;
	
	/**
	 * 처리 내용 : 메세지 전송 및 상태 수정.
	 * 
	 * @param vo
	 * @param params
	 * @return
	 * @throws Exception
	 */
	int updateMsgData(RecogDetailVO vo, HashMap<String, Object> params) throws Exception;
	
	
	

	


	/**
	 * 처리 내용 : 영상저장 요청 - 추가/수정/최근파일명조회
	 * 
	 * @param vo
	 * @param params
	 * @return
	 * @throws Exception
	 */
	void insertRecordData(Map<String, Object> params) throws Exception;

	int updateRecordData(Map<String, Object> params) throws Exception;
	
	Map<String, Object> selectLatestRecFileName(Map<String, Object> params) throws Exception;


	List<RecogDetailVO> selectRecogImglList(RecogImgVO recogVO) throws Exception;
}

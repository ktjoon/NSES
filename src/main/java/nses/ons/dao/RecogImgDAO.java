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
import java.util.Map;

import nses.common.dao.NsesAbstractDAO;
import nses.common.utils.ComStr;
import nses.ons.vo.CarOwnerVO;
import nses.ons.vo.EnforceConfigVO;
import nses.ons.vo.MsgDataVO;
import nses.ons.vo.RecogDetailVO;
import nses.ons.vo.RecogImgVO;

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

@Repository("recogImgDAO")
public class RecogImgDAO extends NsesAbstractDAO {

    public int insertData(RecogImgVO vo) throws Exception {
        return ComStr.toInt(insert("recogImg.insertData", vo));
    }

    public int insertDetailData(RecogDetailVO vo) throws Exception {
        return ComStr.toInt(insert("recogImg.insertDetailData", vo));
    }
    
    /**
     * 처리 내용 : MSG_DATA 테이블 데이터 추가
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int insertMsgData(MsgDataVO vo) throws Exception {
        return ComStr.toInt(insert("recogImg.insertMsgData", vo));
    }
    
    
    

    public List<?> selectListData(RecogImgVO vo) throws Exception {
        return list("recogImg.selectListData", vo);
    }
    
   
    
    /**
     * 처리 내용 : 차량번호에 대한 차주정보 반환
     * 
     * @param sCarNo
     * @return
     * @throws Exception
     */
	public CarOwnerVO selectCarOwnInfo(String sCarNo) throws Exception {
		return (CarOwnerVO) select("recogImg.selectCarOwnInfo", sCarNo);
	}
	
	/**
	 * 처리 내용 : 단속정보를 반환
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public RecogImgVO selectRecogImgData(HashMap<String, Object> map) throws Exception {
	    return (RecogImgVO) select("recogImg.selectRecogImgData", map);
	}
	
	/**
	 * 처리 내용 : 단속정보 상세 리스트 반환
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> selectRecogImgDetailList(HashMap<String, Object> map) throws Exception {
	    return list("recogImg.selectRecogImgDetailList", map);
	}
	
	/**
	 * 처리 내용 : 단속상세 이미저 정보를 가져온다
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public RecogDetailVO selectRecogImage(HashMap<String, Object> map) throws Exception {
	    return (RecogDetailVO) select("recogImg.selectRecogImage", map);
	}

	/**
	 * 처리 내용 : 단속정보를 업데이트 한다.
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateRecogImgData(RecogImgVO vo) throws Exception {
        return update("recogImg.updateRecogImgData", vo);
    }
	
	/**
	 * 처리 내용 : 단속정보 내 USER_ID를 업데이트 한다. (데이터 존재 확인 여부)
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateData(RecogImgVO vo) throws Exception {
        return update("recogImg.updateData", vo);
    }
	
	/**
	 * 처리 내용 : 단속정보 상세 메세지 상태 업데이트 
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateRecogMsg(RecogImgVO vo) throws Exception {
        return update("recogImg.updateRecogMsg", vo);
    }
	
	/**
	 * 처리 내용 : 단속처리 가능여부 리턴		
	 * 0: 단속불가	1: 단속가능
	 * 
	 * @return
	 * @throws Exception
	 */
	public EnforceConfigVO selectCrackStat(HashMap<String, Object> mapData) throws Exception {
        return (EnforceConfigVO) select("recogImg.selectCrackStat", mapData);
    }
	
	public EnforceConfigVO selectMsgConfigData() throws Exception {
        return (EnforceConfigVO) select("recogImg.selectMsgConfigData");
    }
	
	
	/**
	 * 처리 내용 : 단속정보 및 상태 수정.
	 * 
	 * @param vo
	 * @return
	 */
	public int updateEnforceData(RecogImgVO vo) {
		return update("recogImg.updateEnforceData", vo);
	}
	
	/**
	 * 처리 내용 : 메세지 전송 및 상태 수정.
	 * 
	 * @param vo
	 * @return
	 */
	public int updateMsgData(RecogDetailVO vo) {
		return update("recogImg.updateMsgData", vo);
	}

	
	//
	public void insertRecordData(Map<String, Object> params) throws Exception {
		insert("recogImg.insertRecordData", params);
	}
	
	public int updateRecordData(Map<String, Object> params) throws Exception {
		return update("recogImg.updateRecordData", params);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectLatestRecFileName(Map<String, Object> params) throws Exception {
		return (Map<String, Object>) select("recogImg.selectLatestRecFileName", params);
	}

	public List<RecogDetailVO> selectRecogImglList(RecogImgVO recogVO) {
		return (List<RecogDetailVO>) list("recogImg.selectRecogImglList", recogVO);
	}
}

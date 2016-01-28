package nses.ons.service;

import java.util.List;
import java.util.Map;

import nses.ons.vo.RtsaReqVO;

public interface RtsaOccuridService {

    /**
     * UTIS 데이터 저장
     * @param lstData
     * @throws Exception
     */
	void insertListData(List<Map<String, Object>> lstData) throws Exception;
	
	/**
	 * UTIS 검색 데이터
	 * @param coord_list
	 * @return
	 * @throws Exception
	 */
	List<?> selectListData(RtsaReqVO rtsa_data) throws Exception;
}

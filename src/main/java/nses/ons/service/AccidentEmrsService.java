package nses.ons.service;

import java.util.List;



public interface AccidentEmrsService {
	
	List<?> selectListData() throws Exception;
	
	/**
	 * 긴급구조 소방서,소 위치 리스트
	 * @param dsr_seq
	 * @return
	 * @throws Exception
	 */
	List<?> selectWardLocationList(String dsr_seq) throws Exception;
	
	/**
	 * 차량 위치 리스트
	 * @param dsr_seq
	 * @return
	 * @throws Exception
	 */
	List<?> selectVlLocationList(String dsr_seq) throws Exception;

	/**
	 * 대상물 리스트
	 * @param sObjName
	 * @return
	 */
	List<?> selectObjList(String obj_name) throws Exception;
}

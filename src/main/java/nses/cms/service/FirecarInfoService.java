package nses.cms.service;

import java.util.List;
import java.util.Map;

import nses.cms.vo.FirecarInfoVO;
import nses.common.vo.BasePageVO;

public interface FirecarInfoService {
	/**
	 * 차량 정보를 등록한다.
	 * @param vo - 등록할 정보가 담긴 FirecarInfoVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    int insertData(FirecarInfoVO vo) throws Exception;

    /**
	 * 차량 정보를 수정한다.
	 * @param vo - 수정할 정보가 담긴 FirecarInfoVO
	 * @return void형
	 * @exception Exception
	 */
    int updateData(FirecarInfoVO vo) throws Exception;

    /**
	 * 차량 정보를 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 FirecarInfoVO
	 * @return void형
	 * @exception Exception
	 */
    int deleteData(FirecarInfoVO vo) throws Exception;

    /**
	 * 차량 정보를 조회한다.
	 * @param vo - 조회할 정보가 담긴 FirecarInfoVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    FirecarInfoVO selectData(FirecarInfoVO vo) throws Exception;

    /**
	 * 차량 정보 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    List<?> selectListData(BasePageVO pageVO) throws Exception;

    /**
	 * 차량 정보 총 카운트
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
    int selectListCount(BasePageVO pageVO);
    
    /**
	 * 차량 ID를 조회한다.
	 * @param String
	 * @return 조회한 ID
	 * @exception Exception
	 */    
    Map<String, String> selectCarId(String car_no) throws Exception;
}

package nses.cms.service;

import java.util.List;

import nses.cms.vo.CarOwnerVO;
import nses.common.vo.BasePageVO;

public interface CarOwnerService {
	/**
	 * 차주 정보를 등록한다.
	 * @param vo - 등록할 정보가 담긴 CarOwnerVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    void insertData(CarOwnerVO vo, String sUserId) throws Exception;

    /**
	 * 차주 정보를 수정한다.
	 * @param vo - 수정할 정보가 담긴 CarOwnerVO
	 * @return void형
	 * @exception Exception
	 */
    int updateData(CarOwnerVO vo, String sUserId) throws Exception;

    /**
	 * 차주 정보를 조회한다.
	 * @param vo - 조회할 정보가 담긴 CarOwnerVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    CarOwnerVO selectData(CarOwnerVO vo, String sUserId) throws Exception;

    /**
	 * 차주 정보 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    List<CarOwnerVO> selectListData(BasePageVO pageVO, String sUserId) throws Exception;

    /**
	 * 차주 정보 총 카운트
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
    int selectListCount(BasePageVO pageVO) throws Exception;

    /**
	 * 차주 정보를 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CarOwnerVO
	 * @return void형
	 * @exception Exception
	 */
	int deleteData(CarOwnerVO dataVO, String sUserId) throws Exception;

    /**
	 * 차주 정보 히스토리 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    List<?> selectHistData(BasePageVO pageVO) throws Exception;

    /**
	 * 차주 정보 히스토리 총 카운트
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
    int selectHistCount(BasePageVO pageVO) throws Exception;

}

package nses.cms.service;

import java.util.List;

import nses.cms.vo.CctvInfoVO;
import nses.common.vo.BasePageVO;

public interface CctvInfoService {
	/**
	 * CCTV 정보를 등록한다.
	 * @param vo - 등록할 정보가 담긴 CctvInfoVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    int insertData(CctvInfoVO vo) throws Exception;

    /**
	 * CCTV 정보를 수정한다.
	 * @param vo - 수정할 정보가 담긴 CctvInfoVO
	 * @return void형
	 * @exception Exception
	 */
    int updateData(CctvInfoVO vo) throws Exception;

    /**
	 * CCTV 정보를 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CctvInfoVO
	 * @return void형
	 * @exception Exception
	 */
    int deleteData(CctvInfoVO vo) throws Exception;

    /**
	 * CCTV 정보를 조회한다.
	 * @param vo - 조회할 정보가 담긴 CctvInfoVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    CctvInfoVO selectData(CctvInfoVO vo) throws Exception;

    /**
	 * CCTV 정보 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    List<?> selectListData(BasePageVO pageVO) throws Exception;

    /**
	 * CCTV 정보 총 카운트
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
    int selectListCount(BasePageVO pageVO);

    /**
     * CCTV 엑셀 업로드
     * @param lstData
     * @throws Exception
     */
	void insertExcelData(List<CctvInfoVO> lstData) throws Exception;
	
	/**
	 * CCTV 이름 조회
	 * @param cctv_name
	 * @return
	 * @throws Exception
	 */
	int selectCctvName(String cctv_name) throws Exception;
}

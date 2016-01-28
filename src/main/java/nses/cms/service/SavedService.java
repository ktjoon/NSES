package nses.cms.service;

import java.util.List;

import nses.cms.vo.SavedVO;
import nses.common.vo.BasePageVO;

public interface SavedService {
	

    /**
	 * 저장영상을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CarOwnerVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    SavedVO selectData(SavedVO vo) throws Exception;

    /**
	 * 저장영상 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    List<?> selectListData(BasePageVO pageVO) throws Exception;

    /**
	 * 저장영상 총 카운트
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
    int selectListCount(BasePageVO pageVO) throws Exception;
}

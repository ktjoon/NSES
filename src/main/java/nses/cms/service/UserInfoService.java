package nses.cms.service;

import java.util.List;

import nses.cms.vo.UserInfoVO;
import nses.common.vo.BasePageVO;

public interface UserInfoService {
	/**
	 * 사용자 정보를 등록한다.
	 * @param vo - 등록할 정보가 담긴 UserInfoVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    void insertData(UserInfoVO vo) throws Exception;

    /**
	 * 사용자 정보를 수정한다.
	 * @param vo - 수정할 정보가 담긴 UserInfoVO
	 * @return void형
	 * @exception Exception
	 */
    int updateData(UserInfoVO vo) throws Exception;

    /**
	 * 사용자 정보를 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 UserInfoVO
	 * @return void형
	 * @exception Exception
	 */
    int deleteData(UserInfoVO vo) throws Exception;

    /**
	 * 사용자 정보를 조회한다.
	 * @param vo - 조회할 정보가 담긴 UserInfoVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    UserInfoVO selectData(UserInfoVO vo) throws Exception;

    /**
	 * 사용자 정보 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    List<?> selectListData(BasePageVO pageVO) throws Exception;

    /**
	 * 사용자 정보 총 카운트
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
    int selectListCount(BasePageVO pageVO) throws Exception;
}

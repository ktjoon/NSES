package nses.cms.dao;

import java.util.List;

import nses.cms.vo.UserInfoVO;
import nses.common.dao.NsesAbstractDAO;
import nses.common.vo.BasePageVO;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

/**
 * 
 * @Modification Information
 * @
 * @ 수정일			수정자              수정내용
 * @ ---------		---------		-------------------------------
 * @ 2014.12.23						최초생성
 *
 * @author 	개발프레임웍크 실행환경 개발팀
 * @since 	2014.12.23
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSES All right reserved.
 */

@Repository("userinfoDAO")
public class UserInfoDAO extends NsesAbstractDAO {

	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 UserInfoVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public void insertData(UserInfoVO vo) throws Exception {
        insert("userInfo.insertData", vo);
    }

    /**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 UserInfoVO
	 * @return void형
	 * @exception Exception
	 */
    public int updateData(UserInfoVO vo) throws Exception {
        return update("userInfo.updateData", vo);
    }

    /**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 UserInfoVO
	 * @return void형
	 * @exception Exception
	 */
    public int deleteData(UserInfoVO vo) throws Exception {
        return delete("userInfo.deleteData", vo);
    }

    /**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 UserInfoVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    public UserInfoVO selectData(UserInfoVO vo) throws Exception {
        return (UserInfoVO) select("userInfo.selectData", vo);
    }

    /**
	 * 글 목록을 조회한다.
	 * @param pageVO - 조회할 정보가 담긴 BasePageVO
	 * @return 글 목록
	 * @exception Exception
	 */
    public List<?> selectListData(BasePageVO pageVO) throws Exception {
        return list("userInfo.selectListData", pageVO);
    }
    
    /**
     * 글 총 갯수를 조회한다.
     * @param pageVO - 조회할 정보가 담긴 BasePageVO
     * @return 글 총 갯수
     * @throws Exception
     */
	public int selectListCount(BasePageVO pageVO) throws Exception {
        return (Integer) select("userInfo.selectListCount", pageVO);
    }

}

package nses.cms.dao;

import java.util.List;

import nses.cms.vo.CarOwnerVO;
import nses.cms.vo.SavedVO;
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

@Repository("savedDAO")
public class SavedDAO extends NsesAbstractDAO {



    /**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CarOwnerVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    public SavedVO selectData(SavedVO vo) throws Exception {
        return (SavedVO) select("saved.selectData", vo);
    }

    /**
	 * 글 목록을 조회한다.
	 * @param pageVO - 조회할 정보가 담긴 BasePageVO
	 * @return 글 목록
	 * @exception Exception
	 */
    public List<?> selectListData(BasePageVO pageVO) throws Exception {
        return list("saved.selectListData", pageVO);
    }
    
    /**
     * 글 총 갯수를 조회한다.
     * @param pageVO - 조회할 정보가 담긴 BasePageVO
     * @return 글 총 갯수
     * @throws Exception
     */
	public int selectListCount(BasePageVO pageVO) throws Exception {
        return (Integer) select("saved.selectListCount", pageVO);
    }

}

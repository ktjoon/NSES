package nses.cms.dao;

import java.util.List;

import nses.cms.vo.RecogDetailVO;
import nses.cms.vo.RecogListVO;
import nses.cms.vo.RecogVO;
import nses.common.dao.NsesAbstractDAO;
import nses.common.vo.BasePageVO;

import org.springframework.stereotype.Repository;


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

@Repository("recoginfoDAO")
public class RecogInfoDAO extends NsesAbstractDAO {

	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 RecogInfoVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public String insertData(RecogVO vo) throws Exception {
        return (String)insert("recogInfo.insertData", vo);
    }

    /**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 RecogInfoVO
	 * @return void형
	 * @exception Exception
	 */
    public int updateData(RecogVO vo) throws Exception {
        return update("recogInfo.updateData", vo);
    }

    /**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 RecogInfoVO
	 * @return void형
	 * @exception Exception
	 */
    public int deleteData(RecogVO vo) throws Exception {
        return delete("recogInfo.deleteData", vo);
    }

    /**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 RecogInfoVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    public List<?> selectData(RecogDetailVO vo) throws Exception {
        return list("recogInfo.selectData", vo);
    }

    /**
	 * 글 목록을 조회한다.
	 * @param pageVO - 조회할 정보가 담긴 BasePageVO
	 * @return 글 목록
	 * @exception Exception
	 */
    public List<RecogListVO> selectListData(BasePageVO pageVO) throws Exception {
        return (List<RecogListVO>) list("recogInfo.selectListData", pageVO);
    }
    
    /**
     * 글 총 갯수를 조회한다.
     * @param pageVO - 조회할 정보가 담긴 BasePageVO
     * @return 글 총 갯수
     * @throws Exception
     */
	public int selectListCount(BasePageVO pageVO) throws Exception {
        return (Integer) select("recogInfo.selectListCount", pageVO);
    }

	public List<?> selectSenderMsgData(BasePageVO pageVO) {
		return list("recogInfo.selectSenderMsgData", pageVO);
	}

	public int selectSenderMsgCount(BasePageVO pageVO) {
		return (Integer) select("recogInfo.selectSenderMsgCount", pageVO);
	}

}

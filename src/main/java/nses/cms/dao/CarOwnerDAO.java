package nses.cms.dao;

import java.util.List;

import nses.cms.vo.CarOwnerVO;
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

@Repository("carOwnerDAO")
public class CarOwnerDAO extends NsesAbstractDAO {

	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 CarOwnerVO
	 * @return 등록 결과
	 * @exception Exception
	 */
    public void insertData(CarOwnerVO vo) throws Exception {
        insert("carOwner.insertData", vo);
    }

    /**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 CarOwnerVO
	 * @return void형
	 * @exception Exception
	 */
    public int updateData(CarOwnerVO vo) throws Exception {
        return update("carOwner.updateData", vo);
    }

    /**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 CarOwnerVO
	 * @return void형
	 * @exception Exception
	 */
    public int deleteData(CarOwnerVO vo) throws Exception {
        return delete("carOwner.deleteData", vo);
    }

    /**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 CarOwnerVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    public CarOwnerVO selectData(CarOwnerVO vo) throws Exception {
        return (CarOwnerVO) select("carOwner.selectData", vo);
    }

    /**
	 * 글 목록을 조회한다.
	 * @param pageVO - 조회할 정보가 담긴 BasePageVO
	 * @return 글 목록
	 * @exception Exception
	 */
    public List<CarOwnerVO> selectListData(BasePageVO pageVO) throws Exception {
        return (List<CarOwnerVO>) list("carOwner.selectListData", pageVO);
    }
    
    /**
     * 글 총 갯수를 조회한다.
     * @param pageVO - 조회할 정보가 담긴 BasePageVO
     * @return 글 총 갯수
     * @throws Exception
     */
	public int selectListCount(BasePageVO pageVO) throws Exception {
        return (Integer) select("carOwner.selectListCount", pageVO);
    }

}

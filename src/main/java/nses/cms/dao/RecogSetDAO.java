package nses.cms.dao;


import nses.cms.vo.RecogSetVO;
import nses.common.dao.NsesAbstractDAO;

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

@Repository("recogsetDAO")
public class RecogSetDAO extends NsesAbstractDAO {

    /**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 RecogSetVO
	 * @return void형
	 * @exception Exception
	 */
    public int updateData(RecogSetVO vo) throws Exception {
        return update("recogset.updateData", vo);
    }


    /**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 RecogSetVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    public RecogSetVO selectData(RecogSetVO vo) throws Exception {
        return (RecogSetVO) select("recogset.selectData", vo);
    }
    
}

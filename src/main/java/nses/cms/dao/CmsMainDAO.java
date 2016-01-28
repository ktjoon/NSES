package nses.cms.dao;


import nses.cms.vo.LoginVO;
import nses.cms.vo.UserInfoVO;
import nses.common.dao.NsesAbstractDAO;

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

@Repository("cmsMainDAO")
public class CmsMainDAO extends NsesAbstractDAO {

    /**
	 * 사용자정보를 조회한다.
	 * @param vo - 조회할 정보가 담긴 LoginVO
	 * @return 사용자정보
	 * @exception Exception
	 */
    public UserInfoVO selectData(LoginVO vo) throws Exception {
        return (UserInfoVO) select("loginInfo.selectLoginData", vo);
    }

}

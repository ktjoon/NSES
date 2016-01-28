package nses.cms.service;

import nses.cms.vo.LoginVO;
import nses.cms.vo.UserInfoVO;


/**
 * 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.12.23              최초생성
 *
 * @author	개발프레임웍크 실행환경 개발팀
 * @since	2014.12.23
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSES All right reserved.
 */
public interface CmsMainService {

    /**
	 * 사용자 로그인을 처리한다.
	 * @param vo - 사용자 정보가 담긴 LoginVO
	 * @return 결과VO - UserInfoVO
	 * @exception Exception
	 */
	UserInfoVO selectUserLogin(LoginVO vo) throws Exception;

}

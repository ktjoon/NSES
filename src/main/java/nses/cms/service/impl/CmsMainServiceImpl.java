package nses.cms.service.impl;

import javax.annotation.Resource;

import nses.cms.dao.CmsMainDAO;
import nses.cms.service.CmsMainService;


import nses.cms.vo.LoginVO;
import nses.cms.vo.UserInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.12.23              최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2014.12.23
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSES All right reserved.
 */

@Service("cmsMainService")
public class CmsMainServiceImpl extends EgovAbstractServiceImpl implements CmsMainService {

	private static final Logger logger = LoggerFactory.getLogger(CmsMainServiceImpl.class);

	@Resource(name="cmsMainDAO")
	private CmsMainDAO cmsMainDAO;

	/**
	 * 사용자 로그인을 처리한다.
	 * @param vo - 사용자 정보가 담긴 LoginVO
	 * @return ResultVO
	 * @exception Exception
	 */
	public UserInfoVO selectUserLogin(LoginVO vo) throws Exception {
		UserInfoVO	retVO = cmsMainDAO.selectData(vo);

		logger.info("selectUserLogin(" + vo.getUser_id() + ") - " + retVO);

		return retVO;
	}

}

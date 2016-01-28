/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nses.ons.service.impl;

import java.util.List;

import javax.annotation.Resource;

import nses.ons.dao.CctvInfoDAO;
import nses.ons.service.CctvInfoService;
import nses.ons.vo.CctvInfoVO;

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

@Service("cctvInfoService")
public class CctvInfoServiceImpl extends EgovAbstractServiceImpl implements CctvInfoService {

//	private static final Logger logger = LoggerFactory.getLogger(CctvInfoServiceImpl.class);

	@Resource(name="cctvInfoDAO")
	private CctvInfoDAO cctvInfoDAO;

	@Override
	public List<?> selectListData(CctvInfoVO vo) throws Exception {
		return cctvInfoDAO.selectListData(vo);
	}
	

}

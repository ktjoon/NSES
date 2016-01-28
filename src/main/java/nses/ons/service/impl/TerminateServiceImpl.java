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

import nses.ons.dao.TerminateDAO;
import nses.ons.service.TerminateService;
import nses.ons.vo.RecogImgVO;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : AccidentScheduleServiceImpl.java
 * @Description : Sample Business Implement Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Service("terminateService")
public class TerminateServiceImpl extends EgovAbstractServiceImpl implements TerminateService {

	@Resource(name="terminateDAO")
	private TerminateDAO terminateDAO;
	
	@Override
	public List<?> selectListData(RecogImgVO recogImgVO) throws Exception {
		return terminateDAO.selectListData(recogImgVO);
	}

}

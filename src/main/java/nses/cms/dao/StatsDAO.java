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
package nses.cms.dao;

import java.util.List;

import nses.cms.vo.CctvInfoVO;
import nses.cms.vo.StatsVO;
import nses.common.dao.EmrsAbstractDAO;
import nses.common.dao.NsesAbstractDAO;
import nses.common.vo.BasePageVO;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

/**
 * @Class Name : CctvInfoDAO.java
 * @Description : CctvInfo DAO Class
 * @Modification Information
 * @
 * @ 수정일			수정자			수정내용
 * @ ----------		---------   	-------------------------------
 * @ 2009.03.16						최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Repository("statsDAO")
public class StatsDAO extends NsesAbstractDAO {

	public List<?> selectListData(StatsVO statsVO) {
		return list("stats.selectData", statsVO);
	}
	
	
	
}

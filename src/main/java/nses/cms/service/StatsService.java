package nses.cms.service;

import java.util.List;

import nses.cms.vo.StatsVO;

public interface StatsService {
	


    /**
	 * 통계를 조회한다.
	 * @param statsVO - 조회할 정보가 담긴 VO
	 * @return 통계 목록
	 * @exception Exception
	 */
    List<?> selectListData(StatsVO statsVO) throws Exception;
}

package nses.cms.service;


import nses.cms.vo.RecogSetVO;

public interface RecogSetService {

    /**
	 * 단속설정 정보를 수정한다.
	 * @param vo - 수정할 정보가 담긴 CarOwnerVO
	 * @return void형
	 * @exception Exception
	 */
    int updateData(RecogSetVO vo) throws Exception;

    /**
	 * 단속설정 정보를 조회한다.
	 * @param vo - 조회할 정보가 담긴 CarOwnerVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    RecogSetVO selectData(RecogSetVO vo) throws Exception;

}

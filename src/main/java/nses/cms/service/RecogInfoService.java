package nses.cms.service;

import java.util.List;

import nses.cms.vo.RecogDetailVO;
import nses.cms.vo.RecogListVO;
import nses.common.vo.BasePageVO;

public interface RecogInfoService {

    /**
	 * 처리 내용 : 단속 정보를 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 CarOwnerVO
	 * @return 조회한 글
	 * @exception Exception
	 */
    List<?> selectData(RecogDetailVO vo) throws Exception;

    /**
	 * 처리 내용 : 단속 정보 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
    List<RecogListVO> selectListData(BasePageVO pageVO) throws Exception;

    /**
	 * 처리 내용 : 단속 정보 총 카운트
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
    int selectListCount(BasePageVO pageVO) throws Exception;
    
    /**
     * 처리 내용 : 메세지 전송결과 리스트 반환
     * 
     * @param recogVO
     * @return
     */
	List<?> selectSenderMsgData(BasePageVO pageVO) throws Exception;
	
	/**
	 * 처리 내용 : 메세지 전송결과 리스트 카운트 반환
	 * 
	 * @param pageVO
	 * @return
	 */
	int selectSenderMsgCount(BasePageVO pageVO);
}

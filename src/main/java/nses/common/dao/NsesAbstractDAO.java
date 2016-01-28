package nses.common.dao;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@SuppressWarnings("deprecation")
public class NsesAbstractDAO extends EgovAbstractDAO {

    /**
     * EgovAbstractDAO 는 base class 로만 사용되며 해당 인스턴스를 직접 생성할 수 없도록 protected constructor 로 선언하였음.
     */
    protected NsesAbstractDAO() {
        // PMD abstract Rule
    	// - If the class is intended to be used as a base class only (not to be instantiated directly)
        // a protected constructor can be provided prevent direct instantiation
    }

    /**
     * Annotation 형식으로 sqlMapClient 를 받아와 이를 super(SqlMapClientDaoSupport) 의 setSqlMapClient 메서드를 호출하여 설정해 준다.
     *
     * @param sqlMapClient - ibatis 의 SQL Map 과의 상호작용을 위한 기본 클래스로
     *               mapped statements(select, insert, update, delete 등)의 실행을 지원함.
     */
	@Resource(name = "sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
}

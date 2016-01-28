package nses.common.session;


public class AdminSessInfo extends BaseSessInfo {
	//
	private static final String			URI_APP_HOME 	= "/cms/user/list.do";			
	private static final String			URI_APP_LOGOUT 	= "/cms/logout.do";			// logout URI

	//
	public AdminSessInfo() {
		super();
	}

	//
	public String getBaseUri() {
		return URI_APP_HOME;
	}
	public String getLogoutUri() {
		return URI_APP_LOGOUT;
	}
}

package nses.common.session;


public class UserSessInfo extends BaseSessInfo {
	//
	private static final String			URI_APP_HOME 	= "/ons/index.do";			
	private static final String			URI_APP_LOGOUT 	= "/ons/logout.do";			// logout URI

	//
	public UserSessInfo() {
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

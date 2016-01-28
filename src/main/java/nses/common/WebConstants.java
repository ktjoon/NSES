package nses.common;


/**
 * The Class Constants.
 * 
 * @author tk_lee
 */
public class WebConstants {

	/** The Constant DATA_MAP. */
	public static final String 	DATA_MAP = "dataMap";

	/** The Constant DATA_LIST. */
	public static final String 	DATA_LIST = "dataList";

	/** The Constant CODE_LIST. */
	public static final String 	CODE_LIST = "codeList";

	//
	public static final int 	USER_SESSION_TIMEOUT 		= 10 * 60 * 60;
	public static final int 	ADMIN_SESSION_TIMEOUT 		= 10 * 60 * 60;
	
	/** 사용자 정보*/
	public static final String 	USER_ID						= "user_id";
	public static final String 	USER_NAME					= "user_name";
	
	public static final int 	NOTUSE_USE_STAT				= 0;
	public static final int 	LOGIN_USE_STAT				= 1;

	//
	public static final String	CRYTO_ALGORITHM				= "SHA-256";
	public static final String	CRYTO_ENCKEY				= "NSES~119~!-OnS";
}

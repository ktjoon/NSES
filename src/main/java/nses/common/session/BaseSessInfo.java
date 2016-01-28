package nses.common.session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseSessInfo {

	public static final String			SESSION_LOGIN_USER 	= "sess_LoginUser";
	public static final String			SESSION_LOGIN_ADMIN = "sess_LoginAdmin";

	public static final int				PERM_PERSON 	= 1;	// person
	public static final int				PERM_MANAGER 	= 5;	// manager
	public static final int				PERM_SUPER 		= 8;	// super user
	public static final int				PERM_ADMIN 		= 9;	// super admin

	
	private String			m_sLoginId;
	private String			m_sLoginName;
	private String			m_nLoginEmail;
	private int				m_nLoginPower = PERM_PERSON;
	private Date 			m_dateCurr;

	//
	public BaseSessInfo() {
		Calendar 	calendar = Calendar.getInstance();
		m_dateCurr = calendar.getTime();
	}

	//
	public String getUserId() {
		return m_sLoginId;
	}
	public String getUserName() {
		return m_sLoginName;
	}
	public String getUserEmail() {
		return m_nLoginEmail;
	}
	public int getUserPerm() {
		return m_nLoginPower;
	}
	public boolean	isEditPerm() {
		return getUserPerm() <= PERM_MANAGER;
	}
	public boolean	isDelPerm() {
		return getUserPerm() <= PERM_MANAGER;
	}
	public String getLoginDate() {
		String strDatePattern	= "yyyy/mm/dd";
		return new SimpleDateFormat(strDatePattern).format(m_dateCurr);
	}

	//
	public String getCurrentDate(String strDatePattern) {
		Calendar 	calendar = Calendar.getInstance();
		Date 		date = calendar.getTime();
		// new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		return new SimpleDateFormat(strDatePattern).format(date);
	}
	
	public void setUserId(String sUserId) {
		m_sLoginId		= sUserId;
	}
	public void setUserName(String sUserName) {
		m_sLoginName	= sUserName;
	}
	public void setUserPower(int nPower) {
		m_nLoginPower	= nPower;
	}
	public void setUserEmail(String sUserEmail) {
		m_nLoginEmail	= sUserEmail;
	}
}

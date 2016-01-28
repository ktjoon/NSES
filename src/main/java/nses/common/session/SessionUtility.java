package nses.common.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtility {
	//
	public static HttpSession createNewSession(HttpServletRequest request,
			String key, Object value) {
		HttpSession session = request.getSession();
		putSessionValue(request, key, value);
		return session;
	}

	public static HttpSession getSession(HttpServletRequest request) {
		return request.getSession(false);
	}

	public static void putSessionValue(HttpServletRequest request, String key,
			Object value) {
		HttpSession session = request.getSession(false);
		session.setAttribute(key, value);
	}

	public static Object getSessionValue(HttpServletRequest request, String key) {
		HttpSession session = request.getSession(false);
		return session.getAttribute(key);
	}

	public static void cleanSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
	}
	
	public static void setTimeOut(HttpServletRequest request, int timeout) {
	    HttpSession httpSession = null;
	    if ((httpSession=getSession(request))==null)
	        return;
	    
	    httpSession.setMaxInactiveInterval(timeout);
	}

	//
	public static void setLoginForUser(HttpServletRequest request, BaseSessInfo info) {
		HttpSession session = request.getSession(true);

//		session.setAttribute(WebConstants.USER_ID, info.getUserId());
		session.setAttribute(BaseSessInfo.SESSION_LOGIN_USER, info);
	}
	public static BaseSessInfo getLoginForUser(HttpServletRequest request) {
		HttpSession 	session = request.getSession(false);
		if (session == null)
			return null;
		return (BaseSessInfo) session.getAttribute(BaseSessInfo.SESSION_LOGIN_USER);
	}
	public static void clearLoginForUser(HttpServletRequest request) {
		HttpSession 	session = request.getSession(false);
		if (session == null)
			return;
		session.removeAttribute(BaseSessInfo.SESSION_LOGIN_USER);
//		session.removeAttribute(WebConstants.USER_ID, info.getUserId());
		
		// is admin session null -> clear session 
		if (session.getAttribute(BaseSessInfo.SESSION_LOGIN_ADMIN) == null)
			session.invalidate();
	}
	//
	public static void setLoginForAdmin(HttpServletRequest request, BaseSessInfo info) {
		HttpSession session = request.getSession(true);
		session.setAttribute(BaseSessInfo.SESSION_LOGIN_ADMIN, info);
	}
	public static BaseSessInfo getLoginForAdmin(HttpServletRequest request) {
		HttpSession 	session = request.getSession(false);
		if (session == null)
			return null;
		return (BaseSessInfo) session.getAttribute(BaseSessInfo.SESSION_LOGIN_ADMIN);
	}
	public static void clearLoginForAdmin(HttpServletRequest request) {
		HttpSession 	session = request.getSession(false);
		if (session == null)
			return;
		session.removeAttribute(BaseSessInfo.SESSION_LOGIN_ADMIN);
		
		// is user session null -> clear session 
		if (session.getAttribute(BaseSessInfo.SESSION_LOGIN_USER) == null)
			session.invalidate();
	}
}

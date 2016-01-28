package nses.cms.vo;

import nses.common.vo.BaseReqVO;

public class LoginVO extends BaseReqVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2042384474503962577L;
	
	//
	private String user_id;
	private String user_pwd;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

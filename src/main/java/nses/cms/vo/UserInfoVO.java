package nses.cms.vo;

import nses.common.vo.BasePageVO;

public class UserInfoVO extends BasePageVO {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1051921724757580078L;
	
	private String	user_id;
	private String	user_pwd;
	private String	user_name;
	private String	user_email;
	private String	user_telno;
	private String	reg_dt;
	private String	mod_dt;
	private int		use_stat;
	
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
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_telno() {
		return user_telno;
	}
	public void setUser_telno(String user_telno) {
		this.user_telno = user_telno;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getMod_dt() {
		return mod_dt;
	}
	public void setMod_dt(String mod_dt) {
		this.mod_dt = mod_dt;
	}
	public int getUse_stat() {
		return use_stat;
	}
	public void setUse_stat(int use_stat) {
		this.use_stat = use_stat;
	}
	
	//
	public boolean isUserAuth(String sPass) {
		return sPass.equals(this.user_pwd);
	}
}

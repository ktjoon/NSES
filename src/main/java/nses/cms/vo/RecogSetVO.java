package nses.cms.vo;

import nses.common.vo.BasePageVO;

public class RecogSetVO extends BasePageVO {
	
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1051921724757580078L;
	
	private String enforce_time;
	private String enforce_start_time;
	private String enforce_end_time;
	private String msg_contents;
	private String msg_auto_send;
	private String org_user_name;
	private String rec_save_sec;
	private String org_user_tel;
	private String sender_tel;
	private String mod_dt;
	private String user_id;
	
	
	public String getSender_tel() {
		return sender_tel;
	}
	public void setSender_tel(String sender_tel) {
		this.sender_tel = sender_tel;
	}
	public String getEnforce_time() {
		return enforce_time;
	}
	public void setEnforce_time(String enforce_time) {
		this.enforce_time = enforce_time;
	}
	public String getEnforce_start_time() {
		return enforce_start_time;
	}
	public void setEnforce_start_time(String enforce_start_time) {
		this.enforce_start_time = enforce_start_time;
	}
	public String getEnforce_end_time() {
		return enforce_end_time;
	}
	public void setEnforce_end_time(String enforce_end_time) {
		this.enforce_end_time = enforce_end_time;
	}
	public String getMsg_contents() {
		return msg_contents;
	}
	public void setMsg_contents(String msg_contents) {
		this.msg_contents = msg_contents;
	}
	public String getMsg_auto_send() {
		return msg_auto_send;
	}
	public void setMsg_auto_send(String msg_auto_send) {
		this.msg_auto_send = msg_auto_send;
	}
	public String getOrg_user_name() {
		return org_user_name;
	}
	public void setOrg_user_name(String org_user_name) {
		this.org_user_name = org_user_name;
	}
	public String getOrg_user_tel() {
		return org_user_tel;
	}
	public void setOrg_user_tel(String org_user_tel) {
		this.org_user_tel = org_user_tel;
	}
	public String getMod_dt() {
		return mod_dt;
	}
	public void setMod_dt(String mod_dt) {
		this.mod_dt = mod_dt;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getRec_save_sec() {
		return rec_save_sec;
	}
	public void setRec_save_sec(String rec_save_sec) {
		this.rec_save_sec = rec_save_sec;
	}
	
	
	
}

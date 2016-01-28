package nses.cms.vo;

import nses.common.vo.BasePageVO;

public class CarOwnerVO extends BasePageVO {
	
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1051921724757580078L;
	
	private String	own_car_no;
	private String	own_name;
	private String	tel_no;
	private String	reg_dt;
	private String	mod_dt;
	private int		use_stat;
	
	public String getOwn_car_no() {
		return own_car_no;
	}
	public void setOwn_car_no(String own_car_no) {
		this.own_car_no = own_car_no;
	}
	public String getOwn_name() {
		return own_name;
	}
	public void setOwn_name(String own_name) {
		this.own_name = own_name;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
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
}

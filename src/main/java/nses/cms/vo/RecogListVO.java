package nses.cms.vo;

import nses.common.vo.BasePageVO;

public class RecogListVO extends BasePageVO {
	
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 2871515591527193045L;
	private String dsr_seq;
	private String own_car_no;
	private String recog_time;
	private String poi_addr;
	private String own_name;
	private String tel_no;
	private String cctv_name;
	
	public String getOwn_car_no() {
		return own_car_no;
	}
	public void setOwn_car_no(String own_car_no) {
		this.own_car_no = own_car_no;
	}
	public String getRecog_time() {
		return recog_time;
	}
	public void setRecog_time(String recog_time) {
		this.recog_time = recog_time;
	}
	public String getPoi_addr() {
		return poi_addr;
	}
	public void setPoi_addr(String poi_addr) {
		this.poi_addr = poi_addr;
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
	public String getDsr_seq() {
		return dsr_seq;
	}
	public void setDsr_seq(String dsr_seq) {
		this.dsr_seq = dsr_seq;
	}
	public String getCctv_name() {
		return cctv_name;
	}
	public void setCctv_name(String cctv_name) {
		this.cctv_name = cctv_name;
	}
	
	
}

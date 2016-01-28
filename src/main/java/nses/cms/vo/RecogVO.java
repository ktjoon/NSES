package nses.cms.vo;

import java.util.ArrayList;
import java.util.List;

import nses.common.vo.BasePageVO;

public class RecogVO extends BasePageVO {
	
	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = -5081768861994403744L;

	private List<?> 	items;
	
	private String		dsr_seq;
	private String		own_car_no;
	private String		enforce_stat;
	private String		enforce_dt;	
	private String		user_id;
	private String		reg_dt;
	
	private int			recog_flag;			// 1: 저장, 2 : 단속처리, 3 : 메세지 전송
	
	public RecogVO() {
		if (items == null)
			items = new ArrayList<RecogDetailVO>();
	}

	public String getDsr_seq() {
		return dsr_seq;
	}
	public void setDsr_seq(String dsr_seq) {
		this.dsr_seq = dsr_seq;
	}
	public String getOwn_car_no() {
		return own_car_no;
	}
	public void setOwn_car_no(String own_car_no) {
		this.own_car_no = own_car_no;
	}
	public String getEnforce_stat() {
		return enforce_stat;
	}
	public void setEnforce_stat(String enforce_stat) {
		this.enforce_stat = enforce_stat;
	}
	public String getEnforce_dt() {
		return enforce_dt;
	}
	public void setEnforce_dt(String enforce_dt) {
		this.enforce_dt = enforce_dt;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public List<?> getItems() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;
	}
	
	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}

	public int getRecog_flag() {
		return recog_flag;
	}
	public void setRecog_flag(int recog_flag) {
		this.recog_flag = recog_flag;
	}
		
}

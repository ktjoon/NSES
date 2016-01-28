package nses.ons.vo;

import java.util.List;

public class FireCarInfoVO {
	//
	private	static String	CAM_YES = "Y";

	//
	private String car_id;
	private String car_no;
	private String car_type;
	private String cam_yn;
	private String cam_hostport;
	private String cam_auth_data;
	private String cam_url;
	private String cctv_seqno;
	private String model_name;
	private String model_number;
	private String serial_number;
	private String munufact_dt;
	private String reg_dt;
	private String mod_dt;
	private String use_stat;
	private String user_id;
	
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getCam_yn() {
		return cam_yn;
	}
	public void setCam_yn(String cam_yn) {
		this.cam_yn = cam_yn;
	}
	public String getCam_hostport() {
		return cam_hostport;
	}
	public void setCam_hostport(String cam_hostport) {
		this.cam_hostport = cam_hostport;
	}
	public String getCam_auth_data() {
		return cam_auth_data;
	}
	public void setCam_auth_data(String cam_auth_data) {
		this.cam_auth_data = cam_auth_data;
	}
	public String getCam_url() {
		return cam_url;
	}
	public void setCam_url(String cam_url) {
		this.cam_url = cam_url;
	}
	public String getCctv_seqno() {
		return cctv_seqno;
	}
	public void setCctv_seqno(String cctv_seqno) {
		this.cctv_seqno = cctv_seqno;
	}
	public String getModel_name() {
		return model_name;
	}
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	public String getModel_number() {
		return model_number;
	}
	public void setModel_number(String model_number) {
		this.model_number = model_number;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getMunufact_dt() {
		return munufact_dt;
	}
	public void setMunufact_dt(String munufact_dt) {
		this.munufact_dt = munufact_dt;
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
	public String getUse_stat() {
		return use_stat;
	}
	public void setUse_stat(String use_stat) {
		this.use_stat = use_stat;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	//
	public boolean isExistCam() {
		if (cam_yn == null || cam_hostport == null)
			return false;
		if (CAM_YES.equals(cam_yn) == false)
			return false;
		if (cam_hostport.length() <= 0)
			return false;
		
		return true;
	}
	public void copyTo(List<?> lstWardCar) {
		for (int i = 0; i < lstWardCar.size(); i ++) {
			AccdWcarInfoVO 	vo = (AccdWcarInfoVO) lstWardCar.get(i);
			
			if (car_id.equals(vo.getCar_id())) {
				vo.setCam_yn(CAM_YES);
				vo.setCam_hostport(this.cam_hostport);
				vo.setCam_auth_data(this.cam_auth_data);
				vo.setCam_url(this.cam_url);
			}
		}
	}
}

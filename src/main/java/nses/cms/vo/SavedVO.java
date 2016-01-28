package nses.cms.vo;

import nses.common.vo.BasePageVO;

public class SavedVO extends BasePageVO {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1051921724757580078L;
	
	private String file_name;
	private String car_no;
	private String file_server;
	private String file_path;
	private String file_size;
	private String file_type;
	private String cam_type;
	private int save_sec;
	private String poi_x;
	private String poi_y;
	private String reg_dt;
	private String user_id;
	private String save_stat;
	private String rec_stat;
	
	//
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public String getFile_server() {
		return file_server;
	}
	public void setFile_server(String file_server) {
		this.file_server = file_server;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getCam_type() {
		return cam_type;
	}
	public void setCam_type(String cam_type) {
		this.cam_type = cam_type;
	}
	public int getSave_sec() {
		return save_sec;
	}
	public void setSave_sec(int save_sec) {
		this.save_sec = save_sec;
	}
	public String getPoi_x() {
		return poi_x;
	}
	public void setPoi_x(String poi_x) {
		this.poi_x = poi_x;
	}
	public String getPoi_y() {
		return poi_y;
	}
	public void setPoi_y(String poi_y) {
		this.poi_y = poi_y;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getSave_stat() {
		return save_stat;
	}
	public void setSave_stat(String save_stat) {
		this.save_stat = save_stat;
	}
	public String getRec_stat() {
		return rec_stat;
	}
	public void setRec_stat(String rec_stat) {
		this.rec_stat = rec_stat;
	}
	
}

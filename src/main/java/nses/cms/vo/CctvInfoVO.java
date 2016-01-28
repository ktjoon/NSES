package nses.cms.vo;

import nses.common.vo.BasePageVO;

public class CctvInfoVO extends BasePageVO {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1051921724757580078L;
	
	private String cctv_guid;
	private String cctv_name;
	private Double cctv_lng;
	private Double cctv_lat;
	private Double utmk_x;
	private Double utmk_y;
	private String cctv_hostport;
	private String cctv_seqno;
	private String cctv_kind;
	private String org_type;
	private String tel_no;
	private String cam_auth_data;
	private String cam_url;
	private String cctv_model;
	private String com_protocol;
	private String video_codec;
	private String reg_dt;
	private String mod_dt;
	private String user_id;
	
	public String getCctv_guid() {
		return cctv_guid;
	}
	public void setCctv_guid(String cctv_guid) {
		this.cctv_guid = cctv_guid;
	}
	public String getCctv_name() {
		return cctv_name;
	}
	public void setCctv_name(String cctv_name) {
		this.cctv_name = cctv_name;
	}
	public Double getCctv_lng() {
		return cctv_lng;
	}
	public void setCctv_lng(Double cctv_lng) {
		this.cctv_lng = cctv_lng;
	}
	public Double getCctv_lat() {
		return cctv_lat;
	}
	public void setCctv_lat(Double cctv_lat) {
		this.cctv_lat = cctv_lat;
	}
	public String getCctv_hostport() {
		return cctv_hostport;
	}
	public void setCctv_hostport(String cctv_hostport) {
		this.cctv_hostport = cctv_hostport;
	}
	public String getCctv_kind() {
		return cctv_kind;
	}
	public void setCctv_kind(String cctv_kind) {
		this.cctv_kind = cctv_kind;
	}
	public String getOrg_type() {
		return org_type;
	}
	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getCctv_model() {
		return cctv_model;
	}
	public void setCctv_model(String cctv_model) {
		this.cctv_model = cctv_model;
	}
	public String getCom_protocol() {
		return com_protocol;
	}
	public void setCom_protocol(String com_protocol) {
		this.com_protocol = com_protocol;
	}
	public String getVideo_codec() {
		return video_codec;
	}
	public void setVideo_codec(String video_codec) {
		this.video_codec = video_codec;
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Double getUtmk_x() {
		return utmk_x;
	}
	public void setUtmk_x(Double utmk_x) {
		this.utmk_x = utmk_x;
	}
	public Double getUtmk_y() {
		return utmk_y;
	}
	public void setUtmk_y(Double utmk_y) {
		this.utmk_y = utmk_y;
	}
	public String getCctv_seqno() {
		return cctv_seqno;
	}
	public void setCctv_seqno(String cctv_seqno) {
		this.cctv_seqno = cctv_seqno;
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
	
	
}

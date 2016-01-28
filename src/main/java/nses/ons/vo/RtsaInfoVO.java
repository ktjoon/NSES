package nses.ons.vo;


public class RtsaInfoVO {

	private String	seq_no;
	private String	reg_date;
	private String	type_desc;
	private String	type_other;
	private String	status_desc;
	private Double	gis_x;
	private Double	gis_y;
	private Double	coord_x;
	private Double	coord_y;
	private String	reg_dt;
	//
	private int		type_traffic;
	
	//
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getType_desc() {
		return type_desc;
	}
	public void setType_desc(String type_desc) {
		this.type_desc = type_desc;
	}
	public String getType_other() {
		return type_other;
	}
	public void setType_other(String type_other) {
		this.type_other = type_other;
	}
	public String getStatus_desc() {
		return status_desc;
	}
	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
	}
	public Double getGis_x() {
		return gis_x;
	}
	public void setGis_x(Double gis_x) {
		this.gis_x = gis_x;
	}
	public Double getGis_y() {
		return gis_y;
	}
	public void setGis_y(Double gis_y) {
		this.gis_y = gis_y;
	}
	public Double getCoord_x() {
		return coord_x;
	}
	public void setCoord_x(Double coord_x) {
		this.coord_x = coord_x;
	}
	public Double getCoord_y() {
		return coord_y;
	}
	public void setCoord_y(Double coord_y) {
		this.coord_y = coord_y;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public int getType_traffic() {
		return type_traffic;
	}
	public void setType_traffic(int type_traffic) {
		this.type_traffic = type_traffic;
	}
}

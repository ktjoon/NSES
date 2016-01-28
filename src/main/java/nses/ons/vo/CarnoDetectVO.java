package nses.ons.vo;

import nses.common.utils.ComDate;
import nses.common.vo.BaseResVO;

import com.google.gson.Gson;

public class CarnoDetectVO extends BaseResVO {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 5775199077418827055L;

	private String 	car_no;		// 인식된 차량번호
	// 인식된 이미지 영역의 RECT
	private int		left;
	private int		top;
	private int		right;
	private int		bottom;
	private String 	recog_rect;		// 인식된 차량번호
	// 인식한 시간
	private String 	curr_date;		// 인식된 시간
	
	public CarnoDetectVO() {
		curr_date	= ComDate.makeDateTimeString();
	}

	//
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getBottom() {
		return bottom;
	}
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	public String getRecog_rect() {
		return recog_rect;
	}
	public void setRecog_rect(String recog_rect) {
		this.recog_rect = recog_rect;
	}
	public void setRecog_rect_info() {
		this.recog_rect = String.format("%d,%d,%d,%d", left, top, right, bottom);
	}
	public String getCurr_date() {
		return curr_date;
	}

	public static CarnoDetectVO create(int nRetCode) {
		CarnoDetectVO	vo = new CarnoDetectVO();
		vo.setRetCode(nRetCode);
		
		if (nRetCode == ERR_CARNO_DETECT_FAIL) {
			vo.setRetMsg("DETECT_FAIL");
		}

		return vo;
	}

	public static CarnoDetectVO createFromJson(String sResult) {
		CarnoDetectVO	vo = new Gson().fromJson(sResult, CarnoDetectVO.class);
		
		if (vo == null) {
			return create(ERR_CCTV_CAPTURE_FAIL);
		}

		if (vo.isRetCode())
			vo.setRecog_rect_info();
		
		return vo;
	}
}

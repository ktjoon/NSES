/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nses.ons.vo;

import com.mysql.jdbc.util.Base64Decoder;


/**
 * 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.12.23              최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2014.12.23
 * @version 1.0
 * @see
 *
 *  Copyright (C) by NSES All right reserved.
 */
public class RecogDetailVO {
	private String	dsr_seq;
	private String	own_car_no;
	private String	recog_time;
	private String	recog_type;
	private String	recog_car_no;	
	private String	cctv_key;
	private String	cctv_name;
	private double	poi_x;
	private double	poi_y;
	private String	poi_addr;
	private String	sms_stat;
	private String	sms_dt;
	private byte[]	img_data1;
	private byte[]	img_data2;
	private byte[]	img_data3;
	private String	cctv_seqno;
	/*
	private String	img_data_1;
	private String	img_data_2;
	private String	img_data_3;
	*/
	
	private String	recog_img_rect;
	private String	reg_dt;
	
	private int 	recog_flag;				// 1: 저장, 2 : 단속처리, 3 : 메세지 전송
	
	
	
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
	public String getRecog_time() {
		return recog_time;
	}
	public void setRecog_time(String recog_time) {
		this.recog_time = recog_time;
	}
	public String getRecog_type() {
		return recog_type;
	}
	public void setRecog_type(String recog_type) {
		this.recog_type = recog_type;
	}
	public String getRecog_car_no() {
		return recog_car_no;
	}
	public void setRecog_car_no(String recog_car_no) {
		this.recog_car_no = recog_car_no;
	}
	public String getCctv_key() {
		return cctv_key;
	}
	public void setCctv_key(String cctv_key) {
		this.cctv_key = cctv_key;
	}
	public String getCctv_name() {
		return cctv_name;
	}
	public void setCctv_name(String cctv_name) {
		this.cctv_name = cctv_name;
	}
	public double getPoi_x() {
		return poi_x;
	}
	public void setPoi_x(double poi_x) {
		this.poi_x = poi_x;
	}
	public double getPoi_y() {
		return poi_y;
	}
	public void setPoi_y(double poi_y) {
		this.poi_y = poi_y;
	}
	public String getPoi_addr() {
		return poi_addr;
	}
	public void setPoi_addr(String poi_addr) {
		this.poi_addr = poi_addr;
	}
	public String getSms_stat() {
		return sms_stat;
	}
	public void setSms_stat(String sms_stat) {
		this.sms_stat = sms_stat;
	}
	public String getSms_dt() {
		return sms_dt;
	}
	public void setSms_dt(String sms_dt) {
		this.sms_dt = sms_dt;
	}
	public byte[] getImg_data1() {
		return img_data1;
	}
	public void setImg_data1(byte[] img_data1) {
		this.img_data1 = img_data1;
	}
	public byte[] getImg_data2() {
		return img_data2;
	}
	public void setImg_data2(byte[] img_data2) {
		this.img_data2 = img_data2;
	}
	public byte[] getImg_data3() {
		return img_data3;
	}
	public void setImg_data3(byte[] img_data3) {
		this.img_data3 = img_data3;
	}
	public String getRecog_img_rect() {
		return recog_img_rect;
	}
	public void setRecog_img_rect(String recog_img_rect) {
		this.recog_img_rect = recog_img_rect;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}

	
	/*
	public String getImg_data_1() {
		return img_data_1;
	}
	public void setImg_data_1(String img_data_1) {
		this.img_data_1 = img_data_1;
	}
	public String getImg_data_2() {
		return img_data_2;
	}
	public void setImg_data_2(String img_data_2) {
		this.img_data_2 = img_data_2;
	}
	public String getImg_data_3() {
		return img_data_3;
	}
	public void setImg_data_3(String img_data_3) {
		this.img_data_3 = img_data_3;
	}
	*/
	
	public int getRecog_flag() {
		return recog_flag;
	}
	public void setRecog_flag(int recog_flag) {
		this.recog_flag = recog_flag;
	}
	public String getCctv_seqno() {
		return cctv_seqno;
	}
	public void setCctv_seqno(String cctv_seqno) {
		this.cctv_seqno = cctv_seqno;
	}
	public byte[] getImgData(int nImgData) {
		if (nImgData == 2)
			return img_data2;
		if (nImgData == 3)
			return img_data3;
		
		return img_data1;
	}
	public byte[] getImgData_Decode64(int nImgData) {
		byte[] 	imgData = getImgData(nImgData);
		
		if (imgData == null)
			return null;
		
		// data:image/png;base64,

		if (imgData.length < 30)
			return imgData;
		
		int		dataPos = 0;
		
		for (int i = 0; i < 30; i ++) {
			if (imgData[i] == ',') {
				dataPos = i+1;
				break;
			}
		}
		return Base64Decoder.decode(imgData, dataPos, imgData.length - dataPos);
	}
}
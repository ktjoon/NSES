package nses.ons.vo;

import org.springframework.context.MessageSource;

import com.google.gson.Gson;

import nses.common.vo.BaseResVO;

public class CctvCaptureVO extends BaseResVO {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = 1796337719150205565L;

	// 
	private String 	url;			// 이미지 URL
	private String 	img_uri;		// 이미지 IMG_URI
	
	//
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg_uri() {
		return img_uri;
	}
	public void setImg_uri(String img_uri) {
		this.img_uri = img_uri;
	}


	public static CctvCaptureVO create(int nRetCode) {
		CctvCaptureVO	vo = new CctvCaptureVO();
		vo.setRetCode(nRetCode);
		
		if (nRetCode == ERR_CCTV_CAPTURE_FAIL) {
			vo.setRetMsg("CAPTURE_FAIL");
		}

		return vo;
	}

	public static CctvCaptureVO create(int nRetCode, MessageSource messageSource) {
		CctvCaptureVO	vo = new CctvCaptureVO();
		vo.setRetCode(nRetCode);
		
		if (nRetCode == ERR_CCTV_CAPTURE_FAIL) {
			vo.setRetMsg("CAPTURE_FAIL");
		}
		else if (nRetCode == ERR_REQ_RECORD_FAIL) {
			vo.setRetCode(ERR_REQ_RECORD_FAIL, messageSource);
		}

		return vo;
	}

	public static CctvCaptureVO createFromJson(String sResult) {
		CctvCaptureVO	vo = new Gson().fromJson(sResult, CctvCaptureVO.class);
		
		if (vo == null) {
			return create(ERR_CCTV_CAPTURE_FAIL);
		}

		if (vo.isRetCode()) {
			vo.setImg_uri(vo.getUrl());
		}
		
		return vo;
	}
	public static CctvCaptureVO createFromJsonR(String sResult, MessageSource messageSource) {
		CctvCaptureVO	vo = new Gson().fromJson(sResult, CctvCaptureVO.class);
		
		if (vo == null) {
			return create(ERR_REQ_RECORD_FAIL, messageSource);
		}

		if (vo.isRetCode()) {
			vo.setImg_uri(vo.getUrl());
		}
		
		return vo;
	}
}

package nses.ons.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nses.common.utils.ComStr;
import nses.common.utils.ComUtils;
import nses.common.vo.BaseResVO;
import nses.common.vo.ResultListVO;
import nses.ons.service.RtsaOccuridService;
import nses.ons.vo.RtsaReqVO;
import nses.ons.vo.RtsaResVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import egovframework.rte.fdl.property.EgovPropertyService;


@Controller
public class RtsaOccuridController {

	private static final Logger 	logger = LoggerFactory.getLogger(RtsaOccuridController.class);

	/** RtsaOccuridService */
	@Resource(name ="rtsaOccuridService")
	private RtsaOccuridService rtsaOccuridService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	

	@RequestMapping("/ons/rtsa/data_list.do")
	public void rtsaAction(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("data_list.do");
		
		String			sReqData = ComStr.toStr(params.get("req_data"));
		RtsaReqVO		reqData;
		
		reqData	= new Gson().fromJson(sReqData, RtsaReqVO.class);
		
		int				nResCode = BaseResVO.ERR_DATA_NOT_FOUND;
		List<?>			lstData	= null;
		RtsaResVO		resVO;
		String			sCarNo = "";

		if (reqData != null) {
			sCarNo = reqData.getCar_no();
			lstData	= rtsaOccuridService.selectListData(reqData);
		}

		if (lstData != null)
			nResCode = BaseResVO.RET_OK;

		resVO = RtsaResVO.create(nResCode);
		resVO.setRoutes(lstData);
		resVO.setCar_no(sCarNo);
		
		ComUtils.responseJson(request, response, resVO);
	}
}

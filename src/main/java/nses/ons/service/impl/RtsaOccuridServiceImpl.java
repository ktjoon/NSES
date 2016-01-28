package nses.ons.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import nses.ons.dao.RtsaOccuridDAO;
import nses.ons.service.RtsaOccuridService;
import nses.ons.vo.RtsaInfoVO;
import nses.ons.vo.RtsaReqVO;
import nses.ons.vo.RtsaReqVO.RouteInfo;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("rtsaOccuridService")
public class RtsaOccuridServiceImpl extends EgovAbstractServiceImpl implements RtsaOccuridService {

	/** RtsaOccuridDAO */
	@Resource(name="rtsaOccuridDAO")
	private RtsaOccuridDAO rtsaOccuridDAO;

	/**
     * UTIS 데이터 저장
     * @param lstData
     * @throws Exception
     */
	public void insertListData(List<Map<String, Object>> lstData) throws Exception {
		rtsaOccuridDAO.updateUseData("");
		rtsaOccuridDAO.insertListData(lstData);
	}
	
	/**
	 * UTIS 검색 데이터
	 * @param coord_list
	 * @return
	 * @throws Exception
	 */
	public List<?> selectListData(RtsaReqVO rtsa_data) throws Exception {
		ArrayList<RtsaReqVO.RouteInfo>	aryList = new ArrayList<RtsaReqVO.RouteInfo>();
		RtsaInfoVO		vo = new RtsaInfoVO();
		RtsaInfoVO		res = null;
		
		List<RouteInfo>		routes = rtsa_data.getRoutes();
		for (int i = 0; i < routes.size(); i ++) {
			RtsaReqVO.RouteInfo	info = routes.get(i);
			
			vo.setCoord_x(info.point.x);
			vo.setCoord_y(info.point.y);
			
			try {
				res = null;
				res = rtsaOccuridDAO.selectData(vo);
				if (res == null)
					info.tdp_type = 3;
				// 3: 원활
			} catch(Exception e) {
				System.out.println("*****************");
				e.printStackTrace();
			}
			
			if (res != null) {
				info.tdp_type = res.getType_traffic();
			}
//			System.out.println("link_idx: " + info.link_idx + ", point: [" + info.point.x + ", " + info.point.y + "]" + " - tdp_type: " + info.tdp_type);
			
			aryList.add(info);
		}

		return aryList;
	}
}

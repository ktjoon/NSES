package nses.ons.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import nses.ons.service.AccidentEmrsService;
import nses.ons.service.AccidentInfoService;
import nses.ons.vo.AccidentInfoVO;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.property.EgovPropertyService;


@Service
public class AccidentEmrsController {

	@Resource(name ="accidentemrsService")
	private AccidentEmrsService accidentEmrsService;
	
	@Resource(name = "accidentInfoService")
	private AccidentInfoService accidentInfoService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	public static int timeVal = 0;
	
	@SuppressWarnings("unchecked")
	@Scheduled(fixedDelay=10000)
	public void accidentList() throws Exception{
		
		timeVal++;
		
		if(timeVal % propertiesService.getInt("scheduleTime") != 0) {
			return ;
		}
		
		AccidentInfoVO accidentVO	= new AccidentInfoVO();
		
		List<?> lstData = accidentEmrsService.selectListData();
		
		List<?> astData = accidentInfoService.selectListData(accidentVO);
		
		List<AccidentInfoVO> nList = new ArrayList<AccidentInfoVO>();
		
		List<AccidentInfoVO> aList = new ArrayList<AccidentInfoVO>();
		
		nList = (List<AccidentInfoVO>) lstData;
		
		aList = (List<AccidentInfoVO>) astData;
		
		List<AccidentInfoVO> inList = new ArrayList<AccidentInfoVO>();
		
		List<AccidentInfoVO> upList = new ArrayList<AccidentInfoVO>();
		
		
		for(int i = 0; i<nList.size(); i++) {
			boolean flag = true;
			for(int k = 0; k<aList.size(); k++) {
				if(nList.get(i).getDsr_seq().equals(aList.get(k).getDsr_seq())) {
					flag = false;
				} 
			}
			
			if(flag) {
				AccidentInfoVO vo = new AccidentInfoVO();
				vo.setDsr_seq(nList.get(i).getDsr_seq());
				if(accidentInfoService.selectData(vo) < 1 ) {
					inList.add(nList.get(i));
				}
				
			}
			
		}
		
		for(int i = 0; i<aList.size(); i++) {
			boolean flag = true;
			for(int k = 0; k<nList.size(); k++) {
				if(aList.get(i).getDsr_seq().equals(nList.get(k).getDsr_seq())) {
					flag = false;
				}
			}
			
			if(flag) {
				upList.add(aList.get(i));
			}
		}
		
		if(inList.size() > 0) {
//			accidentInfoService.insertListData(inList);
		}
		
		if(upList.size() >0) {
//			accidentInfoService.updateListData(upList);
		}
		
		
	
	}
}

package nses.ons.web;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import nses.common.xml.XmlExecutor;
import nses.common.xml.XmlResValue;
import nses.ons.service.RtsaOccuridService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.property.EgovPropertyService;


@Service
public class RtsaDaemoController {

	private static final Logger 	logger = LoggerFactory.getLogger(RtsaDaemoController.class);

	/** RtsaOccuridService */
	@Resource(name ="rtsaOccuridService")
	private RtsaOccuridService rtsaOccuridService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	public static int 		s_timeVal = 0;
	
	@Scheduled(fixedDelay=10000)
	public void rtsaDataInsert() throws Exception {
		
		s_timeVal ++;

		if (s_timeVal % propertiesService.getInt("rtsaSchTime") != 0) {
			return;
		}
		
		String		sRtsaDir = propertiesService.getString("rtsaSchDir");
		String		sBackDir = propertiesService.getString("rtsaBackDir");
		File 		files[] = new File(sRtsaDir).listFiles(new FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return false;
				}
				String	fileName = f.getName().toLowerCase();

				return fileName.endsWith(".xml");
			}
		});
		
		if (files == null) {
			return;
		}

		
		for (File file:files) {
			XmlResValue 	res;
			
			res = XmlExecutor.parse(file.getAbsolutePath());
			
			logger.info("RTSA-File: " + file.getAbsolutePath() + ", Count: " + res.getCount());

			if (res.getCount() > 0) {
				res.convCoord();
				
				List<Map<String, Object>> aryList = res.toArrayList();

				rtsaOccuridService.insertListData(aryList);
			}
			
			File	fBack = new File(sBackDir, file.getName());
			
			if (file.renameTo(fBack) == false) {
				fBack.delete();
				file.renameTo(fBack);
			}
		}
	}

}

package nses.common.xml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XmlExecutor {
	//
	// ---------------------------------------------------------------------
	// 외부에서 호출하는 함수들
	// ---------------------------------------------------------------------
	public static XmlResValue parse(String sFilePath) {
		XmlResValue 	result = new XmlResValue();
		InputStream		is = null;

		try {
			is = new FileInputStream(sFilePath);
			DocumentBuilderFactory 	factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder 		builder = factory.newDocumentBuilder();
			Document 				doc = builder.parse(is);
			
			result.makeValueFromXmlString(doc);
			result.convCoord();

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try { is.close(); } catch(Exception e) { }
			}
		}
		
		return result;
	}


	//
	/*
	public static void main(String[] args) {
		XmlResValue 	res;
		res = XmlExecutor.parse("C:\\Intel\\xtest.xml");
		res.convCoord();
		
		ArrayList<Map<String,Object>>	aryList = res.toArrayList();
		
		for (int i = 0; i < aryList.size(); i ++) {
			Map<String,Object>	map = aryList.get(i);
			dumpMap(map);
		}
	}
	*/
	public static void dumpMap(Map<String,Object> map) {
		Iterator<Entry<String, Object>>		iter = map.entrySet().iterator();

		while (iter.hasNext()) {
			Entry<String, Object>	ent = iter.next();

			 System.out.println("KEY:" + ent.getKey() + ", VAL:" + ent.getValue());
		}
	}
}

package nses.common.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlResValue {
	public	static	final	int			RET_OK 			= 0;
	public	static	final	int			ERR_NODATA 		= -1;

	//
	private static final boolean		g_bDebug = false;

	//
	private	int							m_nRetCode = ERR_NODATA;

	private	XmlResData					m_rdxHead;
	private	ArrayList<XmlResData>		m_aryBody = null;

	//
	public XmlResValue() {
		m_rdxHead = new XmlResData();
		m_aryBody = new ArrayList<XmlResData>();
	}

	// ---------------------------------------------------------------------
	// 외부에서 호출하는 함수들
	// ---------------------------------------------------------------------
	public void setRetcode(int nRetCode) {
		m_nRetCode = nRetCode;
	}
	public int GetRetcode() {
		return m_nRetCode;
	}
	public boolean IsRetOK() {
		return m_nRetCode == RET_OK;
	}
	public boolean IsRetError() {
		return m_nRetCode != RET_OK;
	}
	//
	public void clear() {
		m_rdxHead.clear();
		if (m_aryBody != null) {
			m_aryBody.clear();
		}
	}
	//
	public void convCoord() {
		if (m_aryBody != null) {
			for (int i = 0; i < m_aryBody.size(); i ++) {
				m_aryBody.get(i).convCoord();
			}
		}
	}
	/*
	public String toJson() {
		String		sJson = "";
		JSONWriter 	builder = new JSONStringer();

		try {
			builder.object();

			builder.key("head");
			m_rdxHead.toJson(builder);

			builder.key("body");
			builder.array();
			if (m_aryBody != null) {
				for (int i = 0; i < m_aryBody.size(); i ++) {
					m_aryBody.get(i).toJson(builder);
				}
			}
			builder.endArray();

			builder.endObject();
			
			sJson = builder.toString();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return sJson;
	}
	*/

	//
	public int getCount() {
		if (m_aryBody == null)
			return 0;
		return m_aryBody.size();
	}
	public XmlResData item(int index) {
		return m_aryBody.get(index);
	}
	public String getString(int index, String sName) {
		return m_aryBody.get(index).getString(sName); 
	}
	public int getInteger(int index, String sName) {
		return m_aryBody.get(index).getInteger(sName); 
	}

	public ArrayList<Map<String,Object>> toArrayList() {
		ArrayList<Map<String,Object>>	aryList = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < getCount(); i ++) {
			aryList.add(m_aryBody.get(i).getData());
		}
		return aryList;
	}
	//
	public void makeValueFromXmlString(String sXmlStr) {
		if (parseXmlResult(sXmlStr))
			m_nRetCode = RET_OK;
	}
	public void makeValueFromXmlString(Document doc) {
		if (parseXmlResult(doc))
			m_nRetCode = RET_OK;
	}

	// ---------------------------------------------------------------------
	// 내부에서 사용되는 함수들
	// ---------------------------------------------------------------------
	/*
	 * - Xml Data Sample -
	 * 
	 * <RpcResult>
	 *   <RpcHead>
	 *     <title>okman</title>
	 *     <items>1234</items>
	 *   </RpcHead>
	 *   <RpcBody>
	 *     <RpcData>
	 *       <memid>111</memid>
	 *       <memname>test_01</memname>
	 *       <groupid>group</groupid>
	 *     </RpcData>
	 *     <RpcData>
	 *       <memid>123</memid>
	 *       <memname>test02</memname>
	 *       <groupid>group</groupid>
	 *     </RpcData>
	 *   </RpcBody>
	 * </RpcResult>
	 */
	private boolean parseXmlResult(String sResXml) {
		boolean		bRet = false;

		if (m_aryBody == null)
			m_aryBody = new ArrayList<XmlResData>();
		else
			m_aryBody.clear();

		InputStream 	istream = null;

		try {
			DocumentBuilderFactory 	factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder 		builder = factory.newDocumentBuilder();
			Document 				doc;

			istream = new ByteArrayInputStream(sResXml.getBytes("utf-8"));
			doc = builder.parse(istream);

			parseXmlResult(doc);

			bRet = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (istream != null) {
				try { istream.close(); } catch(Exception e) {}
				istream = null;
			}
		}

		return bRet;
	}
	private boolean parseXmlResult(Document doc) {
		boolean		bRet = false;

		if (m_aryBody == null)
			m_aryBody = new ArrayList<XmlResData>();
		else
			m_aryBody.clear();

		try {
			Element 	elmSkin = doc.getDocumentElement();
			NodeList 	nodeData = elmSkin.getElementsByTagName("RTSA_OCCURID");

			if (nodeData != null) {
				for (int i = 0; i < nodeData.getLength(); i ++) {
					if (g_bDebug) System.out.println("TagName: " + nodeData.item(i).getNodeName());
					XmlResData		rda = parseRowData(nodeData.item(i));
					if (rda != null)
						m_aryBody.add(rda);
				}
			} 

			bRet = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return bRet;
	}
	private XmlResData parseRowData(Node rowItems) {
		if (rowItems == null)
			return null;

		XmlResData 	res = new XmlResData();
		NamedNodeMap 	nodeMap = rowItems.getAttributes();

		if (g_bDebug) System.out.println("  NodeMap: " + nodeMap.getLength());

		String			sName, sValue;

		for (int i = 0; i < nodeMap.getLength(); i ++) {
			Node	node = nodeMap.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE)
				continue;

			sValue 	= node.getNodeValue();
			sName 	= node.getNodeName();

			if (sName == null || sName.charAt(0) == '#')
				continue;

			if (sValue != null) {
				if (g_bDebug) System.out.println("  NODE-ATTR " + sName + "=" + sValue);
				res.SetValue(sName, sValue.trim());
			}
		}

		return res;
	}
}

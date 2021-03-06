package nses.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpReader {
	public static boolean				s_bDebug = true;
	private static final Logger 		logger = LoggerFactory.getLogger(HttpReader.class);
	
	//
	public static int 					s_nTimeoutDefSec = 5;

	//
	/*
	 * Http GET Method
	 * getDocument
	 * sUrl : HTTP URL 주소
	 */
	public static String getDocument(String sUrl) {
		return 	getHttpDocument(sUrl, "", s_nTimeoutDefSec, false);
	}
	public static String getDocument(String sUrl, int nTimeoutSec) {
		return 	getHttpDocument(sUrl, "", nTimeoutSec, false);
	}

	/*
	 * Http POST Method
	 * getDocument
	 * sUrl : POST URL 주소
	 * sParam : POST 파라미터
	 */
	public static String getDocument(String sUrl, String sParam, int nTimeOutSec) {
		return 	getHttpDocument(sUrl, sParam, nTimeOutSec, false);
	}
	
	//
	public static String getHttpDocument(String sUrl, String sParam, int nTimeoutSec, boolean bLogParam) {
		String			sRetVal = "";
		int				nTimeoutMS = 15000;

		if (s_bDebug) {
			logger.info("URL:" + sUrl);
		}

		try {
			URL 				url = new URL(sUrl);
			HttpURLConnection 	conn = null;

			conn = (HttpURLConnection) url.openConnection();
			if (nTimeoutSec > 0)
				nTimeoutMS = nTimeoutSec * 1000;
			conn.setConnectTimeout(nTimeoutMS);
			conn.setReadTimeout(nTimeoutMS);
			conn.setUseCaches(false);

			if (sParam.length() > 0) {
				if (s_bDebug && bLogParam) {
					logger.info("parameters:" + sParam);
				}
				conn.setDoOutput(true);
				OutputStreamWriter 	writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				writer.write(sParam);
				writer.flush();
				writer.close();
			}

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader 		reader = null;
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String 				sLine = null;
				StringBuffer 		sbData = new StringBuffer();

				while ((sLine = reader.readLine()) != null) {
					sbData.append(sLine);
				}
				reader.close();

				sRetVal = sbData.toString();
				if (s_bDebug) {
					logger.info("result:" + sRetVal);
				}
			}
			else {
				if (s_bDebug) {
					logger.info("Http ResponseCode : " + conn.getResponseCode());
				}
			}
		} catch (SocketTimeoutException e) {
			logger.info("Connect Timeout : " + nTimeoutMS + " ms");
		} catch (Exception e) {
			logger.error("getHttpDocument", e);
		}
		return sRetVal;
	}

	//
	public static void responseImage(HttpServletResponse response, String sUrl) {
		putHttpDocument(sUrl, "", s_nTimeoutDefSec, response);
	}
	public static void putHttpDocument(String sUrl, String sParam, int nTimeoutSec, HttpServletResponse response) {
		int				nTimeoutMS = 15000;

		if (s_bDebug) {
			logger.info("URL:" + sUrl);
		}

		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 
		response.setContentType("image/jpeg");

		BufferedInputStream 	bis = null;
		BufferedOutputStream 	bos = null;
		
		try {
			URL 				url = new URL(sUrl);
			HttpURLConnection 	conn = null;

			conn = (HttpURLConnection) url.openConnection();
			if (nTimeoutSec > 0)
				nTimeoutMS = nTimeoutSec * 1000;
			conn.setConnectTimeout(nTimeoutMS);
			conn.setReadTimeout(nTimeoutMS);
			conn.setUseCaches(false);

			if (sParam.length() > 0) {
				if (s_bDebug) {
					logger.info("parameters:" + sParam);
				}
				conn.setDoOutput(true);
				OutputStreamWriter 	writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				writer.write(sParam);
				writer.flush();
				writer.close();
			}

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				byte 					byteData[] = new byte[4096];

				bis = new BufferedInputStream(conn.getInputStream());
				bos = new BufferedOutputStream(response.getOutputStream());

				int nRead = 0;
				while ((nRead = bis.read(byteData)) > 0) {
					bos.write(byteData , 0, nRead);
			   	}
			}
			else {
				if (s_bDebug) {
					logger.info("Http ResponseCode : " + conn.getResponseCode());
				}
			}
		} catch (SocketTimeoutException e) {
			logger.info("Connect Timeout : " + nTimeoutMS + " ms");
		} catch (Exception e) {
			logger.error("putHttpDocument", e);
		} finally {
			if (bis != null) try { bis.close(); } catch(Exception e) { bis = null; }
			if (bos != null) try { bos.close(); } catch(Exception e) { bos = null; }
		}
	}

	public static String makeHttpParams(Map<String,Object> params) {
		StringBuilder						sb = new StringBuilder();
		Iterator<Entry<String, Object>> 	it = params.entrySet().iterator();

		while (it.hasNext()) {
			if (sb.length() > 0)
				sb.append("&");
			makeNameValue(sb, it.next());
		}

		return sb.toString();
	}
	public static void makeNameValue(StringBuilder sb, Entry<String, Object> entry) {
		try {
			sb.append(entry.getKey());
			sb.append("=");
			if (entry.getValue() != null) {
				sb.append(URLEncoder.encode(ComStr.toStr(entry.getValue()), "UTF-8"));
			}
		} catch(UnsupportedEncodingException e) { }
	}
	public static StringBuilder makeNameValue(StringBuilder sb, String sName, Object oValue) {
		sb.append(sName);
		try {
			if (oValue != null) {
				sb.append(URLEncoder.encode(ComStr.toStr(oValue), "UTF-8"));
			}
		} catch(UnsupportedEncodingException e) { }
		return sb;
	}
	public static StringBuilder makeNameValueA(StringBuilder sb, String sName, Object oValue) {
		sb.append(sName);

		if (oValue != null)
			sb.append(ComStr.toStr(oValue));
		return sb;
	}
}

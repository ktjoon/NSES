package nses.common.utils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class ComUtils {
	//
	private static final Logger 	logger = LoggerFactory.getLogger(ComUtils.class);

	//
	public static void responseJson(HttpServletRequest request, HttpServletResponse response, Object oSrcObj, Logger logger) throws Exception {

		Gson 		gson = new Gson();
		String 		sJson = gson.toJson(oSrcObj);

		if (logger != null)
			logger.info("json:" + sJson);

		byte[]		byJson = sJson.getBytes("UTF-8");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.setContentLength(byJson.length);

		OutputStream	os = null;
		try {
			os = response.getOutputStream();
			os.write(byJson);
			os.flush();
		} catch (IOException e) {
			logger.error("responseJson", e);
		} finally {
			if (os != null) {
				try { os.close(); } catch(Exception e) { os = null; }
			}
		}
	}
	public static void responseJson(HttpServletRequest request, HttpServletResponse response, Object oSrcObj) throws Exception {
		responseJson(request, response, oSrcObj, logger);
	}
	public static void responseJsonEx(HttpServletRequest request, HttpServletResponse response, Object oSrcObj) throws Exception {

		Gson 		gson = new Gson();
		String 		sJson = gson.toJson(oSrcObj);

		// logger.info("json:" + sJson);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		//response.setContentType("text/plan;charset=UTF-8");

		PrintWriter pw = response.getWriter();
		pw.write(sJson);
		pw.flush();
	}

	//
	public static void responseImage(HttpServletResponse response, byte[] imgData) {
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 
		response.setContentType("image/png");

		if (imgData == null) {
			response.setContentLength(0);
			return;
		}

		BufferedOutputStream 	bos = null;
		
		try {
			bos = new BufferedOutputStream(response.getOutputStream());
			bos.write(imgData);
		} catch (IOException e) {
			logger.error("responseImage", e);
		} catch (Exception e) {
			logger.error("responseImage", e);
		} finally {
			if (bos != null) try { bos.close(); } catch(Exception e) { bos = null; }
		}
	}
	public static boolean writeImage(String sFilePath, byte[] imgData) {
		boolean		bRet = false;

		if (imgData == null) {
			return false;
		}

		BufferedOutputStream 	bos = null;
		
		try {
			bos = new BufferedOutputStream(new FileOutputStream(sFilePath));
			bos.write(imgData);
			bRet = true;
		} catch (IOException e) {
			logger.error("writeImage", e);
		} catch (Exception e) {
			logger.error("writeImage", e);
		} finally {
			if (bos != null) try { bos.close(); } catch(Exception e) { bos = null; }
		}
		
		return bRet;
	}
}

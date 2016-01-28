/*------------------------------------------------------------------------------
 * comStr.java
 * mGIS, Version 1.0
 * 
 * Copyright rights reserved.
 *(c) 2009-2012 mGIS.,
 * mGIS * All 
 *----------------------------------------------------------------------------*/
package nses.common.utils;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * CMS STRING변환
 *  
 * @author jssong
 */
public class ComStr {
	private static String[] nlTags = { "P", "CENTER", "HR", "BR", "LI",
		   "UL", "/UL", "OL",  "/OL", 
		   "TABLE", "/TABLE", "/TR",
		   "META", "BODY", "/BODY", "/HTML",
		   "/HEAD", "/TITLE" };
	
	
	/**
	 * 생성자
	 */
	public ComStr() {
		
	}
	
	
	/**
	 * String 값을 Encoding한다
	 * @param strIn
	 * @return retStr
	 */
    public static String DSEncode(String strIn) {
        strIn = strIn + "PASSWD";
        String retStr = "";
        for (int i = 0; i < strIn.length(); i++) {
            retStr += (char) ((int) strIn.charAt(i) + (i % 2) + 1);
        }
        return retStr;
    }

    /**
     * String 값을 Decoding한다
     * @param strIn
     * @return retStr
     */
    public static String DSDecode(String strIn) {
        String retStr = "";
        for (int i = 0; i < (strIn.length()); i++) {
            retStr += (char) ((int) (strIn.charAt(i)) - (i % 2) - 1);
        }
        if (retStr.length() < 6 || !retStr.substring(retStr.length() - 6).equals("PASSWD")) {
            retStr = "";
        } else {
            retStr = retStr.substring(0, retStr.length() - 6);

        }
        return retStr;
    }
	
    /**
     * 마지막 특정 문자 제거 하기
     * @param str
     * @param delChar
     * @return String
     */
    public static String delRightChar(String str, char delChar) {
        String value = str;

        while (value.length() > 0) {
            int i = value.length() - 1;
            if (value.charAt(i) == delChar) {
                value = value.substring(0, i);
            } else
                break;
        }
        return value;
    }
    
    /**
     * NULL값 처리
     * @param strTarget
     * @return String
     */
    public static String getNullConv(String strTarget) {
    	return getNullConv(strTarget, "");
    }

    /**
     * NULL값 처리
     * @param strTarget
     * @param strConv
     * @return String
     */
    public static String getNullConv(String strTarget, String strConv) {
    	String szTemp;

    	if(strConv == null)	strConv = "";

    	if(strTarget == null || "".equals(strTarget) || "null".equals(strTarget)) {
    		szTemp = strConv;
    	} else {
    		szTemp = strTarget;
    	}
    	
    	//szTemp 		= szTemp.replace("'", "´");
    	//szTemp  	= szTemp.replace("\"","˝");
    	//szTemp  	= szTemp.replace("<","&lt;");
    	//szTemp  	= szTemp.replace(">","&gt;");
    	
    	return szTemp;
    }
    
    /**
     * TEXTAREA 값 NULL값 처리
     * @param strTarget
     * @param strConv
     * @return String
     */
    public static String getNullConvTextarea(String strTarget) {
    	return getNullConvTextarea(strTarget, "");
    }
    
    /**
     * TEXTAREA 값 NULL값 처리
     * @param strTarget
     * @param strConv
     * @return String
     */
    public static String getNullConvTextarea(String strTarget, String strConv) {
    	String szTemp;

    	if(strConv == null)	strConv = "";

    	if(strTarget == null || "".equals(strTarget) || "null".equals(strTarget)) {
    		szTemp = strConv;
    	} else {
    		szTemp = strTarget;
    	}
    	
    	return szTemp;
    }

    /**
     * NULL값 처리
     * @param strTarget
     * @param intConv
     * @return Integer
     */
    public static int intNullChk(String strTarget, int intConv) {
        int 	returnValue = 0;

        try {
	        if (strTarget == null || ("").equals(strTarget.trim()) || ("null").equals(strTarget))
	                returnValue = intConv;
	        else returnValue = Integer.parseInt(strTarget.trim());
        } catch (NumberFormatException e) {
        	returnValue = intConv;
        }

        return returnValue;
    }
    public static long longNullChk(String strTarget, int intConv) {
    	long 	returnValue = 0;

        try {
	        if (strTarget == null || ("").equals(strTarget.trim()) || ("null").equals(strTarget))
	                returnValue = intConv;
	        else returnValue = Long.parseLong(strTarget.trim());
        } catch (NumberFormatException e) {
        	returnValue = intConv;
        }

        return returnValue;
    }

    public static int toInt(Object oTarget) {
    	String 	sTarget = null;
    	if (oTarget != null)
    		sTarget = oTarget.toString();
        return intNullChk(sTarget, 0);
    }
    public static int toInt(Object oTarget, int nDefVal) {
    	String 	sTarget = null;
    	if (oTarget != null)
    		sTarget = oTarget.toString();
        return intNullChk(sTarget, nDefVal);
    }
    public static long toLong(Object oTarget, int nDefVal) {
    	String 	sTarget = null;
    	if (oTarget != null)
    		sTarget = oTarget.toString();
        return longNullChk(sTarget, nDefVal);
    }
    public static int toIntEx(Object oTarget, int nDefVal) {
    	String 	sTarget = null;
    	if (oTarget != null)
    		sTarget = oTarget.toString();
    	int nVal = intNullChk(sTarget, nDefVal);
    	if (nVal < nDefVal)
    		nVal = nDefVal;
    	return nVal;
    }
    public static double toDouble(Object oTarget) {
    	if (oTarget == null)
    		return 0.0;
    	if (oTarget instanceof Double)
    		return (Double)oTarget;

    	double 	dbVal = 0.0;
    	try {
    		dbVal = Double.parseDouble(oTarget.toString());
    	} catch(NumberFormatException e) { dbVal = 0.0; }

        return dbVal;
    }
    public static String toStr(Object oTarget) {
    	String 	sTarget;
    	if (oTarget != null)
    		sTarget = oTarget.toString();
    	else
    		sTarget = "";
        return sTarget;
    }
    public static String toUpper(Object oTarget) {
    	String 	sTarget;
    	if (oTarget != null)
    		sTarget = oTarget.toString();
    	else
    		sTarget = "";
        return sTarget.toUpperCase();
    }
    public static String toHtmlStr(Object oTarget) {
    	return toStr(oTarget).replace("\"", "&quot;");
    }

    /**
     * 특정 문자 치환
     * @param str
     * @param index_str
     * @param new_str
     * @return String
     */
    public static String getChangeString(String str, String index_str, String new_str) {
        String temp = "";
        if (str != null && str.indexOf(index_str) != -1) {
            while (str.indexOf(index_str) != -1) {
                temp = temp + str.substring(0, str.indexOf(index_str)) + new_str;
                str = str.substring(str.indexOf(index_str) + index_str.length());
            }
            temp = temp + str;
        } else {
            temp = str;
        }

        return temp;
    }
    
    /**
     * 오른쪽 공백 문자 제거
     * @param  str
     * @return String
     */ 
    public static String rightTrim(String str) {
        if (str == null || str.equals(""))
          return str;

        int i;
        int len = str.length();

        for (i = 0; i < len; i++) {
          if (str.charAt(len - i - 1) != ' ')
            break;
        }

        return str.substring(0, len - i);
      }
    
    /**
     * ","를 기준으로 배열를 만든후 반환(split)
     * @param  str
     * @return String[]
     */
    public static String[] strArrToken(String str) {
		return strArrToken(str, ",");
	}

	/**
	 * 특정 문자를 기준으로 배열를 만든후 반환(split)
	 * @param  str
	 * @param  delemiter
	 * @return String[]
	 */
	public static String[] strArrToken(String str, String delemiter) {
		StringTokenizer	token			=	new StringTokenizer(str, delemiter);
		StringBuffer	temp			=	new StringBuffer();
		int		cnt						=	token.countTokens();
		String	strArr[]				=	new String[cnt];

		for(int i=0; i<cnt; i++) {
			strArr[i]					=	token.nextToken();
			temp.append(strArr[i]);
		}

		return strArr;
	}
	
	/**
     * SPLIT함수
     * @param  text
     * @param  splitword
     * @return String []
     */
    public static String [] StringSplit(String text, String splitword)
	{
		StringTokenizer oToken = new StringTokenizer(text, splitword);
		
		String [] strarrReturn = new String[oToken.countTokens()];
		
		for(int i=0; i<strarrReturn.length; i++)
		{
			strarrReturn[i] = oToken.nextToken();
		}
		
		return strarrReturn;
	}
    
    /**
     * String에서 해당 INDEX값을 반환
     * @param  str
     * @param  indexstr
     * @param  fromindex
     * @return int
     */
    public static int indexOfaA(String str, String indexstr, int fromindex) {
        int index = 0;
        indexstr = indexstr.toLowerCase();
        str = str.toLowerCase();
        index = str.indexOf(indexstr, fromindex);
        return index;
    }
    
    /**
     * getConv 리턴한다
     * @param  objKey
     * @return getConv
     */
    public static String getConv(Object objKey ) {
        
        return getConv(objKey, "" );
    }      
    
    /**
     * Object를 String 으로 반환한다
     * @param  objKey
     * @param  strVal
     * @return String
     */
    public static String getConv(Object objKey , String strVal) {
        if (objKey != null && !objKey.equals("null")) {
            String strKey = objKey.toString().trim();
           
           if (!"".equals(strKey)) return strKey;
        }
        return strVal;
    }
        
    /**
	 * 태그 제거 함수 
	 * @param souceStr: 원본 문자열 
	 * @return
	 */
	public static String removeHtmlTag(String souceStr) {
		final int NORMAL_STATE = 0;
		final int TAG_STATE = 1;
		final int START_TAG_STATE = 2;
		final int END_TAG_STATE = 3;
		final int SINGLE_QUOT_STATE = 4;
		final int DOUBLE_QUOT_STATE = 5;
		int state = NORMAL_STATE;
		int oldState = NORMAL_STATE;
		char[] chars = souceStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		boolean bSlash = false;
		String tagWord = "";
		boolean tagWordDone = false;
		char a;
		for (int i = 0; i < chars.length; i++) {
			a = chars[i];
			switch (state) {
				case NORMAL_STATE:
					if (a == '<') {
						tagWord = "";
						state = TAG_STATE;
					}
					else
						sb.append(a);
					break;
				case TAG_STATE:
					if (a == '>')
						state = NORMAL_STATE;
					else if (a == '\"') {
						oldState = state;
						state = DOUBLE_QUOT_STATE;
					}
					else if (a == '\'') {
						oldState = state;
						state = SINGLE_QUOT_STATE;
					}
					else if (a == '/') {
						tagWord = "" + a;
						tagWordDone = false;
						state = END_TAG_STATE;
					}
					else if (a != ' ' && a != '\t' && a != '\n' && a != '\r' && a != '\f') {
						tagWord = "" + a;
						tagWordDone = false;
						state = START_TAG_STATE;
					}
					break;
				case START_TAG_STATE:

				case END_TAG_STATE:
					if (a == '>') {
						if (isNewLineTag(tagWord)) {
							//sb.append(CRLF);                        
						}
						state = NORMAL_STATE;
					}
					else if (a == '\"') {
						oldState = state;
						state = DOUBLE_QUOT_STATE;
					}
					else if (a == '\'') {
						oldState = state;
						state = SINGLE_QUOT_STATE;
					}
					else if (a == ' ' || a == '\t' || a == '\n' || a == '\r' || a == '\f') {
						tagWordDone = true;
					}
					else if (!tagWordDone) {
						tagWord += a;
					}
					break;

				case DOUBLE_QUOT_STATE:
					if (bSlash)
						bSlash = false;
					else if (a == '\"')
						state = oldState;
					else if (a == '\\')
						bSlash = true;
					break;
				case SINGLE_QUOT_STATE:
					if (bSlash)
						bSlash = false;
					else if (a == '\'')
						state = oldState;
					else if (a == '\\')
						bSlash = true;
					break;
			}
		}
		String tmpStr = sb.toString();
		if(tmpStr!=null){
			tmpStr = replaceStr(tmpStr,"&nbsp;","");
		}
		return tmpStr;
	}		
	
	public static boolean isNewLineTag(String s) {
		if (s == null)
			return false;
		String str = allTrim(s);
		for (int i = 0; i < nlTags.length; i++) {
			if (str.equalsIgnoreCase(nlTags[i]))
				return true;
		}
		return false;
	}
	
	/**
	 * 문자열 치환 함수 
	 * @param sourceStr: 원본 문자열
	 * @param oldStr: 찾을 문자열 
	 * @param newStr: 치환할 문자열 
	 * @return oldStr을 newStr로 치환한 String값 리턴
	 */
	public static String replaceStr(String sourceStr, String oldStr, String newStr){
		int s = 0;
		int e = 0;
		
		if(sourceStr!=null){		
			StringBuffer result = new StringBuffer();
	
			while ((e = sourceStr.indexOf(oldStr, s)) >= 0) {
				result.append(sourceStr.substring(s, e));
				result.append(newStr);
				s = e + oldStr.length();
			}
			result.append(sourceStr.substring(s));
			return result.toString();
		}else
			return "";
	} 	
	
	public static String allTrim(String s) {
		if (s == null)
			return null;
		else if (s.length() == 0)
			return "";

		int len = s.length();
		int i = 0;
		int j = len - 1;

		for (i = 0; i < len; i++) {
			if ( s.charAt(i) != ' ' && s.charAt(i) != '\t'
									&& s.charAt(i) != '\r'
									&& s.charAt(i) != '\n' )
				break;
		}
		if (i == len)
			return "";

		for (j = len - 1; j >= i; j--) {
			if ( s.charAt(i) != ' ' && s.charAt(i) != '\t'
									&& s.charAt(i) != '\r'
									&& s.charAt(i) != '\n' )
				break;
		}
		return s.substring(i, j + 1);
	}	
	
	/**
	 * 문자열을 지정한 길이에서 자르기, 초과일 경우 "..."이 붙음 
	 * @param souceStr: 원본 문자열
	 * @param byteLen: 바이트 수
	 * @return
	 * @throws Exception
	 */
	public static String getCutStr(String souceStr, int byteLen) throws Exception{
		String szLeft = trim(souceStr);
		int nszLeft = szLeft.length();
		for(int i = 1; i <= nszLeft; i++){
			szLeft = souceStr.substring(0, i);
			if(getStrLen(szLeft) <= byteLen)
				continue;
			szLeft = souceStr.substring(0, i - 1);
			szLeft = szLeft + "...";
			break;
		}
		return szLeft;
	}	
	
	/**
	 * 원본문자열이 null이 아닐 경우 trim()적용후 리턴
	 * (null일 경우 ""이 리턴됨)
	 * @param tmpStr
	 * @return
	 * @throws Exception
	 */	
	public static String trim(String tmpStr) throws Exception{
		if(tmpStr!=null)
			return tmpStr.trim();
		else
			return "";
	}
	
	/**
	 * 문자열의 byte길이 리턴
	 * @param sourceStr: 원본문자열 
	 * @return
	 * @throws Exception
	 */
	public static int getStrLen(String sourceStr) throws Exception{
		int nLen = 0;
		sourceStr = trim(sourceStr);
		for(int i = 0; i < sourceStr.length(); i++){
			char szEach = sourceStr.charAt(i);
			if('\0' <= szEach && szEach <= '\377')
				nLen++;
			else
				nLen += 2;
		}
		return nLen;
	}
	/**
	 * 배열(문자열)을 문자로 변환
	 * @param sourceStr: 원본문자열 
	 * @return
	 * @throws Exception
	 */
	public static String getArrayToStr(String[] sourceStr, String delim) throws Exception{
		StringBuffer sbRtn = new StringBuffer();
		String strDeli = delim;
		
		if (strDeli.length() < 1) {strDeli = ",";}
		
		if (sourceStr.length > 0){sbRtn.append(sourceStr[0]);}
		
		for(int i = 1; i < sourceStr.length; i++){
			sbRtn.append(strDeli).append(sourceStr[i]);
		}
		return sbRtn.toString();
	}
	
	public static String toJS(String str)
	{
		return str.replace("\\", "\\\\")
				  .replace("\'", "\\\'")
				  .replace("\"", "\\\"")
				  .replace("\r\n", "\\n")
				  .replace("\n", "\\n");
	}

	//
	public static String makeMBString(Object oBytes) {
		return makeMBString(toInt(oBytes));
	}
	public static String makeMBString(int nBytes) {
		if (nBytes <= 0)
			return "-";
			//return "0 KB";
		if (nBytes <= 1000)
			return "1 KB";

		float		fKBytes = nBytes / 1000f;
		if (fKBytes < 1000f) {
			return String.format("%5.1f KB", fKBytes);
		}

		return String.format("%6.1f MB", fKBytes / 1000f).trim();
	}

	public static String makeCommaString(Object oSize) {
		return makeCommaString(toInt(oSize));
	}
	public static String makeCommaString(int nSize) {
		return new DecimalFormat("#,##0").format(nSize);
	}

	//
	public static boolean isEmpty(Object oSrc) {
		if (oSrc == null) {
			return true;
		}
		if (oSrc instanceof String) {
			return ((String) oSrc).isEmpty();
		}

		return "".equals(oSrc.toString());
	}
	public static boolean isEmpty(String sStr) {
		if (sStr == null) {
			return true;
		}

		return sStr.isEmpty();
	}
}
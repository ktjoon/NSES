package nses.common.utils;

public class DbTextUtils {

	public static String makeDispText_TelNo(String sDbText) {
		String		sDispText = "";
		String		strTel = sDbText.trim() + "--";
		String[]	arrTel;
		arrTel = strTel.split("-");
		if (arrTel.length >= 3) {
			if (arrTel[0].isEmpty() == false && arrTel[1].isEmpty() == false && arrTel[2].isEmpty() == false)
				sDispText = sDbText;
		}
		return sDispText;
	}
	public static String[] makeEditText_TelNo(String sDbText) {
		boolean		bValid = false;
		String		strTel = sDbText.trim() + "--";
		String[]	arrTel;
		arrTel = strTel.split("-");
		if (arrTel.length >= 3) {
			if (arrTel[0].isEmpty() == false && arrTel[1].isEmpty() == false && arrTel[2].isEmpty() == false)
				bValid = true;
		}
		if (bValid == false) {
			String[]	spTel = { "", "", "" };
			arrTel = spTel;
		}
		return arrTel;
	}
	public static String makeDbText_TelNo(String sTel1, String sTel2, String sTel3) {
		String		sDbText = "";
		if (sTel1.isEmpty() == false && sTel2.isEmpty() == false && sTel3.isEmpty() == false)
			sDbText = sTel1 + "-" + sTel2 + "-" + sTel3;
		return sDbText;
	}
}

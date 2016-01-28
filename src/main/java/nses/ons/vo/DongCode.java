package nses.ons.vo;

public class DongCode {
	private	static String[]		m_saDong = {
			"101", "옥련동", 
			"102", "선학동", 
			"103", "연수동", 
			"104", "청학동", 
			"105", "동춘동", 
			"106", "송도동", 
			"620", "옥련동", 
			"630", "옥련1동", 
			"640", "옥련2동", 
			"750", "선학동", 
			"761", "연수1동", 
			"762", "연수2동", 
			"763", "연수3동", 
			"766", "청학동", 
			"780", "동춘1동", 
			"790", "동춘2동", 
			"795", "동춘3동", 
			"800", "청량동", 
			"810", "송도동", 
			"820", "송도1동", 
			"830", "송도2동" 
	};
	// "999" = "연수구외" 

	public static String getCode(String sAddr) {
		String		sRetCode = "999";

		if (sAddr == null)
			return sRetCode;
		
		for (int i = 0; i < m_saDong.length; i += 2) {
			if (sAddr.indexOf(m_saDong[i+1]) > 0) {
				sRetCode = m_saDong[i];
				break;
			}
		}
		return sRetCode;
	}
	
	/*
	public static void main(String[] args) {
		System.out.println("dongcode: " + DongCode.getCode("인천광역시 연수구 송도1동 10-50"));
	}
	*/
}

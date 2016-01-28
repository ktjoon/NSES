package nses.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableCellFormat;


public class ExcelUtil {
	static final WritableCellFormat NUMBER_FORMAT = new WritableCellFormat();
	
	/**
	 * 엑셀파일 읽기<br>
	 * 첫행은 헤더로 간주하고 두번째 줄부터 읽는다.	  
	 * @param fileName
	 * @param isTrim 필드의 공백제거여부
	 * @return
	 * @throws Exception 
	 */
	public static List<String[]> readExcel(String fileName, boolean isTrim) throws Exception {
		return readExcel(fileName, 1, isTrim, 0);
	}
	
	/**
	 * 엑셀파일 읽기<br>
	 * 첫행은 헤더로 간주하고 두번째 줄부터 읽는다.	  
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public static List<String[]> readExcel(String fileName) throws Exception {
		return readExcel(fileName, 1, false, 0);
	}

	/**
	 * 엑셀파일 읽기
	 * @param fileName
	 * @param startRow 행의 시작줄
	 * @param isTrim 필드의 공백제거여부
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String fileName, int startRow, boolean isTrim, int sheetS) throws Exception {
		List<String[]> list = new ArrayList<String[]>();

		Workbook workbook = Workbook.getWorkbook(new File(fileName));
		Sheet sheet = workbook.getSheet(sheetS);

		for (int i = startRow, m = sheet.getRows(); i < m; i++) {
			String[] row = new String[sheet.getColumns()];
			for (int j = 0, n = sheet.getColumns(); j < n; j++) {
				Cell cell = sheet.getCell(j, i);
				row[j] = isTrim ? cell.getContents().trim() : cell.getContents();
			}
			list.add(row);
		}
		
		workbook.close();
		return list;
	}
	
}

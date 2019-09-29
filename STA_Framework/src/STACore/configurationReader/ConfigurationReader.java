package STACore.configurationReader;

/**************************************************
#Project Name: wallethub
#Class Name: ConfigurationReader
#Description: This class is responsible for reading the excel config data
#Owner: Ashis Kumar Pradhan
#Author: Ashis Kumar Pradhan
#Date of creation: 26-Jul-2019
#Name of person modifying: (Tester Name): 
#Date of modification: 
‘**************************************************/

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FilenameUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ConfigurationReader {

	public static HashMap loadExcelLines(String string) {

		HashMap<String, LinkedHashMap<Integer, List>> outerMap = new LinkedHashMap<String, LinkedHashMap<Integer, List>>();
		HashMap<String, HashMap<String, Integer>> getColumnIndex = new HashMap<String, HashMap<String, Integer>>();
		LinkedHashMap<Integer, List> hashMap = new LinkedHashMap<Integer, List>();

		String sheetName = null;
		FileInputStream fis = null;
		Workbook workBook;
		try {
			if (FilenameUtils.getExtension(string).equals("xlsx")) {
				fis = new FileInputStream(string);
				workBook = new XSSFWorkbook(fis);
				HashMap<String, Integer> temp;
				for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
					XSSFSheet sheet = (XSSFSheet) workBook.getSheetAt(i);
					sheetName = workBook.getSheetName(i);
					temp = new HashMap<String, Integer>();
					Iterator<Row> rows = sheet.rowIterator();
					while (rows.hasNext()) {
						XSSFRow row = (XSSFRow) rows.next();
						Iterator cells = row.cellIterator();
						List data = new LinkedList();
						while (cells.hasNext()) {
							XSSFCell cell = (XSSFCell) cells.next();
							cell.setCellType(CellType.STRING);
							data.add(cell.getStringCellValue());
							if (row.getRowNum() == 0) {
								temp.put(cell.getStringCellValue(), ((Integer) cell.getColumnIndex()));
							}
						}
						hashMap.put(row.getRowNum(), data);
					}
					outerMap.put(sheetName, hashMap);
					hashMap = new LinkedHashMap<Integer, List>();
					getColumnIndex.put(sheetName, temp);
				}
			} else {
				fis = new FileInputStream(string);
				workBook = new HSSFWorkbook(fis);
				HashMap<String, Integer> temp;

				// Get the first sheet on the workbook.
				for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
					Sheet sheet = workBook.getSheetAt(i);
					sheetName = workBook.getSheetName(i);
					temp = new HashMap<String, Integer>();
					Iterator rows = sheet.rowIterator();
					while (rows.hasNext()) {
						HSSFRow row = (HSSFRow) rows.next();
						Iterator cells = row.cellIterator();
						List data = new LinkedList();
						while (cells.hasNext()) {
							HSSFCell cell = (HSSFCell) cells.next();
							cell.setCellType(CellType.STRING);
							data.add(cell.getStringCellValue());
							if (row.getRowNum() == 0) {
								temp.put(cell.getStringCellValue(), ((Integer) cell.getColumnIndex()));
							}
						}
						hashMap.put(row.getRowNum(), data);
					}
					outerMap.put(sheetName, hashMap);
					hashMap = new LinkedHashMap<Integer, List>();
					getColumnIndex.put(sheetName, temp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return outerMap;
	}
}
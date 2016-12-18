package membership;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CallExcel {
	ArrayList<HashMap<String, Object>> memberArray;
	Scanner input;

	String inputId;
	String inputPassword;
	String inputName;
	String inputAddress;
	String inputEmail;
	String inputBirthDay;

	public CallExcel() {
		memberArray = new ArrayList<HashMap<String, Object>>();
		input = new Scanner(System.in);
	}

	public ArrayList<HashMap<String, Object>> Call() {
		XSSFRow excelRow;
		XSSFCell excelCell;
		XSSFSheet excelSheet;
		int rowNumber;
		int cellNumber;

		try {
			FileInputStream inputStream = new FileInputStream("membershipFile.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			int sheetNumber = workbook.getNumberOfSheets();
			System.out.println("sheet 수 : " + sheetNumber);

			// sheet정보를 받음
			excelSheet = workbook.getSheetAt(0);

			// sheet에서 rows를 받음
			rowNumber = excelSheet.getPhysicalNumberOfRows();
			System.out.println("row 수 : " + rowNumber);

			// row에서 cell수를 받음
			cellNumber = excelSheet.getRow(0).getPhysicalNumberOfCells();
			String[] valueArray = new String[cellNumber];
			System.out.println("cell 수 : " + cellNumber);

			for (int rowCount = 1; rowCount < rowNumber; rowCount++) {
				MemberInformation excelMemberInformation = new MemberInformation();
				HashMap<String, Object> inputMemberHashMap = new HashMap<String, Object>();
				excelRow = excelSheet.getRow(rowCount); // row 가져오기
				if (excelRow != null) {
					for (int cellCount = 0; cellCount < cellNumber; cellCount++) {
						excelCell = excelRow.getCell(cellCount);
						if (excelCell != null) {
							String excelValue = null;
							switch (excelCell.getCellType()) {
							case XSSFCell.CELL_TYPE_FORMULA:
								excelValue = excelCell.getCellFormula();
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								excelValue = "" + excelCell.getNumericCellValue();
								break;
							case XSSFCell.CELL_TYPE_STRING:
								excelValue = "" + excelCell.getStringCellValue();
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								excelValue = "[null 아닌 공백]";
								break;
							case XSSFCell.CELL_TYPE_ERROR:
								excelValue = "" + excelCell.getErrorCellValue();
								break;
							default:
							}
							valueArray[cellCount] = excelValue;
						} else {
							System.out.print("[null]\t");
						}
					}	// for(cellCount) 문
					excelMemberInformation.setPassword(valueArray[1]);
					excelMemberInformation.setName(valueArray[2]);
					excelMemberInformation.setBirthDay(valueArray[3]);
					excelMemberInformation.setAddress(valueArray[4]);
					excelMemberInformation.setEmail(valueArray[5]);
					inputMemberHashMap.put(valueArray[0], excelMemberInformation);
					memberArray.add(inputMemberHashMap);
				}	// for(rowCount) 문
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberArray;
	}
}
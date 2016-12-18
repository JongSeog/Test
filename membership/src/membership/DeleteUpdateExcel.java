package membership;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DeleteUpdateExcel {
	ArrayList<HashMap<String, Object>> excelArray;
	XSSFRow excelRow;
	XSSFRow excelRow2;
	XSSFCell excelCell;
	XSSFSheet excelSheet;

	public DeleteUpdateExcel(ArrayList<HashMap<String, Object>> excelArray) {
		this.excelArray = excelArray;
	}

	public void update() {
		try {
			FileInputStream inputStream = new FileInputStream("membershipFile.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			excelSheet = workbook.getSheetAt(0);
			System.out.println("삭제후 넘어온 인덱스크기  : " + excelArray.size());
			for (int rowIdx = 0; rowIdx < excelArray.size(); rowIdx++) {
				Set<String> key = excelArray.get(rowIdx).keySet();
				Iterator<String> iterator = key.iterator();
				String iteratorId = iterator.next();
				MemberInformation inputExcelInformation = (MemberInformation) excelArray.get(rowIdx).get(iteratorId);

				excelRow = excelSheet.createRow(rowIdx + 1);

				excelCell = excelRow.createCell(0);
				System.out.println(iteratorId);
				excelCell.setCellValue(iteratorId);

				excelCell = excelRow.createCell(1);
				System.out.println(inputExcelInformation.getPassword());
				excelCell.setCellValue(inputExcelInformation.getPassword());

				excelCell = excelRow.createCell(2);
				System.out.println(inputExcelInformation.getName());
				excelCell.setCellValue(inputExcelInformation.getName());

				excelCell = excelRow.createCell(3);
				System.out.println(inputExcelInformation.getBirthDay());
				excelCell.setCellValue(inputExcelInformation.getBirthDay());

				excelCell = excelRow.createCell(4);
				System.out.println(inputExcelInformation.getAddress());
				excelCell.setCellValue(inputExcelInformation.getAddress());

				excelCell = excelRow.createCell(5);
				System.out.println(inputExcelInformation.getEmail());
				excelCell.setCellValue(inputExcelInformation.getEmail());
			}
			
			excelRow2 = excelSheet.getRow(excelArray.size()+1);
			excelSheet.removeRow(excelRow2);			

			File file = new File("membershipFile.xlsx");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				workbook.write(fos);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				workbook.close();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
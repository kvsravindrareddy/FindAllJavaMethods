package com.ravindra.apachepoi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * 
 * @author KVSRR
 * NN Palli
 * Donakonda
 * Prakastam district
 * Andhrapradesh, India
 *
 */
public class ApachePOIForMethods {
	public static org.apache.poi.ss.usermodel.Workbook workBook = null;
	public static Sheet sheet = null;
	public static Row row = null;
	public static Cell cell = null;
	static Font           font;   // to handle font related things
	static CellStyle      style;  // to handle styles

//	private static final String "MethodsReportKVSRR.xls" = PropertiesLoader.loadProperties().getProperty(""MethodsReportKVSRR.xls"");
	public static void createExcelSheet() throws IOException {
		workBook = new HSSFWorkbook();
		FileOutputStream fos = new FileOutputStream(new File("MethodsReportKVSRR.xls"));
		sheet = workBook.createSheet("FirstSheet");
		workBook.write(fos);
		workBook.close();
	}
	
	public static void createWorkBookSheetTitle() throws Exception {
		workBook = new HSSFWorkbook(); 
		FileOutputStream fos = new FileOutputStream(new File("MethodsReportKVSRR.xls"));
		sheet = workBook.createSheet("Testing");
		String[] titles = {"Directory", "Java Class", "Methods"};

		int i = 0;
		Cell cell;
		row = sheet.createRow(0);
		for(int j=0;j<titles.length;j++) {
			cell = row.createCell(i++);
			cell.setCellValue(titles[j]);

		}
		workBook.write(fos);
		fos.close();
	}

	public static int writeDirectoryIntoExcel(String directoryPath) throws EncryptedDocumentException, InvalidFormatException, IOException {
		InputStream fis = new FileInputStream(new File("MethodsReportKVSRR.xls"));
		workBook = WorkbookFactory.create(fis);
		sheet = workBook.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum();
		row = sheet.createRow(++lastRowNum);
		cell = row.createCell(0);
		cell.setCellValue(directoryPath);
		FileOutputStream fos = new FileOutputStream(new File("MethodsReportKVSRR.xls"));
		int currentRowNum = sheet.getLastRowNum();
		System.out.println("currentRowNum : "+currentRowNum);
		workBook.write(fos);
		fos.close();
		return currentRowNum;
	}

	public static void writeProjectNameIntoExcel(String projectName) throws EncryptedDocumentException, InvalidFormatException, IOException {
		InputStream fis = new FileInputStream(new File("MethodsReportKVSRR.xls"));
		workBook = WorkbookFactory.create(fis);
		sheet = workBook.getSheetAt(0);
		row = sheet.getRow(2);
		cell = row.createCell(1);
		cell.setCellValue(projectName);
		FileOutputStream fos = new FileOutputStream(new File("MethodsReportKVSRR.xls"));
		workBook.write(fos);
		fos.close();
	}

	public static int writeJavaFileIntoExcel(String javaFileName, String pathName) throws EncryptedDocumentException, InvalidFormatException, IOException {
		InputStream fis = new FileInputStream(new File("MethodsReportKVSRR.xls"));
		workBook = WorkbookFactory.create(fis);
		sheet = workBook.getSheetAt(0);
		//		int lastRowNum = sheet.getLastRowNum();
		//		row = sheet.createRow(++lastRowNum);
		int lastRowNum = writeDirectoryIntoExcel(pathName);
		row = sheet.getRow(lastRowNum);
		cell = row.createCell(1);
		cell.setCellValue(javaFileName);
		FileOutputStream fos = new FileOutputStream(new File("MethodsReportKVSRR.xls"));
		int currentRowNum = sheet.getLastRowNum();
//		System.out.println("currentRowNum in java Files : "+currentRowNum);
		workBook.write(fos);
		fos.close();
		return currentRowNum;
	}

	public static int writeMethodNamesToExistingWoBook(String methods, String javaFileName, String pathName) throws Exception {
		InputStream fis = new FileInputStream(new File("MethodsReportKVSRR.xls"));
		workBook = WorkbookFactory.create(fis);
		sheet = workBook.getSheetAt(0);
		//		for(int i=1;i<methods.length;i++){

		//			int lastRowNum = sheet.getLastRowNum();
		//			row = sheet.createRow(++lastRowNum);
		int lastRowNum = writeJavaFileIntoExcel(javaFileName, pathName);
//		ApachePOIForMethods.directoryMerge(lastRowNum);
		row = sheet.getRow(lastRowNum);
		cell = row.createCell(2);
		cell.setCellValue(methods);
		//		}
		FileOutputStream fos = new FileOutputStream(new File("MethodsReportKVSRR.xls"));
		int currentRowNum = sheet.getLastRowNum();
		workBook.write(fos);
		fis.close();
		fos.close();
		return currentRowNum;
	}
	
	public static void directoryMerge(int lastRow) throws EncryptedDocumentException, InvalidFormatException, IOException {
		InputStream fis = new FileInputStream(new File("MethodsReportKVSRR.xls"));
		workBook = WorkbookFactory.create(fis);
		sheet = workBook.getSheetAt(0);
		
		sheet.addMergedRegion(new CellRangeAddress(1,lastRow,0,0));
		FileOutputStream fos = new FileOutputStream(new File("MethodsReportKVSRR.xls"));
		workBook.write(fos);
		fos.close();
	}
}

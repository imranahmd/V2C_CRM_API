package com.crm.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {

	public final String Upload_Dir= new ClassPathResource("").getFile().getAbsolutePath();
	
	public FileUploadHelper ()throws IOException
	{
		
	}
	

	public boolean upload(MultipartFile multipart_file) throws IOException
	{
		boolean f = false;
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>> "+Upload_Dir);

		Files.copy(multipart_file.getInputStream(), Path.of(Upload_Dir+File.separator+multipart_file.getOriginalFilename()));
		f=true;
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return f;
		
	}
	
	public static List<List<String>> readDataFromXLS(File fileName) {
		List<List<String>> rows = new ArrayList<List<String>>();

		try {
			
			FileInputStream fis = new FileInputStream(fileName);			
			XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			Iterator<Row> rowIterator = mySheet.iterator();

			while (rowIterator.hasNext()) {

				List<String> rowValues = new ArrayList<String>();

				Row row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case STRING:
						System.out.print(cell.getStringCellValue() + " \t");
						rowValues.add(cell.getStringCellValue());
						break;
					case NUMERIC:
						System.out.print(cell.getNumericCellValue() + " \t");
						rowValues.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
						break;
					default:
					}
				}
				rows.add(rowValues);

			}
			myWorkBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rows;
	}
	
	public static List<List<String>> readDataFromXLSX(File fileName) {
		List<List<String>> rows = new ArrayList<List<String>>();

		try {

			
			FileInputStream fis = new FileInputStream(fileName);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheetAt = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheetAt.iterator();
			while (rowIterator.hasNext()) {

				List<String> rowValues = new ArrayList<String>();
				Row row = rowIterator.next();
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case STRING:
						System.out.print(cell.getStringCellValue() + "\t");
						rowValues.add(cell.getStringCellValue());
						break;
					case NUMERIC:
						System.out.print(cell.getNumericCellValue() + "\t");
						rowValues.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
						break;
					default:
					}
				}
				rows.add(rowValues);

			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rows;
	}
	
}

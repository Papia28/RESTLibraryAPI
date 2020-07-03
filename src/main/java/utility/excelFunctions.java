package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelFunctions {

	private static File excelFile = null;
	private static FileInputStream excelInputStream = null;
	private static FileOutputStream excelOutputStream = null;
	private static XSSFWorkbook workbook = null;
	private static XSSFWorkbook oWorkbook = null;
	private static XSSFSheet sheet = null;
	private static int totalRows = 0;
	private static int totalCols = 0;
	private static Object[][] dataValues = null;
	private static int colID = 0, colResult = 0;
	private static XSSFRow testRow = null;
	private static XSSFCell testCellBookID = null;
	private static XSSFCell testCellResult = null;
	private static String path = null, sheetName = null;
	
	//method to set path of input excel and configure the XSSF class objects
	public static void setInputExcel(String path, String sheetName) throws Throwable {

		try {
			excelFile = new File(path);
			excelInputStream = new FileInputStream(excelFile);			
			workbook = new XSSFWorkbook(excelInputStream);
			sheet = workbook.getSheet(sheetName);			
			totalRows = sheet.getLastRowNum();		//returns zero based number of rows	
			XSSFRow row = sheet.getRow(totalRows);				
			//totalCols = row.getLastCellNum();    //returns the index of the last cell contained in this row PLUS ONE				
			totalCols = row.getPhysicalNumberOfCells();   //returns the number of defined cells i.e. cells having values			
			dataValues = new Object[totalRows][totalCols];   //initialize the multidimensional object array
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in setting excel file path.");
			throw e;
		}
	}
	
	//method to read data from the excel
	public static Object[][] readExcelData(String path1, String sheetName1) throws Throwable {
		try {

			XSSFRow row = null;
			XSSFCell cell = null;
			path = path1;
			sheetName = sheetName1;
			setInputExcel(path, sheetName);

			for (int i = 0; i < totalRows; i++) 
			{
				row = sheet.getRow(i+1);
				
				for (int j = 0; j < totalCols; j++) 
				{
					cell = row.getCell(j);
					CellType type = cell.getCellType();

					switch (type) 
					{
						case STRING:
							dataValues[i][j] = cell.getStringCellValue();
							break;
						
						case NUMERIC:
							dataValues[i][j] = (int) (cell.getNumericCellValue());
							break;
							
						case BOOLEAN:
							dataValues[i][j] = cell.getBooleanCellValue();
							break;
							
						default:
							dataValues[i][j] = null;
							break;
					}
					//System.out.println(dataValues[i][j]);
				}
			}
			return dataValues;
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in reading values from excel.");
			throw e;
		}
		finally {
			excelInputStream.close();
		}
	}
	
	
	//method to set the output excel path
	public static void setOutputExcel() throws Throwable{
		try {
			excelOutputStream  = new FileOutputStream(excelFile);
			oWorkbook = new XSSFWorkbook();
			
			//to find the column numbers of Book ID and Result columns
			XSSFRow firstRow = sheet.getRow(0);
			int col = firstRow.getPhysicalNumberOfCells();
			for (int i = 0; i < col; i++) 
			{
				XSSFCell cell = firstRow.getCell(i);
				String header = cell.getStringCellValue();

				if (header.equalsIgnoreCase("Book ID"))
					colID = i;
				if (header.equalsIgnoreCase("Result"))
					colResult = i;
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error in output stream.");
			throw e;
		}		
	}
	
	//method to set data to excel
	public static void setExcelData(int tcNum, String bookID, String result) throws Throwable 
	{
		try {	
			setOutputExcel();
			testRow = sheet.getRow(tcNum);	
			
			//set bookID value in excel
			testCellBookID = testRow.getCell(colID);  			
			if(testCellBookID == null)
				testCellBookID = testRow.createCell(colID);
			else
				testCellBookID.setBlank();			
			testCellBookID.setCellValue(bookID);
			oWorkbook.write(excelOutputStream);			
			
			//set execution result in excel
			testCellResult = testRow.getCell(colResult);			
			if(testCellResult == null)
				testCellResult = testRow.createCell(colResult);
			else
				testCellResult.setBlank();				
			testCellResult.setCellValue(result);			
			oWorkbook.write(excelOutputStream);
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in setting values in cells.");
			throw e;
		} 
		finally {
			oWorkbook.close();
			excelOutputStream.flush();
			excelOutputStream.close();			
		}
	}

}

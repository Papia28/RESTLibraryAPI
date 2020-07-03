package simulateDynamicLibrary;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import utility.excelFunctions;
import utility.jsonPayload;
import utility.readJsonResults;

public class simulateDynamicAddBook {

	private static String resource = "/Library/Addbook.php";
	private static String response = null;
	private static String pathExcel = "src\\resources\\excelData\\addBookData.xlsx";
	private static String sheetName = "Sheet1";

	@Test(dataProvider = "provideDynamicJSON", groups = "addBook")
	public void addBook(int tcNum, String name, String isbn, int aisle, String author, boolean flag) throws Throwable 
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String bookID = null;
		try {
			if (flag == true) 
			{
				response = given().log().all().header("Content-Type", "application/json")
						.body(jsonPayload.jsonToString(name, isbn, aisle, author)).when().post(resource).then().log()
						.all().assertThat().statusCode(200).header("server", equalTo("Apache")).extract().response()
						.asString();
				
				// to evaluate the response and provide proper result in excel
				bookID = readJsonResults.getBookID(response);
				excelFunctions.setExcelData(tcNum, bookID, "Pass");
			} 
			else if (flag == false) {
				excelFunctions.setExcelData(tcNum, bookID, "Skip");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in @Test method.");
			excelFunctions.setExcelData(tcNum, bookID, "Fail");
			throw e;
		}
	}

	@DataProvider(name = "provideDynamicJSON")
	public Object[][] dynamicData() throws Throwable {

		try {
			Object[][] dataValues = excelFunctions.readExcelData(pathExcel, sheetName);
			return dataValues;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in dataprovider");
			throw e;
		}
	}

}

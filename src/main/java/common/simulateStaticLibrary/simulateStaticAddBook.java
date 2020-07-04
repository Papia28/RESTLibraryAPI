package common.simulateStaticLibrary;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import main.java.utility.jsonPayload;
import main.java.utility.readJsonResults;


public class simulateStaticAddBook {

	private static String resource = "/Library/Addbook.php";
	private static String response = null, bookID = null;

	@Test
	public static String addBook() throws Throwable {

		try {
			response = given().log().all().header("Content-Type", "application/json")
					.body(jsonPayload.jsonToString(".\\resources\\jsonData\\addBook.json")).when().post(resource).then().log()
					.all().assertThat().statusCode(200).header("server", equalTo("Apache")).extract().response()
					.asString();

			bookID = readJsonResults.getBookID(response);
			System.out.println("Generated Book ID: " + bookID);
			return bookID;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in simulateStaticAddBook : addBook()");
			return null;
		}

	}
}

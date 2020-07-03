package main.java.common;
import org.testng.annotations.Test;

import io.restassured.*;

public class Book {
	
	@Test
	public static void simulateBook() {
	
	RestAssured.baseURI = "http://216.10.245.166";
	
	}
}

package main.java.utility;

import io.restassured.path.json.JsonPath;

public class readJsonResults {
	
	private static String bookID = null;
	private static String server = null;
	private static JsonPath js = null;
	
	public static String getBookID(String response) throws Throwable{		
		try {
		js = new JsonPath(response);
		bookID = js.getString("ID");
		return bookID;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error in getBookID()");
			throw e;
		}
	}
	
	public static String getServer(String response) throws Throwable{
		try {
		js = new JsonPath(response);
		server = js.getString("Server");
		return server;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error in getBookID()");
			throw e;
		}
	}
}

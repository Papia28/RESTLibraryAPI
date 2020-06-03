package utility;

import io.restassured.path.json.JsonPath;

public class readJsonResults {
	
	private static String bookID = null;
	private static String server = null;
	private static JsonPath js = null;
	
	public static String getBookID(String response) {		
		
		js = new JsonPath(response);
		bookID = js.getString("ID");
		return bookID;
	}
	
	public static String getServer(String response) {
		
		js = new JsonPath(response);
		server = js.getString("Server");
		return server;
	}
}

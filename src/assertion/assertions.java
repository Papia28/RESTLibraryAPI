package assertion;

import org.testng.Assert;

public class assertions {
	
	public static void assertEquality(String actualValue, String expectedValue) throws Throwable
	{
			Assert.assertEquals(actualValue, expectedValue, "Assertion failure!");
			System.out.println("Assertion success!");		
	}
}

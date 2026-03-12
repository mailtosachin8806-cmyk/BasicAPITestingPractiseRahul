package GoogleAPI;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class dynamicJson {

	JsonPath js;
	
	@Test(description="Dynamically build json payload with external data inputs")
	public void addBook() {
		
	RestAssured.baseURI = "https://rahulshettyacademy.com";
		
	String Response	= given().log().all().header("Content-Type", "application/json").body(payload.AddBook("asdfe","8524"))
		.when().post("/Library/Addbook.php").then().assertThat().log().all()
		.statusCode(200).extract().response().asString();
		
	js	= new JsonPath(Response); 
	String ID = js.get("ID");
	System.out.println(ID);
	
	// Delete book API
	
	String DeleteAPIResponse  =  given().log().all().header("Content-Type", "application/json")
			.body(payload.deleteBook(ID))
	.when().delete("/Library/DeleteBook.php").then().assertThat()
	.log().all().statusCode(200).extract().response().asString();
	
	System.out.println(DeleteAPIResponse);
	
	js = new JsonPath(DeleteAPIResponse);
	String Actualmsg =js.getString("msg");
	System.out.println(Actualmsg);
	}
	
	
	@Test(description="Parameterize the API tests with multiple data sets",dataProvider="BookData")
	public void addMultipleBook(String isbn , String aisle) 
	{
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";	
		String MultipleBookResponse	= given().log().all().header("Content-Type", "application/json")
		.body(payload.AddBook(isbn,aisle))
		.when().post("/Library/Addbook.php").then().assertThat().log().all()
		.statusCode(200).extract().response().asString();
			
		js = new JsonPath(MultipleBookResponse); 
		String ID = js.get("ID");
		System.out.println(ID);
		
		// Delete book API
		
		String DeleteAPIResponse  =  given().log().all().header("Content-Type", "application/json")
		.body(payload.deleteBook(ID))
		.when().delete("/Library/DeleteBook.php").then().assertThat()
		.log().all().statusCode(200).extract().response().asString();
		
		System.out.println(DeleteAPIResponse);
		
		js = new JsonPath(DeleteAPIResponse);
		String Actualmsg =js.getString("msg");
		System.out.println(Actualmsg);
			
	}
	
	
	@DataProvider(name="BookData")
	public Object[][] getData() {
		
		return new Object[][] {{"asdse","7410"},{"wsaq","8520"},{"tgbv","9630"}};
	}
}

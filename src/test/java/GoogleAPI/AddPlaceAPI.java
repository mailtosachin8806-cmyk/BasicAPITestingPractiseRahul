package GoogleAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AddPlaceAPI {

	@Test
	public void validateAddPlaceAPI() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response); //for parsing json
		String placeId = js.getString("place_id");
		System.out.println("The place ID : " + placeId);
		
		//Update place
		
		String newAddress = " Summaer Walk Africa, Pune";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"}").when().put("/maps/api/place/update/json").then().assertThat()
		.statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Check address will return
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(getPlaceResponse);
		String updatedAddress = js1.getString("address");
		System.out.println("The updated address is : " + updatedAddress);
		Assert.assertEquals(newAddress, updatedAddress);
		
		
	}
		
}

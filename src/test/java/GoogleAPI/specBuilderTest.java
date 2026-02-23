package GoogleAPI;

import org.testng.annotations.Test;

import Pojo.Location;
import Pojo.addPlace;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class specBuilderTest {

	/*
	 * Spec Builder test
	 */

	@Test(description = "To handle serialization")
	public void handleSerialization() {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		addPlace p = new addPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setLanguage("French-IN");

		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		p.setTypes(mylist);

		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);

		p.setLocation(loc);
		
		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		RequestSpecification req = given().log().all().spec(reqSpec).body(p);
		
		String response	= req.when().post("/maps/api/place/add/json")
				.then().log().all().spec(resSpec)
				.extract().response().asString();

		// System.out.println(res);

	}

}

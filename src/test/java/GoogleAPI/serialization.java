package GoogleAPI;

import org.testng.annotations.Test;

import Pojo.Location;
import Pojo.addPlace;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class serialization {

	/*
	 * Handle Serialization
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

		String res = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(p).when().post("/maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		// System.out.println(res);

	}

}

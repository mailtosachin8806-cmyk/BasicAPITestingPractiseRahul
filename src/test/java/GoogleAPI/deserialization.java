package GoogleAPI;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pojo.Api;
import Pojo.WebAutomation;
import Pojo.getCourse;
import io.restassured.path.json.JsonPath;

public class deserialization {

	/*
	 * Implementation of Deserialization
	 */
	@Test
	public void handleDeserialization() {

		String[] courseTitle = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		String Response = given().log().all()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println(Response);

		JsonPath js = new JsonPath(Response);
		String accessToken = js.getString("access_token");

		getCourse gc = given().log().all().queryParam("access_token", accessToken).when()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(getCourse.class);

		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		List<Api> apiCourses = gc.getCourses().getApi();

		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
				System.out.println(apiCourses.get(i).getCourseTitle());
			}
		}

		// Get the course name of webAutomation

		List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();

		for (int j = 0; j < webAutomationCourses.size(); j++) {
			System.out.println(webAutomationCourses.get(j).getCourseTitle());
		}

		// Compare actual course title with expected course title
		ArrayList<String> Aladd = new ArrayList<String>();

		for (int k = 0; k < webAutomationCourses.size(); k++) {
			Aladd.add(webAutomationCourses.get(k).getCourseTitle());
		}

		List<String> expectedList = Arrays.asList(courseTitle);
		Assert.assertTrue(Aladd.equals(expectedList));
		System.out.println("Successfully Compare actual course title with expected course title");

	}

}

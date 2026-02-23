package OAuth2;

import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class Oauth {
	
	@Test
	public void handleOauth() {
		
		String Response = given().log().all()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.asString();
	
		System.out.println(Response);
	
		JsonPath js= new JsonPath(Response);
		String accessToken = js.getString("access_token");
		
		String finalResponse = given().log().all().queryParam("access_token", accessToken)
		.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
	
		System.out.println(finalResponse);
		
	}

}

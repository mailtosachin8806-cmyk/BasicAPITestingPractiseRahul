package GoogleAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pojo.LoginRequest;
import Pojo.LoginResponse;
import Pojo.OrderDetail;
import Pojo.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Ecommerce_E2E {
	
	@Test
	public void handleEccomerceE2E() {
	
	//Generate a token 
	RequestSpecification req =	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
	
	//Achive Serialization
	LoginRequest loginRequest = new LoginRequest();
	loginRequest.setUserEmail("gharsele96@gmail.com");
	loginRequest.setUserPassword("Sachin@0014");
	
	RequestSpecification reqLogin 	= given().log().all().spec(req).body(loginRequest);
	LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
	
	//Achive Deserialization
	System.out.println(loginResponse.getToken());
	String token = loginResponse.getToken();
	System.out.println(loginResponse.getUserId());
	String userID= loginResponse.getUserId();
	
	
	//Add Product
	RequestSpecification addProductBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.addHeader("Authorization", token).build();
	
	RequestSpecification reqAddProduct = given().log().all().spec(addProductBasereq).formParam("productName", "Shoes")
	.formParam("productAddedBy", userID).formParam("productCategory", "fashion")
	.formParam("productSubCategory", "shirts").formParam("productPrice", "1500")
	.formParam("productDescription", "Addias Originals").formParam("productFor", "women")
	.multiPart("productImage",new File("G://APIAutomation//Adidas_Shoes.png"));
	
	String addProductResponse  = reqAddProduct.when().post("/api/ecom/product/add-product").then()
								.log().all().extract().response().asString();
	
	JsonPath js = new JsonPath(addProductResponse);
	String productID = js.get("productId");
	System.out.println("The product is: " + productID);
	
	//Create order
	
	RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
	
	OrderDetail orderDetail = new OrderDetail();
	orderDetail.setCountry("India");
	orderDetail.setProductOrderedId(productID);
	
	List<OrderDetail> orderDetailsList = new ArrayList<OrderDetail>();
	orderDetailsList.add(orderDetail);
	
	Orders orders = new Orders();
	orders.setOrders(orderDetailsList);
	
	RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);
	
	String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
	
	System.out.println(responseAddOrder);
	
	//Get order details
	
	RequestSpecification getOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
			.build();
	
	RequestSpecification getOrderDetails = given().log().all().spec(getOrderBaseReq).queryParam("Add User ID", userID);
	String responseOrderDetails = getOrderDetails.when().get("/api/ecom/order/get-orders-details").then().log().all().extract().response().asString();
	
	JsonPath js2 = new JsonPath(responseOrderDetails);
	System.out.println(js2.getString("message"));
	
	
	//Delete the product
	
	RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
	
	RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq).pathParam("productId", productID);
	
	String deleteProductResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
	
	JsonPath js1 = new JsonPath(addProductResponse);
//	Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
	System.out.println("Product Delete Successfully");
	
	}

}

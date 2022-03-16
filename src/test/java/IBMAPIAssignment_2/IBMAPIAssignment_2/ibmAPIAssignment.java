package IBMAPIAssignment_2.IBMAPIAssignment_2;


import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.testng.ITestContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ibmAPIAssignment {

	
	@Test
public void createUser(ITestContext userName,ITestContext pwd) throws JsonProcessingException {
		
		userCreationPojo user= new userCreationPojo();
		user.setUsername("Santosh");
		user.setFirstname("Keshetti");
		user.setLastname("Kumar");
		user.setPassword("Admin123");
		user.setEmail("santosh@test.com");
		user.setUserstatus("0");
		
		String username=user.getUsername();
		String password=user.getPassword();
		
		ObjectMapper objectMapper=new ObjectMapper();
		String response=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
	
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RestAssured.given().contentType(ContentType.JSON).body(response)
		.when().post("/user").then().statusCode(200).log().all();
		
		userName.setAttribute("USERNAME", username);
		
		pwd.setAttribute("PASSWORD", password);		
	}
	
	
	@Test(dependsOnMethods="createUser")
	public void updateUser(ITestContext userName) throws JsonProcessingException 
	{
		String Username=userName.getAttribute("USERNAME").toString();
		
		userCreationPojo user= new userCreationPojo();
		
		user.setUsername("Santosh");
		user.setFirstname("Keshetti");
		user.setLastname("Kumar");
		user.setPassword("Admin123");
		user.setEmail("santosh@test.com");
		user.setUserstatus("0");
		
		ObjectMapper objectMapper=new ObjectMapper();
		String response=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
		
		System.out.println(response);
		
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RestAssured.given().contentType(ContentType.JSON)
		.body(response).when().put("/user/"+Username).then().statusCode(200).log().all();

	}

	@Test(dependsOnMethods="createUser")
	public void login(ITestContext userName,ITestContext pwd) throws JsonProcessingException 
	{
		String username=userName.getAttribute("USERNAME").toString();
		String password=pwd.getAttribute("PASSWORD").toString();
		
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RestAssured.given().get("/user/login?username="+username+"&password="+password).then().statusCode(200).log().all();

	}
	@Test(dependsOnMethods="login")
	public void logout(ITestContext userName) throws JsonProcessingException 
	{
		String username=userName.getAttribute("USERNAME").toString();
		
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RestAssured.given().get("/user/logout?username="+username).then().statusCode(200).log().all();

	}
	
	@Test(dependsOnMethods="login")
	public void deleteUser(ITestContext userName) throws JsonProcessingException 
	{
		String username=userName.getAttribute("USERNAME").toString();
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RestAssured.given().delete("/user/" +username).then().statusCode(200).log().all();

	}

	
}

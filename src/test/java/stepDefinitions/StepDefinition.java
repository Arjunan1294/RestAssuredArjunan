package stepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static org.junit.Assert.*;

import static io.restassured.RestAssured.*;

public class StepDefinition extends Utils{
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id = null;
	JsonPath js;



	@Given("Add Place PayLoad {string} {string} {string}")
	public void add_place_pay_load(String name, String language, String address) throws IOException {
		res=given().spec(requestSpecification())
				.body(data.addPlacePayload(name, language, address));

	}

	@When("^User Calls \"([^\"]*)\" API with \"([^\"]*)\" http request$")
	public void user_calls_something_api_with_post_http_request(String resource, String method) throws Throwable {

		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST")) 
			response =res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response =res.when().get(resourceAPI.getResource());


	}

	@Then("^The API call got success with status code 200$")
	public void the_api_call_got_success_with_status_code_200() throws Throwable {
		assertEquals(response.getStatusCode(),200);
	}

	@And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
	public void something_in_response_body_is_something(String keyValue, String ExpectedValue) throws Throwable {

		assertEquals(getJsonPath(response, keyValue),ExpectedValue);
	}

	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws Throwable {
		 place_id = getJsonPath(response, "place_id");
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_something_api_with_post_http_request(resource,"GET");
		String Actualname = getJsonPath(response, "name");
		assertEquals(Actualname, expectedName);
	}

	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
		res = given().spec(requestSpecification()).body(data.deletePlacePayLoad(place_id));
	}



}
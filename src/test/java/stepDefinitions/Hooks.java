package stepDefinitions;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws Throwable
	{		//execute this code only when place id is null
		//write a code that will give you place id
		
		StepDefinition m =new StepDefinition();
		if(StepDefinition.place_id==null)
		{
		m.add_place_pay_load("Shetty", "French", "Asia");
		m.user_calls_something_api_with_post_http_request("AddPlaceAPI", "POST");
		m.verify_place_id_created_maps_to_using("Shetty", "getPlaceAPI");
		}
		
		

	}
}

Feature: Validating Place APIs

@AddPlace @Regression
Scenario Outline: Verify if place is added
Given Add Place PayLoad "<name>" "<language>" "<address>"
When User Calls "AddPlaceAPI" API with "post" http request
Then The API call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify place_Id created maps to "<name>" using "getPlaceAPI"

Examples:
|name  |language|address     |
|Ahouse|English |Cross Center|
#|Bhouse|Tamil		|Kurukku sand|

@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working

	Given DeletePlace Payload
	When User Calls "deletePlaceAPI" API with "POST" http request
	Then The API call got success with status code 200
	And "status" in response body is "OK"


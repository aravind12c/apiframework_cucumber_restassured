Feature: Pet Store API Testing
Description: To give a POC on the API Testing

@api @tc01
Scenario: As an user, I want to add new Pet to the Store using API
	Given I have a Endpoint to add new pet to store
	When I hit the endpoint using "POST" Method to add new pet
	Then I Validate the success response
	And I verify the pet is available by using "GET" Method by PETID

@api @tc02
Scenario: As an user, I want to upload the image of a pet using API
	Given I have a Endpoint to upload the image of a pet
	When I hit the endpoint using "POST" Method to upload the image
	Then I Validate the success response
	And I verify the response message whether Image got uploaded
	
@api @negative @tc03
Scenario: As an user, I receive error message if I try to get the PetID with incorrect details
	Given I have a Endpoint to get the Pet details
	When I hit the endpoint using "GET" Method to get pet details
	Then I recieve pet not found message

@api @tc04
Scenario: As an user, I want to update the existing pet details
	Given I have a Endpoint to update the pet details
	When I hit the endpoint using "PUT" Method to update the pet details
	Then I Validate the success response
	And I verify the updated pet details by using "GET" Method by PETID
	
@api @tc05
Scenario: As an user, I want to delete the existing pet
	Given I have a Endpoint to delete the pet details
	When I hit the endpoint using "Delete" Method to delete the pet details
	Then I Validate the success response
	And I verify the pet details are deleted using "GET" method
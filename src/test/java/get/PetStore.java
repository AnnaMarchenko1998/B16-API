package get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetStorePojo;

public class PetStore {
    @Test
    public void getPetTest(){

        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet/10567";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().response();

       PetStorePojo parsedResponse = response.as(PetStorePojo.class);
        String actualPetName = parsedResponse.getName();
        String expectedPetName = "hutch";
        Assert.assertEquals("Failed to validate pet name",expectedPetName,actualPetName);



    }
}

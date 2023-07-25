package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetStorePojo;
import utils.PayloadUtils;

import java.io.File;

public class PetStore {

    @Test
    public void createPetTest(){
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPetPayload(300498,"Lola")).when().post()
                .then().statusCode(200)
                .extract().response();

       PetStorePojo parsedResponse = response.as(PetStorePojo.class);

        Assert.assertEquals(parsedResponse.getName(),"Lola");
        Assert.assertEquals(parsedResponse.getStatus(), "sdet doggie");


    }

   @Test
   public void createPetWithJasonFileTest(){
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

       File jsonFile = new File("src/test/resources/pet.json");

        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonFile)
                .when().post()
                .then().log().body().statusCode(200);

        PetStorePojo pet = new PetStorePojo();
        pet.setId(78923);
        pet.setName("rex");
        pet.setStatus("barking");

       Response response = RestAssured.given().accept(ContentType.JSON)
               .contentType(ContentType.JSON)
               .body(pet)
               .when().post()
               .then().log().body().statusCode(200)
               .extract().response();

       //Deserialization -> JonPath
       JsonPath parsedResponse = response.jsonPath();
       String actualName = parsedResponse.getString("name");
       Assert.assertEquals("rex", actualName);

       parsedResponse.getString("category.name");
   }
}

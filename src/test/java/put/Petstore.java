package put;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import utils.PayloadUtils;

public class Petstore {

    @Test
    public void updatePetTest(){
        /*
        1.Create a pet
        2. List a pet
        3. Update a pet
        4. List a pet
         */

        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        int originalPetId = 113355;
        String originalPetName = "Alfa";

        //1.Create a pet
        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPetPayload(originalPetId, originalPetName))
                .when().post()
                .then().statusCode(200)
                .body("id", Matchers.equalTo(originalPetId))
                .body("name", Matchers.equalTo(originalPetName));


        //2.Get a pet
        RestAssured.given().accept(ContentType.JSON)

                .when().get(originalPetId + "")
                .then().statusCode(200)
                .body("id",Matchers.is(originalPetId))
                .body("name", Matchers.equalTo(originalPetName));

        //3.Update a pet

        String updatedPetName = "Pluto";
        RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPetPayload(originalPetId, updatedPetName))
                .when().put()
                .then().statusCode(200)
                .body("id", Matchers.is(originalPetId))
                .body("name", Matchers.is(updatedPetName));

        //4. Get a pet

        RestAssured.given().accept(ContentType.JSON)
                .when().get("" + originalPetId)
                .then().statusCode(200)
                .body("id", Matchers.equalTo(originalPetId))
                .body("name", Matchers.equalTo(updatedPetName));


        //Anna API checking Git commands. My offer is waiting for me!!!
        




    }
}

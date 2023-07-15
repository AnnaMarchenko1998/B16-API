package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CatFacts {

    @Test
    public void factsValidation(){
        RestAssured.baseURI ="https://catfact.ninja";
        RestAssured.basePath="facts";
        Response response = RestAssured.given().accept("application/json")
                .queryParam("limit",100)
                .when().get()
                .then().statusCode(200)
                .extract().response();

        Map<String,Object> parseResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

       List<Map<String,Object>> data = (List<Map<String, Object>>) parseResponse.get("data");

       Integer expectedFactNumber = 100;
       Integer actualFactNumber = data.size();
       Assert.assertEquals(expectedFactNumber,actualFactNumber);

    }


}

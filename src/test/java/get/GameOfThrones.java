package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.ContinentPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GameOfThrones {

    @Test
    public void getCharactersTest(){
        RestAssured.baseURI="https://thronesapi.com/api/v2/Characters";
        given().header("Accept","application/json")
                .when().get()
                .then().statusCode(200).log().body();
    }

    @Test
    public void getSpecificCharacter(){
        RestAssured.baseURI="https://thronesapi.com/api/v2/Characters/10";


       Response response = RestAssured.given().header("Accept","application/json")
                .when().get()
                .then().statusCode(200).log().body()
                .extract().response();

       Map <String, Object> deserializeResponse = response.as(new TypeRef<Map<String, Object>>() {
       });

        System.out.println(deserializeResponse);
        String firstName = (String) deserializeResponse.get("firstName"); //--> converting Object to String
        String lastName = String.valueOf(deserializeResponse.get("lastName")); //--> converting Object to String
        Assert.assertEquals("Cateyln", firstName);
        Assert.assertEquals("Stark", lastName);
    }

    @Test
    public void continentsTest(){
        RestAssured.baseURI="https://thronesapi.com/api/v2/continents";
        //RestAssured.basePath = "api/v2/continents";
      Response response =  RestAssured.given().accept("application/json")
                .when().get()
                .then().statusCode(200).log().body()
                .extract().response();

      List<Map<String,Object>> parsedResponse = response.as(new TypeRef<List<Map<String, Object>>>() {
      });
      Integer actualContinentCount = parsedResponse.size();
      Integer expectedContinenrCount = 4;
      Assert.assertEquals(expectedContinenrCount, actualContinentCount);

      List<String> allContinents= new ArrayList<>();

      for (int i = 0; i< parsedResponse.size(); i++) {
         allContinents.add((String)(parsedResponse.get(i)).get("name"));
      }

        System.out.println(allContinents);
    }

    @Test
    public void continentTest(){
        RestAssured.baseURI = "https://thronesapi.com";
        RestAssured.basePath = "api/v2/continents/0";

        Response response = RestAssured.given().accept("application/json")
                .when().get()
                .then().statusCode(200)
                .log().body()
                .extract().response();

       ContinentPojo parsedResponse =  response.as(ContinentPojo.class);
       int id = parsedResponse.getId();
       String name = parsedResponse.getName();
       Assert.assertEquals(0,id);
       Assert.assertEquals("Westeros", name);
    }

    @Test
    public void continentTest2(){
        RestAssured.baseURI = "https://thronesapi.com";
        RestAssured.basePath ="api/v2/continents";
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().response();

        //pojo deserialization
        List <ContinentPojo> continentPojoList = new ArrayList<>();
        ContinentPojo[] parsedResponse = response.as(ContinentPojo[].class);

        Map<String, Integer> continentIdMap = new HashMap<>();

        for (int i = 0; i < parsedResponse.length; i++){
            ContinentPojo continentPojo = parsedResponse[i];
            String name = continentPojo.getName();
            int id = continentPojo.getId();
            continentIdMap.put(name,id);
        }


    }
}

package get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.StarWarsCharacterPojo;
import pojo.StarWarsPlanetsPojo;

public class StarWars {

    @Test
    public void getCharacterTest(){

        Response response = RestAssured.given().accept("application/json")
                .when().get("https://swapi.dev/api/people/1")
                .then().statusCode(200)
                .extract().response();

        StarWarsCharacterPojo parseResponse = response.as(StarWarsCharacterPojo.class);
        String expectedName = "Luke Skywalker";
        String actualName = parseResponse.getName();
        Assert.assertEquals(expectedName,actualName);
    }

    @Test
    public void getPlanetTest(){
        RestAssured.baseURI = "https://swapi.dev/";
        RestAssured.basePath = "api/planets/1";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get().then().statusCode(200).extract().response();

        StarWarsPlanetsPojo deserializedResponse = response.as(StarWarsPlanetsPojo.class);

        Assert.assertEquals(deserializedResponse.getName(),"Tatooine");
        Assert.assertEquals(deserializedResponse.getPopulation(),"200000" );
        Assert.assertEquals(deserializedResponse.getTerrain(), "desert");
    }
}

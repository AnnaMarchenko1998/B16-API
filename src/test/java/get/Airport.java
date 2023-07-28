package get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class Airport {

    @Test
    public void getListOfAirportsTest(){

        RestAssured.baseURI = "https://airportgap.dev-tester.com";
        RestAssured.basePath = "api/airports";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("Authorization","Bearer JrUQ9qQ9hbPM13r57KciPWxG")
                .when().get()
                .then().statusCode(200)
                .extract().response();

        //JsonPath desirialization

        JsonPath parsedResponse = response.jsonPath();
        String nextPageUrl = parsedResponse.getString("links.next");
        System.out.println("Next page url is -> " + nextPageUrl);

        //retrieve id og first airport
        String firstAirportId = parsedResponse.getString("data[0].id");
        Assert.assertEquals("GKA", firstAirportId);

        //retrieve city from the first airport
        String firstCity = parsedResponse.getString("data[0].attributes.city");
        Assert.assertEquals("Goroka", firstCity);

    }
}

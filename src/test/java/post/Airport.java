package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.AirportPojo;
import utils.PayloadUtils;

public class Airport {

    @Test
    public void distanceTest(){

        RestAssured.baseURI = "https://airportgap.dev-tester.com";
        RestAssured.basePath = "api/airports/distance";

        Response response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer xoxb-4764264203175-5572283351303-ChTt1SkoH3zJPsSqvNZ3K8uq")
                .body(PayloadUtils.getAirportPayload("ORD", "MIA"))
                .when().post()
                .then().statusCode(200)
                .extract().response();

        AirportPojo parsedResponse = response.as(AirportPojo.class);
        double actualMiles = parsedResponse.getData().getAttributes().getMiles();
        Assert.assertEquals(1198.4365657701198, actualMiles, 0.0);

        double actualKilometers = parsedResponse.getData().getAttributes().getKilometers();
        Assert.assertEquals(1930.0402832460652, actualKilometers, 0.0);
    }


}

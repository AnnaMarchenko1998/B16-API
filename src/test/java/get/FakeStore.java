package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class FakeStore {

    @Test
    public void productPriceTest(){

        RestAssured.baseURI = "https://fakestoreapi.com/products/1";

        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get()
                .then().statusCode(200).log().body()
                .extract().response();

        Map<String, Object> deserializeData = response.as(new TypeRef<Map<String, Object>>() {
        });

        Double price = (double) deserializeData.get("price");
        Assert.assertTrue(109.95 == price);
        Assert.assertEquals(109.95, price,0);
        Assert.assertEquals(109.95, deserializeData.get("price"));


        Map<String,Object> ratingMap = (Map<String, Object>) deserializeData.get("rating");
        Double rate = (Double) ratingMap.get("rate");
        Integer count = (Integer) ratingMap.get("count");

        Assert.assertTrue(rate == 3.9);
        Assert.assertTrue(count == 120);
    }

    @Test
    public void sumOfProductsTest(){
        RestAssured.baseURI = "https://fakestoreapi.com/products";
        Response response = RestAssured.given().accept("application/json")
                .when().get()
                .then().statusCode(200)
                .extract().response();
        List<Map<String,Object>> parseResponse = response.as(new TypeRef<List<Map<String, Object>>>() {
        });
        double sumOfPrice = 0.0;

        for (int i=0; i< parseResponse.size(); i++){

           sumOfPrice += Double.parseDouble(parseResponse.get(i).get("price").toString());
        }
        System.out.println(sumOfPrice);

        Assert.assertTrue(sumOfPrice > 200);
    }
}

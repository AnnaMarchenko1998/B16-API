package get;

import io.restassured.RestAssured;
import org.junit.Test;

public class Currency {


/*
1. Defined URL/endpoint: https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json
2. Add query string parameters if needed
3. Add headers if needed
4. Define HTTP method (GET)
5. Send request
 */

@Test
    public void  usdCurrentTest(){
    //1. Defined URL/endpoint: https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json
    RestAssured.baseURI = "https://fakestoreapi.com/products/1";
    RestAssured.given().header("Accept","application/json")
            .when().get()
            .then().statusCode(200).log().body();



}






}

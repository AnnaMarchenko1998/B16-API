package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SocketHandler;

public class HomeWorkTima {

     /*
    Homework:
1.Continents store name and id in a Map (https://thronesapi.com/api/v2/continents)
2. Find cat facts with less than 50 characters and more than 200 characters and store those
 in a separate List, facts with 50 and less List and facts with 200 and more list
3. Find non cat related facts and store them in a List (https://catfact.ninja/facts?limit=100)
     */

    @Test
    public void continentsNameAndId() {
        RestAssured.baseURI = "https://thronesapi.com/";
        RestAssured.basePath = "api/v2/continents";
        Response response = RestAssured.given().accept("application/json")
                .when().get()
                .then().statusCode(200)
                .extract().response();

        List<Map<String, Object>> parsedResponse = response.as(new TypeRef<List<Map<String, Object>>>() {
        });
        Map<String, Integer> nameAndId = new HashMap<>();

        for (int i = 0; i < parsedResponse.size(); i++) {
            nameAndId.put((String) parsedResponse.get(i).get("name"), (Integer) parsedResponse.get(i).get("id"));
        }

        System.out.println(nameAndId);
    }

    /*
    Homework:
    2. Find cat facts:
        -> with less than 50 characters and more than 200 characters
        -> facts with 50 and less List
        -> facts with 200 and more list
    NOTE: store those in a separate Lists

    3. Find non cat related facts and store them in a List (https://catfact.ninja/facts?limit=100)
     */

    @Test
    public void catFactsTest() {
        RestAssured.baseURI = "https://catfact.ninja/facts?limit=332";
        //  RestAssured.basePath ="facts?limit=332";
        Response response = RestAssured.given().accept("application/json")
                .queryParam("limit", 332)
                .when().get()
                .then().statusCode(200)
                .extract().response();
        Map<String, Object> parsedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

        List<Map<String, Object>> allFacts = (List<Map<String, Object>>) parsedResponse.get("data");

        List<String> nonCatRelatedFacts = new ArrayList<>();
        List<String> lessThan50LengthFacts = new ArrayList<>();
        List<String> moreThan200LengthFacts = new ArrayList<>();

        for (int i = 0; i < allFacts.size(); i++) {
//                if ((Integer) allFacts.get(i).get("length") < 50) {
//                    lessThan50LengthFacts.add((String) allFacts.get(i).get("fact"));
//                } else if ((Integer) allFacts.get(i).get("length") > 200) {
//                    moreThan200LengthFacts.add((String) allFacts.get(i).get("fact"));
            //             }
            String fact = allFacts.get(i).get("fact").toString().toLowerCase();

                if (fact.length() <= 50) {
                    lessThan50LengthFacts.add(fact);
                } else if (fact.length() > 200) {
                    moreThan200LengthFacts.add(fact);
                }
                if (!fact.contains("cat") && !fact.contains("cats") && !fact.contains("kittens")) {
                    nonCatRelatedFacts.add(fact);
                }

        }

            System.out.println("Less than 50 symbols facts -> " + lessThan50LengthFacts);
            System.out.println("More than 200 symbols facts ->" + moreThan200LengthFacts);
            System.out.println("Non Cat Related facts -> " + nonCatRelatedFacts);

    }

}

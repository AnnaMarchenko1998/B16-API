package utils;

public class PayloadUtils {

    public static String getPetPayload(Integer petId, String petName){
        String petPayload = "{\n" +
                "  \"id\": "+ petId +",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"Some Name\"\n" +
                "  },\n" +
                "  \"name\": \""+ petName +"\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://s3.amazon.com\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    \n" +
                "  ],\n" +
                "  \"status\": \"sdet doggie\"\n" +
                "}";
        return petPayload;
    }

    public static String getSlackPayload(String message){
        String slackPayload = "{\n" +
                "  \"channel\": \"C05H5S8A50V\",\n" +
                "  \"text\": \"Anna: "+message+"\"\n" +
                "}";
        return slackPayload;
    }

    public static String getAirportPayload(String originAirport, String destinationAirport){
        String airportLocations = "{\n" +
                "    \"from\": \""+originAirport+"\",\n" +
                "    \"to\": \""+destinationAirport+"\"\n" +
                "}";
        return airportLocations;
    }
}

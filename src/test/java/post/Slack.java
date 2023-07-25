package post;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import pojo.SlackPojo;
import utils.ConfigReader;
import utils.PayloadUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Slack {

@Test
    public void sendMessageTest(){

    RestAssured.baseURI = "https://slack.com";
    RestAssured.basePath = "api/chat.postMessage";

    Response response = RestAssured.given().accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer xoxb-4764264203175-5572283351303-ChTt1SkoH3zJPsSqvNZ3K8uq")
            .body(PayloadUtils.getSlackPayload("doing my Homework"))
            .when().post()
            .then().statusCode(200)
            .extract().response();

    SlackPojo parsedResponse = response.as(SlackPojo.class);
    String actualText = parsedResponse.getMessage().getText();
    Assert.assertTrue(actualText.contains("Anna"));
}

    /*
        Homework:
        1. Send slack message via API request +
        2. Login to Slack website(use selenium) +
        3. Navigate to #api channel +
        4. Validate your message is in Slack UI +

        Homework2:
	    1. Send slack message from slack website(use selenium) +
	    2. Send API GET request to list all messages +
	    3. Validate your message is in the list. +

	    Homework3:
	    1. Send slack message via API request +
	    2. Send API GET request to list all messages
	    3. Validate your message is in the list.

         */
@Test
    public void homeworkSlackTest1() throws AWTException, InterruptedException {
    WebDriver driver= new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.navigate().to("https://slack.com");
    WebElement signIn = driver.findElement(By.xpath("//a[@class='c-nav--signed-out__link']"));
    signIn.click();
    WebElement manuallyLogin = driver.findElement(By.xpath("//a[.='sign in manually instead']"));
    manuallyLogin.click();
    WebElement chanelLink = driver.findElement(By.cssSelector("#domain"));
    chanelLink.sendKeys(ConfigReader.readProperty("QA_slack_chanelLink"));
    WebElement continueBttn = driver.findElement(By.xpath("//button[@type='submit']"));
    continueBttn.click();
    WebElement signInWithPassword = driver.findElement(By.xpath("//a[.='sign in with a password instead']"));
    signInWithPassword.click();
    WebElement email = driver.findElement(By.cssSelector("#email"));
    email.sendKeys(ConfigReader.readProperty("QA_slack_username"));
    WebElement password = driver.findElement(By.cssSelector("#password"));
    password.sendKeys(ConfigReader.readProperty("QA_slack_password"));
    WebElement signInBtn = driver.findElement(By.cssSelector("#signin_btn"));
    signInBtn.click();
    Thread.sleep(5000);
    Robot robot = new Robot();
    robot.keyPress(KeyEvent.VK_ENTER);
    WebElement openInBrowser = driver.findElement(By.xpath("//a[.='use Slack in your browser']"));
    openInBrowser.click();
    WebElement apiChanel = driver.findElement(By.xpath("//span[@data-qa='channel_sidebar_name_api']"));
    apiChanel.click();
    List<WebElement> allMessages = driver.findElements(By.xpath("//div[@class='p-rich_text_section']"));
    for (WebElement myText : allMessages){
        if (myText.getText().contains("doing my Homework")){
        Assert.assertTrue(myText.getText().contains("doing my Homework"));
            break;
        }
    }

    //Homework2
    WebElement textBox = driver.findElement(By.xpath("//div[@role='textbox']"));
    textBox.sendKeys("Jenkins is almost here");
    WebElement send = driver.findElement(By.xpath("//button[@aria-label='Send now']"));
    send.click();
    driver.quit();
}

@Test
    public void validateSlackMessageApiTest(){

    RestAssured.baseURI = "https://slack.com";
    RestAssured.basePath = "api/conversations.history";

    Response response = RestAssured.given().accept(ContentType.JSON)
            .queryParam("channel", "C05H5S8A50V")
            .header("Authorization", "Bearer xoxb-4764264203175-5572283351303-ChTt1SkoH3zJPsSqvNZ3K8uq")
            .when().get()
            .then().statusCode(200)
            .extract().response();

    Map<String, Object> parsedResponse = response.as(new TypeRef<Map<String, Object>>() {
    });

    List<Map<String,Object>> messages = (List<Map<String, Object>>) parsedResponse.get("messages");

    for(Map<String,Object> oneMessage : messages){
        if (oneMessage.get("text").toString().contains("Jenkins is almost here")){
            Assert.assertTrue(oneMessage.get("text").toString().contains("Jenkins is almost here"));
            break;
        }
    }
}

@Test
    public void sendApiMessageToSlackTest(){

    RestAssured.baseURI = "https://slack.com";
    RestAssured.basePath = "api/chat.postMessage";

    Response response = RestAssured.given().accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer xoxb-4764264203175-5572283351303-ChTt1SkoH3zJPsSqvNZ3K8uq")
            .body(PayloadUtils.getSlackPayload("Asparagus"))
            .when().post()
            .then().statusCode(200)
            .extract().response();

    SlackPojo parsedResponse = response.as(SlackPojo.class);
    Assert.assertTrue(parsedResponse.getMessage().getText().contains("Asparagus"));

}

@Test
    public void getMessageFromSlackTest(){

    RestAssured.baseURI = "https://slack.com";
    RestAssured.basePath = "api/conversations.history";

    Response response = RestAssured.given().accept(ContentType.JSON)
            .queryParam("channel", "C05H5S8A50V")
            .header("Authorization", "Bearer xoxb-4764264203175-5572283351303-ChTt1SkoH3zJPsSqvNZ3K8uq")
            .when().get()
            .then().statusCode(200)
            .extract().response();

    Map<String,Object> parsedResponse = response.as(new TypeRef<Map<String, Object>>() {
    });

    List<Map<String,Object>> allMessages = (List<Map<String, Object>>) parsedResponse.get("messages");

    for (Map<String,Object> message : allMessages){
        if (message.get("text").toString().contains("Asparagus")){
            Assert.assertTrue(message.get("text").toString().contains("Asparagus"));
            break;
        }
    }



}


}

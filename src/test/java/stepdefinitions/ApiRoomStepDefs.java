package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojos.RoomPojo;

import java.util.List;

import static baseurl.MedunnaBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static stepdefinitions.MedunnaRoomStepDefs.roomNumber;

public class ApiRoomStepDefs {
    int roomId;
    Response response;
    RoomPojo expectedData;
    @Given("Oda ID bilgisi alinir")
    public void odaIDBilgisiAlinir() {
        spec.pathParams("first", "api", "second", "rooms")
                .queryParam("sort", "createdDate,desc");

        response = given(spec).when().get("{first}/{second}");

        List<Integer> idList = response.jsonPath().getList("findAll{it.roomNumber=="+roomNumber+"}.id");
        roomId = idList.get(0);

    }

    @When("Get request gonderilir")
    public void getRequestGonderilir() {
        spec.pathParams("first", "api", "second", "rooms", "third", roomId);

        expectedData = new RoomPojo(roomNumber, "SUITE", true, 123.00, "End To End Test icin olusturulmustur");

        response = given(spec).when().get("{first}/{second}/{third}");
    }

    @Then("Veriler dogrulanir")
    public void verilerDogrulanir() {
        RoomPojo actualData = response.as(RoomPojo.class);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.getRoomNumber(), actualData.getRoomNumber());
        assertEquals(expectedData.getRoomType(), actualData.getRoomType());
        assertEquals(expectedData.isStatus(), actualData.isStatus());
        assertEquals(expectedData.getPrice(), actualData.getPrice());
        assertEquals(expectedData.getDescription(), actualData.getDescription());
    }
}

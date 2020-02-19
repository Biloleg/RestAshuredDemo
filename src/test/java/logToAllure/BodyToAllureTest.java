package logToAllure;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BodyToAllureTest {
    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setContentType(ContentType.JSON)
            .setBasePath("/api/users")
            .build();
            //.filter(new AllureRestAssured());

    @BeforeTest
    public void setFilter(){
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    public void bodyTest() {
        User user = User.builder()
                .name("Oleg")
                .job("Automation")
                .build();

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post()
                .then()
                .log().body()
                .body("name", equalTo("Oleg"));
    }
}

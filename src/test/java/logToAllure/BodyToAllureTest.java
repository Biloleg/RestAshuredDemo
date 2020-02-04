package logToAllure;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Listeners(TestNGListener.class)
public class BodyToAllureTest {
    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in/")
            .setContentType(ContentType.JSON)
            .setBasePath("/api/users")
            .build();

    @Test
    public void bodyTest() {
        User user = new User();
        user.setName("Oleg");
        user.setJob("Automation");

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post()
                .then()
                .log().body()
                .body("name", equalTo("Oleg1"));
    }
}

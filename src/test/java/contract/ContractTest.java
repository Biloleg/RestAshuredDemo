package contract;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

public class ContractTest {

    @Test
    public void restGet() {
        given()
                .baseUri("https://reqres.in/")
                .contentType(ContentType.JSON)
                .basePath("/api/users")
                .param("page", 2)
                .when()
                .get()
                .then()
                .log()
                .body()
                .body(matchesJsonSchemaInClasspath("schema.json"));
    }
}

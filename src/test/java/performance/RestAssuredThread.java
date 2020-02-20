package performance;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredThread extends Thread {

    private int threadNumber;
    public static Map<Integer, Long> times;
    public static int failures;

    static {
        times = Collections.synchronizedMap(new HashMap<>());
    }

    public RestAssuredThread(int numb) {
        threadNumber = numb;
    }

    public void run() {
        System.out.println("start thread " + threadNumber);
        ValidatableResponse response = given()
                .baseUri("https://reqres.in")
                .contentType(ContentType.JSON)
                .basePath("/api/users")
                .param("page", 2)
                .when()
                .get()
                .then();
        if(response.extract().statusCode() != 200)
            failures++;
        times.put(threadNumber, response.extract().time());
    }
}

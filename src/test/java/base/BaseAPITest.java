package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * BaseAPITest — Common setup for all API test classes
 * Handles: base URL config, auth token, request/response specs
 */
public class BaseAPITest {

    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    protected static String authToken;
    protected static Properties config;

    @BeforeClass
    public void setUp() throws IOException {
        config = loadConfig();

        RestAssured.baseURI = config.getProperty(
                "base.url", "https://your-api-server.com"
        );

        // Build reusable request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        // Build reusable response specification
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();

        // Get auth token for secured endpoints
        authToken = getAuthToken();
    }

    private String getAuthToken() {
        return io.restassured.RestAssured
                .given()
                .spec(requestSpec)
                .body("{\"username\":\"" + config.getProperty("test.username") + "\"," +
                        "\"password\":\"" + config.getProperty("test.password") + "\"}")
                .when()
                .post(config.getProperty("auth.endpoint", "/api/auth/login"))
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    protected RequestSpecification authenticatedRequest() {
        return io.restassured.RestAssured
                .given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + authToken);
    }

    private Properties loadConfig() throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(
                "src/test/resources/config/api-config.properties"
        ));
        return props;
    }
}

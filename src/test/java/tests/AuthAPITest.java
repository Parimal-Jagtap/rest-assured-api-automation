package tests;

import api.AuthAPI;
import base.BaseAPITest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ResponseValidator;

/**
 * AuthAPITest — Test cases for Authentication API
 * Covers: valid login, invalid login, token validation, logout
 */
public class AuthAPITest extends BaseAPITest {

    private AuthAPI authAPI;

    @BeforeClass
    public void setUpAPI() throws Exception {
        setUp();
        authAPI = new AuthAPI(requestSpec);
    }

    @Test(groups = "smoke", description = "Valid login returns 200 and token")
    public void testValidLoginReturnsToken() {
        Response response = authAPI.login(
                config.getProperty("test.username"),
                config.getProperty("test.password")
        );

        new ResponseValidator(response)
                .statusCode(200)
                .hasField("token")
                .hasField("userId")
                .responseTimeBelow(3000);

        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Auth token should not be null");
        Assert.assertFalse(token.isEmpty(), "Auth token should not be empty");
    }

    @Test(groups = "regression", description = "Invalid password returns 401")
    public void testInvalidPasswordReturns401() {
        Response response = authAPI.login(
                config.getProperty("test.username"),
                "WrongPassword123"
        );

        new ResponseValidator(response)
                .statusCode(401)
                .fieldEquals("message", "Invalid credentials");
    }

    @Test(groups = "regression", description = "Missing username returns 400")
    public void testMissingUsernameReturns400() {
        Response response = authAPI.login("", config.getProperty("test.password"));

        new ResponseValidator(response)
                .statusCode(400)
                .hasField("error");
    }

    @Test(groups = "regression", description = "Invalid username returns 401")
    public void testInvalidUsernameReturns401() {
        Response response = authAPI.login(
                "nonexistent@test.com",
                config.getProperty("test.password")
        );

        new ResponseValidator(response).statusCode(401);
    }

    @Test(groups = "smoke", description = "Valid logout returns 200")
    public void testValidLogout() {
        Response response = authAPI.logout(authToken);
        new ResponseValidator(response).statusCode(200);
    }
}

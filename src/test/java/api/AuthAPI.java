package api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * AuthAPI — Wrapper for authentication API endpoints
 * Covers: login, logout, token refresh
 */
public class AuthAPI {

    private final RequestSpecification requestSpec;

    public AuthAPI(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    // POST /api/auth/login
    public Response login(String username, String password) {
        String body = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}",
                username, password
        );
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/auth/login");
    }

    // POST /api/auth/logout
    public Response logout(String token) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/auth/logout");
    }

    // POST /api/auth/refresh
    public Response refreshToken(String refreshToken) {
        String body = String.format("{\"refreshToken\":\"%s\"}", refreshToken);
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/auth/refresh");
    }
}

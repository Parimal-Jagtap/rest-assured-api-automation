package utils;

import io.restassured.response.Response;
import org.testng.Assert;

/**
 * ResponseValidator — Reusable response validation utility
 * Covers: status code, response time, body fields, headers
 */
public class ResponseValidator {

    private final Response response;

    public ResponseValidator(Response response) {
        this.response = response;
    }

    // Validate HTTP status code
    public ResponseValidator statusCode(int expectedCode) {
        int actualCode = response.getStatusCode();
        Assert.assertEquals(actualCode, expectedCode,
                "Expected status code " + expectedCode + " but got " + actualCode);
        return this;
    }

    // Validate field exists in response body
    public ResponseValidator hasField(String fieldPath) {
        Object value = response.jsonPath().get(fieldPath);
        Assert.assertNotNull(value,
                "Expected field '" + fieldPath + "' not found in response");
        return this;
    }

    // Validate field value matches expected
    public ResponseValidator fieldEquals(String fieldPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(fieldPath);
        Assert.assertEquals(actualValue, expectedValue,
                "Field '" + fieldPath + "' mismatch");
        return this;
    }

    // Validate response time is within limit
    public ResponseValidator responseTimeBelow(long milliseconds) {
        long actualTime = response.getTime();
        Assert.assertTrue(actualTime < milliseconds,
                "Response time " + actualTime + "ms exceeded limit of " + milliseconds + "ms");
        return this;
    }

    // Validate response header exists
    public ResponseValidator hasHeader(String headerName) {
        String headerValue = response.getHeader(headerName);
        Assert.assertNotNull(headerValue,
                "Expected header '" + headerName + "' not found");
        return this;
    }

    // Extract field value from response
    public <T> T extract(String fieldPath) {
        return response.jsonPath().get(fieldPath);
    }

    // Get full response for further assertions
    public Response getResponse() {
        return response;
    }
}

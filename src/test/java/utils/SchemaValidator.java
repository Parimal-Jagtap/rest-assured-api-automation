package utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;

/**
 * SchemaValidator — JSON schema validation utility
 * Validates API response structure matches expected schema
 * Catches breaking API contract changes early
 */
public class SchemaValidator {

    /**
     * Validate response body against JSON schema file
     * Schema files stored in src/test/resources/schemas/
     *
     * Usage:
     * SchemaValidator.validate(response, "product-schema.json")
     */
    public static void validate(Response response, String schemaFileName) {
        try {
            response.then().assertThat().body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "schemas/" + schemaFileName
                )
            );
        } catch (Exception e) {
            Assert.fail("Schema validation failed for: "
                + schemaFileName + "\nError: " + e.getMessage());
        }
    }

    /**
     * Validate specific field types in response
     * Ensures API returns correct data types
     */
    public static void validateFieldType(
        Response response,
        String fieldPath,
        Class<?> expectedType
    ) {
        Object value = response.jsonPath().get(fieldPath);
        Assert.assertNotNull(value,
            "Field '" + fieldPath + "' should exist");
        Assert.assertTrue(expectedType.isInstance(value),
            "Field '" + fieldPath + "' should be of type "
            + expectedType.getSimpleName()
            + " but was " + value.getClass().getSimpleName());
    }

    /**
     * Validate response array is not empty
     */
    public static void validateArrayNotEmpty(
        Response response,
        String arrayPath
    ) {
        int size = response.jsonPath().getList(arrayPath).size();
        Assert.assertTrue(size > 0,
            "Array at '" + arrayPath + "' should not be empty");
    }
}

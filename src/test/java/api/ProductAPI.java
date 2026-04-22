package api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * ProductAPI — Wrapper for product API endpoints
 * Covers: get all, get by ID, create, update, delete
 */
public class ProductAPI {

    private final RequestSpecification requestSpec;

    public ProductAPI(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    // GET /api/products
    public Response getAllProducts() {
        return given()
                .spec(requestSpec)
                .when()
                .get("/api/products");
    }

    // GET /api/products/{id}
    public Response getProductById(String productId) {
        return given()
                .spec(requestSpec)
                .pathParam("id", productId)
                .when()
                .get("/api/products/{id}");
    }

    // POST /api/products
    public Response createProduct(String name, String price, String category) {
        String body = String.format(
                "{\"name\":\"%s\",\"price\":%s,\"category\":\"%s\"}",
                name, price, category
        );
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/products");
    }

    // PUT /api/products/{id}
    public Response updateProduct(String productId, String body) {
        return given()
                .spec(requestSpec)
                .pathParam("id", productId)
                .body(body)
                .when()
                .put("/api/products/{id}");
    }

    // DELETE /api/products/{id}
    public Response deleteProduct(String productId) {
        return given()
                .spec(requestSpec)
                .pathParam("id", productId)
                .when()
                .delete("/api/products/{id}");
    }

    // GET /api/products?category={category}
    public Response getProductsByCategory(String category) {
        return given()
                .spec(requestSpec)
                .queryParam("category", category)
                .when()
                .get("/api/products");
    }
}

package api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * OrderAPI — Wrapper for order API endpoints
 * Covers: create order, get order, update status, cancel
 */
public class OrderAPI {

    private final RequestSpecification requestSpec;

    public OrderAPI(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    // POST /api/orders
    public Response createOrder(String productId, int quantity, String address) {
        String body = String.format(
                "{\"productId\":\"%s\",\"quantity\":%d,\"shippingAddress\":\"%s\"}",
                productId, quantity, address
        );
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/orders");
    }

    // GET /api/orders/{id}
    public Response getOrderById(String orderId) {
        return given()
                .spec(requestSpec)
                .pathParam("id", orderId)
                .when()
                .get("/api/orders/{id}");
    }

    // GET /api/orders
    public Response getAllOrders() {
        return given()
                .spec(requestSpec)
                .when()
                .get("/api/orders");
    }

    // PATCH /api/orders/{id}/status
    public Response updateOrderStatus(String orderId, String status) {
        String body = String.format("{\"status\":\"%s\"}", status);
        return given()
                .spec(requestSpec)
                .pathParam("id", orderId)
                .body(body)
                .when()
                .patch("/api/orders/{id}/status");
    }

    // DELETE /api/orders/{id}
    public Response cancelOrder(String orderId) {
        return given()
                .spec(requestSpec)
                .pathParam("id", orderId)
                .when()
                .delete("/api/orders/{id}");
    }
}

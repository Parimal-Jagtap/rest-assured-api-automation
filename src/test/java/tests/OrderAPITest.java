package tests;

import api.OrderAPI;
import api.ProductAPI;
import base.BaseAPITest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ResponseValidator;

/**
 * OrderAPITest — Test cases for Order API
 * Covers: create, get, update status, cancel, validation
 */
public class OrderAPITest extends BaseAPITest {

    private OrderAPI orderAPI;
    private ProductAPI productAPI;
    private String createdOrderId;
    private String validProductId;

    @BeforeClass
    public void setUpAPI() throws Exception {
        setUp();
        orderAPI = new OrderAPI(authenticatedRequest());
        productAPI = new ProductAPI(authenticatedRequest());

        // Get a valid product ID to use in order tests
        Response products = productAPI.getAllProducts();
        validProductId = products.jsonPath().getString("products[0].id");
    }

    @Test(groups = "smoke", description = "Create order returns 201 with order ID")
    public void testCreateOrderReturns201() {
        Response response = orderAPI.createOrder(
                validProductId, 2, "123 Test Street, Pune, 411001"
        );

        new ResponseValidator(response)
                .statusCode(201)
                .hasField("orderId")
                .hasField("status")
                .hasField("totalAmount")
                .fieldEquals("status", "pending")
                .responseTimeBelow(5000);

        createdOrderId = response.jsonPath().getString("orderId");
        Assert.assertNotNull(createdOrderId, "Order ID should not be null");
    }

    @Test(groups = "smoke",
            description = "Get created order returns correct details",
            dependsOnMethods = "testCreateOrderReturns201")
    public void testGetOrderByIdReturns200() {
        Response response = orderAPI.getOrderById(createdOrderId);

        new ResponseValidator(response)
                .statusCode(200)
                .hasField("orderId")
                .hasField("status")
                .hasField("items")
                .fieldEquals("orderId", createdOrderId);
    }

    @Test(groups = "regression",
            description = "Update order status to confirmed",
            dependsOnMethods = "testCreateOrderReturns201")
    public void testUpdateOrderStatusReturns200() {
        Response response = orderAPI.updateOrderStatus(createdOrderId, "confirmed");

        new ResponseValidator(response)
                .statusCode(200)
                .fieldEquals("status", "confirmed");
    }

    @Test(groups = "regression", description = "Get invalid order ID returns 404")
    public void testGetInvalidOrderReturns404() {
        Response response = orderAPI.getOrderById("invalid-order-id-999");
        new ResponseValidator(response).statusCode(404);
    }

    @Test(groups = "regression", description = "Create order with invalid product returns 400")
    public void testCreateOrderWithInvalidProductReturns400() {
        Response response = orderAPI.createOrder(
                "invalid-product-id", 1, "123 Test Street"
        );
        new ResponseValidator(response).statusCode(400);
    }

    @Test(groups = "regression",
            description = "Cancel order returns 200",
            dependsOnMethods = "testUpdateOrderStatusReturns200")
    public void testCancelOrderReturns200() {
        Response response = orderAPI.cancelOrder(createdOrderId);
        new ResponseValidator(response).statusCode(200);
    }

    @Test(groups = "smoke", description = "Get all orders returns list")
    public void testGetAllOrdersReturns200() {
        Response response = orderAPI.getAllOrders();

        new ResponseValidator(response)
                .statusCode(200)
                .hasField("orders")
                .responseTimeBelow(3000);
    }
}

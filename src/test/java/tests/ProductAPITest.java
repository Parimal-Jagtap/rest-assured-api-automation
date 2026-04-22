package tests;

import api.ProductAPI;
import base.BaseAPITest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ResponseValidator;

/**
 * ProductAPITest — Test cases for Product API
 * Covers: get all, get by ID, create, update, delete, filter
 */
public class ProductAPITest extends BaseAPITest {

    private ProductAPI productAPI;
    private String createdProductId;

    @BeforeClass
    public void setUpAPI() throws Exception {
        setUp();
        productAPI = new ProductAPI(authenticatedRequest());
    }

    @Test(groups = "smoke", description = "Get all products returns 200 and list")
    public void testGetAllProductsReturns200() {
        Response response = productAPI.getAllProducts();

        new ResponseValidator(response)
                .statusCode(200)
                .hasField("products")
                .responseTimeBelow(3000);

        int count = response.jsonPath().getList("products").size();
        Assert.assertTrue(count > 0, "Products list should not be empty");
    }

    @Test(groups = "smoke", description = "Get product by valid ID returns 200")
    public void testGetProductByIdReturns200() {
        // First get all products to extract a valid ID
        Response allProducts = productAPI.getAllProducts();
        String productId = allProducts.jsonPath().getString("products[0].id");

        Response response = productAPI.getProductById(productId);

        new ResponseValidator(response)
                .statusCode(200)
                .hasField("id")
                .hasField("name")
                .hasField("price")
                .fieldEquals("id", productId);
    }

    @Test(groups = "regression", description = "Get product by invalid ID returns 404")
    public void testGetProductByInvalidIdReturns404() {
        Response response = productAPI.getProductById("invalid-product-id-999");
        new ResponseValidator(response).statusCode(404);
    }

    @Test(groups = "regression", description = "Create product returns 201 with ID")
    public void testCreateProductReturns201() {
        Response response = productAPI.createProduct(
                "Test Product API", "999", "Electronics"
        );

        new ResponseValidator(response)
                .statusCode(201)
                .hasField("id")
                .hasField("name")
                .fieldEquals("name", "Test Product API");

        createdProductId = response.jsonPath().getString("id");
        Assert.assertNotNull(createdProductId, "Created product ID should not be null");
    }

    @Test(groups = "regression",
            description = "Delete product returns 200",
            dependsOnMethods = "testCreateProductReturns201")
    public void testDeleteProductReturns200() {
        Response response = productAPI.deleteProduct(createdProductId);
        new ResponseValidator(response).statusCode(200);
    }

    @Test(groups = "regression", description = "Filter products by category")
    public void testGetProductsByCategory() {
        Response response = productAPI.getProductsByCategory("Electronics");

        new ResponseValidator(response)
                .statusCode(200)
                .hasField("products");

        response.jsonPath()
                .getList("products.category")
                .forEach(category ->
                        Assert.assertEquals(category, "Electronics",
                                "All products should be in Electronics category"));
    }
}

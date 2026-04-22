package utils;

import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * RequestBuilder — Utility for building API requests
 * Covers: headers, query params, path params, body setup
 */
public class RequestBuilder {

    private final RequestSpecification request;

    public RequestBuilder(RequestSpecification baseRequest) {
        this.request = io.restassured.RestAssured.given().spec(baseRequest);
    }

    public RequestBuilder withBody(Object body) {
        request.body(body);
        return this;
    }

    public RequestBuilder withHeader(String key, String value) {
        request.header(key, value);
        return this;
    }

    public RequestBuilder withQueryParam(String key, String value) {
        request.queryParam(key, value);
        return this;
    }

    public RequestBuilder withPathParam(String key, String value) {
        request.pathParam(key, value);
        return this;
    }

    public RequestBuilder withQueryParams(Map<String, String> params) {
        params.forEach(request::queryParam);
        return this;
    }

    public RequestSpecification build() {
        return request;
    }
}

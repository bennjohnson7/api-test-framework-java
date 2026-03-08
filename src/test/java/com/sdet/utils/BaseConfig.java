package com.sdet.utils;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public class BaseConfig {

    public static RequestSpecification requestSpec;

    public static void setup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setBasePath("")
                .addHeader("Content-Type", "application/json")
                .log(LogDetail.ALL)
                .build();

        RestAssured.requestSpecification = requestSpec;
    }
}
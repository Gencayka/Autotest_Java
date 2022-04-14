package ru.Chayka.rest;

import io.restassured.response.Response;
import lombok.Value;

import java.util.Map;

@Value
public class RestRequestTestLogData {
    String testName;
    Map<String ,String> requestHeaders;
    String requestBodyAsString;
    Response restAssuredResponse;
}

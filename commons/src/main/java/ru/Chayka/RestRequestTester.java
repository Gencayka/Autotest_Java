package ru.Chayka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.NonNull;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Класс предназначен для тестирования сервисов посредством отправки на сервис REST-запросов
 * <br>Абстрактный класс, для каждого сервиса используется отдельный класс-наследник
 */
public abstract class RestRequestTester {
    protected final RequestSpecification baseRequestSpecification;
    protected final Map<String, String> defaultHeaders;
    protected final ObjectMapper mapper;
    protected SoftAssert softAssert;
    protected TestCounter counter;
    protected final Logger logger;

    protected RestRequestTester(@NonNull Logger logger) {
        baseRequestSpecification = RestAssured.given();
        defaultHeaders = new HashMap<>();
        mapper = new ObjectMapper();
        softAssert = new SoftAssert();
        this.logger = logger;
    }

    public final void clearSoftAssert() {
        softAssert = new SoftAssert();
    }

    /**
     * Метод отправляет на сервис REST-запрос типа POST
     *
     * @param requestHeaders       заголовки запроса
     * @param requestSpecification спецификация запроса
     * @param requestBodyAsString  JSON-тело запроса
     * @return ответ на запрос
     */
    protected Response sendPostRequest(Map<String, String> requestHeaders,
                                       RequestSpecification requestSpecification,
                                       String requestBodyAsString) {
        counter.increase();
        RequestSpecification requestSpecificationWithHeaders = RestAssured.given().spec(requestSpecification);
        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            if (header.getValue() != null) {
                requestSpecificationWithHeaders.header(header.getKey(), header.getValue());
            }
        }

        return requestSpecificationWithHeaders
                .and()
                .body(requestBodyAsString)
                .when()
                .post()
                .then()
                .extract().response();
    }

    /**
     * Метод отправляет на сервис REST-запрос типа POST с базовой спецификацией запроса
     *
     * @param requestHeaders      заголовки запроса
     * @param requestBodyAsString JSON-тело запроса
     * @return ответ на запрос
     */
    protected Response sendPostRequest(Map<String, String> requestHeaders,
                                       String requestBodyAsString) {
        return sendPostRequest(requestHeaders, baseRequestSpecification, requestBodyAsString);
    }

    /**
     * Метод отправляет на сервис REST-запрос типа PUT
     *
     * @param requestHeaders       заголовки запроса
     * @param requestSpecification спецификация запроса
     * @param requestBodyAsString  JSON-тело запроса
     * @return ответ на запрос
     */
    protected Response sendPutRequest(Map<String, String> requestHeaders,
                                      RequestSpecification requestSpecification,
                                      String requestBodyAsString) {
        counter.increase();
        RequestSpecification requestSpecificationWithHeaders = RestAssured.given().spec(requestSpecification);
        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            if (header.getValue() != null) {
                requestSpecificationWithHeaders.header(header.getKey(), header.getValue());
            }
        }

        return requestSpecificationWithHeaders
                .and()
                .body(requestBodyAsString)
                .when()
                .put()
                .then()
                .extract().response();
    }

    /**
     * Метод отправляет на сервис REST-запрос типа PUT с базовой спецификацией запроса
     *
     * @param requestHeaders      заголовки запроса
     * @param requestBodyAsString JSON-тело запроса
     * @return ответ на запрос
     */
    protected Response sendPutRequest(Map<String, String> requestHeaders,
                                      String requestBodyAsString) {
        return sendPutRequest(requestHeaders, baseRequestSpecification, requestBodyAsString);
    }

    protected void checkResponseHttpCode(Response restAssuredResponse,
                                           ResponseStatusValues expectedResponseStatusValues) {
        softAssert.assertEquals(Integer.valueOf(restAssuredResponse.getStatusCode()),
                expectedResponseStatusValues.getHttpCode(),
                "Http code of response check failed:");
    }

    protected void checkResponseStatusKeys(ResponseBody responseBody,
                                           ResponseStatusValues expectedResponseStatusValues) {
        logger.debug("Starting response status checks");

        softAssert.assertEquals(responseBody.getStatusCode(), expectedResponseStatusValues.getStatusCode(),
                "statusCode check in response body failed:");
        softAssert.assertTrue(Pattern.matches(expectedResponseStatusValues.getStatusDesc(), responseBody.getStatusDesc()),
                "statusDesc check in response body failed:");

        logger.debug("All response status checks completed");
    }

    /**
     * Метод проверяет соответствие JSON-тела запроса/ответа схеме валидации
     * <br>Если результат валидации не соответствует ожидаемому, в SoftAssert записывается соответствующая ошибка
     *
     * @param jsonBodyAsString JSON-тело запроса/ответа
     * @param jsonSchema       схема валидации JSON-тела запроса/ответа
     * @param expectedValid    должно ли тело успешно пройти валидацию
     */
    protected void validateJsonBody(String jsonBodyAsString, JsonSchemaClass jsonSchema, boolean expectedValid) {
        logger.debug(String.format("Starting %s body validation", jsonSchema.getName().toLowerCase(Locale.ROOT)));
        try {
            Schema schema = SchemaLoader.load(new JSONObject(jsonSchema.getText()));
            schema.validate(new JSONObject(jsonBodyAsString));
        } catch (JSONException ex) {
            softAssert.fail(String.format("Failed to validate %s body\n%s", jsonSchema.getName().toLowerCase(Locale.ROOT),
                    ex.getMessage()));
            return;
        } catch (ValidationException validationException) {
            if (expectedValid) {
                softAssert.fail(String.format("%s body validation failed\n%s", jsonSchema.getName().toLowerCase(Locale.ROOT),
                        validationException.getMessage()));
            } else {
                logger.info(String.format("Validation of %s failed successfully\n%s", jsonSchema.getName().toLowerCase(Locale.ROOT),
                        validationException.getMessage()));
            }
            logger.debug(String.format("Validation of %s completed", jsonSchema.getName().toLowerCase(Locale.ROOT)));
            return;
        }
        if (!expectedValid) {
            softAssert.fail(String.format("%s body validation succeed for some reason", jsonSchema.getName().toLowerCase(Locale.ROOT)));
        }
    }

    /**
     * Метод проверяет соответствие JSON-тела запроса/ответа схеме валидации
     * <br>Если JSON-тело не прошло валидацию, в SoftAssert записывается соответствующая ошибка
     *
     * @param jsonBodyAsString JSON-тело запроса/ответа
     * @param jsonSchema       схема валидации JSON-тела запроса/ответа
     */
    protected void validateJsonBody(String jsonBodyAsString, JsonSchemaClass jsonSchema) {
        validateJsonBody(jsonBodyAsString, jsonSchema, true);
    }

    /**
     * Метод вызывает промежуточную проверку наличия ошибок в SoftAssert.
     * В случае обнаружения ошибок тест-кейс завершается, а в логгер записываются список ошибок
     * и финальный лог (параметры запроса и ответа)
     *
     * @param testName            название тест-кейса
     * @param requestHeaders      заголовки запроса
     * @param requestBodyAsString JSON-тело запроса
     * @param restAssuredResponse ответ типа Response
     */
    protected void assertAll(String testName,
                             Map<String, String> requestHeaders,
                             String requestBodyAsString,
                             Response restAssuredResponse) {
        try {
            softAssert.assertAll();
        } catch (AssertionError assertionError) {
            logger.error(formFinalLog(testName, requestHeaders, requestBodyAsString, restAssuredResponse, false));
            logger.error("CRITICAL DISCREPANCY DETECTED");
            logger.error(assertionError.getMessage());
            throw assertionError;
        }
    }

    protected void assertAll(String testName,
                             Map<String, String> requestHeaders,
                             ResponseBody responseBody,
                             Response restAssuredResponse) throws JsonProcessingException {
        assertAll(testName, requestHeaders, mapper.writeValueAsString(responseBody), restAssuredResponse);
    }

    /**
     * Метод вызывает финальную проверку наличия ошибок в SoftAssert.
     * Метод следует вызывать в конце тестового метода ВМЕСТО softAssert.assertAll()
     * В результате в логгер записываются список ошибок и финальный лог (параметры запроса и ответа)
     *
     * @param testName            название тест-кейса
     * @param requestHeaders      заголовки запроса
     * @param requestBodyAsString JSON-тело запроса
     * @param restAssuredResponse ответ типа Response
     */
    protected void finalAssertAll(String testName,
                                  Map<String, String> requestHeaders,
                                  String requestBodyAsString,
                                  Response restAssuredResponse) {
        try {
            softAssert.assertAll();
        } catch (AssertionError assertionError) {
            logger.error(formFinalLog(testName, requestHeaders, requestBodyAsString, restAssuredResponse, false));
            logger.error(assertionError.getMessage());
            throw assertionError;
        }

        logger.info(formFinalLog(testName, requestHeaders, requestBodyAsString, restAssuredResponse, true));
    }

    /**
     * Метод формирует лог результатов тест-кейса
     * <br>Лог содержит название тест-кейса, успешность тест-кейса, заголовки и тело запроса,
     * заголовки и тело ответа
     *
     * @param testName            название тест-кейса
     * @param requestHeaders      заголовки запроса
     * @param requestBodyAsString JSON-тело запроса
     * @param restAssuredResponse ответ типа Response
     * @param isSuccessful        пройден ли кейс успешно
     * @return лог результатов тест-кейса
     */
    protected String formFinalLog(String testName,
                                  Map<String, String> requestHeaders,
                                  String requestBodyAsString,
                                  Response restAssuredResponse,
                                  boolean isSuccessful) {
        StringBuilder finalLog = new StringBuilder();
        if (isSuccessful) {
            finalLog.append(String.format("\nTest %s succeeded\n\n", testName));
        } else {
            finalLog.append(String.format("\nTest %s failed\n\n", testName));
        }

        finalLog.append("Request headers: \n");
        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            finalLog.append(String.format("%s: %s\n", header.getKey(), header.getValue()));
        }
        finalLog.append(String.format("Request body:\n%s\n\n", prettyPrintJson(requestBodyAsString)));

        finalLog.append("Response headers\n");
        Headers responseHeaders = restAssuredResponse.headers();
        for (Header header : responseHeaders.asList()) {
            finalLog.append(String.format("%s: %s\n", header.getName(), header.getValue()));
        }

        finalLog.append(String.format("Response body\n%s\n\n", prettyPrintJson(restAssuredResponse.asString())));
        return finalLog.toString();
    }

    /**
     * Метод форматирует JSON, делая его читабельным
     *
     * @param uglyJson неотформатированный JSON
     * @return отформатированный JSON
     */
    private String prettyPrintJson(String uglyJson) {
        ObjectMapper localMapper = new ObjectMapper();

        try {
            Object jsonBuffer = localMapper.readValue(uglyJson, Object.class);
            return localMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonBuffer);
        } catch (IOException ignore) {
            return uglyJson;
        }
    }
}

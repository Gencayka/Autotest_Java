package ru.Chayka.services.service2;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.Chayka.RestRequestTester;
import ru.Chayka.RestConfig;
import ru.Chayka.entities.Entity1;
import ru.Chayka.repositories.Entity1Repository;
import ru.Chayka.services.service2.enums.S2HeaderPattern;
import ru.Chayka.services.service2.enums.S2ResponseStatusValues;
import ru.Chayka.services.service2.requestbody.keys.S2Key2;
import ru.Chayka.services.service2.requestbody.keys.S2Key1;
import ru.Chayka.services.service2.requestbody.S2RequestBody;
import ru.Chayka.services.service2.responsebody.S2ResponseBody;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Класс предназначен для тестирования сервиса Service2
 */
@Component
public final class S2Tester extends RestRequestTester {
    private final S2TestDataHolder testDataHolder;
    private final Entity1Repository entity1Repository;

    public S2Tester(@Autowired S2TestDataHolder testDataHolder,
                    @Autowired Entity1Repository entity1Repository,
                    @Autowired S2TestCounter counter) {
        super(LoggerFactory.getLogger(S2Tester.class.getSimpleName()));

        defaultHeaders.put(S2HeaderPattern.HEADER1.getHeaderName(), testDataHolder.getDefaultHeader1());
        defaultHeaders.put(S2HeaderPattern.HEADER2.getHeaderName(), testDataHolder.getDefaultHeader2());
        defaultHeaders.put(S2HeaderPattern.HEADER3.getHeaderName(), testDataHolder.getDefaultHeader3());
        defaultHeaders.put(S2HeaderPattern.HEADER4.getHeaderName(), testDataHolder.getDefaultHeader4());

        this.testDataHolder = testDataHolder;
        this.entity1Repository = entity1Repository;
        this.counter = counter;

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        baseRequestSpecification
                .baseUri(RestConfig.getUniqueInstance().getBaseUri())
                .port(RestConfig.getUniqueInstance().getPort())
                .basePath(RestConfig.getUniqueInstance().getService2BasePath())
                .urlEncodingEnabled(false)
                .contentType(ContentType.JSON);
    }

    public void baseTest(String testName,
                         S2ResponseStatusValues responseStatusValues,
                         Map<String, String> requestHeaders,
                         String parameter,
                         boolean isRequestBodyValid) throws IOException {
        clearSoftAssert();
        logger.debug("Starting test " + testName);

        List<Entity1> allEntities1 = entity1Repository.findAll();

        S2RequestBody requestBody = S2RequestBody.builder()
                .s2Key1(S2Key1.builder()
                        .s2Key2(S2Key2.builder()
                                .parameter(parameter)
                                .build())
                        .build())
                .build();

        String requestBodyAsString = mapper.writeValueAsString(requestBody);

        Response restAssuredResponse = sendPostRequest(requestHeaders, requestBodyAsString);

        //Проверки
        checkResponseHttpCode(restAssuredResponse, responseStatusValues);
        assertAll(testName, requestHeaders, requestBodyAsString, restAssuredResponse);

        S2ResponseBody responseBody = mapper.readValue(restAssuredResponse.asString(), S2ResponseBody.class);
        checkResponseStatusKeys(responseBody, responseStatusValues);
        assertAll(testName, requestHeaders, requestBodyAsString, restAssuredResponse);

        checkResponseHeaders(requestHeaders, restAssuredResponse, responseStatusValues);
        validateJsonBody(requestBodyAsString, testDataHolder.getRequestJsonSchema(), isRequestBodyValid);
        validateJsonBody(restAssuredResponse.asString(), testDataHolder.getResponseJsonSchema());

        /*
        Остальные проверки
         */

        finalAssertAll(testName, requestHeaders, requestBodyAsString, restAssuredResponse);
    }

    public void specificHeadersTest(String testName,
                                    S2ResponseStatusValues responseValues,
                                    Map<String, String> requestHeaders,
                                    String parameter,
                                    boolean isRequestBodyValid) throws IOException {
        Map<String, String> localRequestHeaders = new HashMap<>(defaultHeaders);
        localRequestHeaders.put(S2HeaderPattern.HEADER5.getHeaderName(), testDataHolder.formHeader5());

        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            if (header.getValue() == null) {
                localRequestHeaders.remove(header.getKey());
            } else {
                localRequestHeaders.put(header.getKey(), header.getValue());
            }
        }

        baseTest(testName, responseValues, localRequestHeaders, parameter, isRequestBodyValid);
    }

    public void specificHeadersTest(String testName,
                                    S2ResponseStatusValues responseValues,
                                    Map<String, String> requestHeaders,
                                    boolean isRequestBodyValid) throws IOException {
        specificHeadersTest(testName, responseValues, requestHeaders, testDataHolder.getDefaultParameter(), isRequestBodyValid);
    }

    private void checkResponseHeaders(Map<String, String> requestHeaders,
                                                 Response restAssuredResponse,
                                                 S2ResponseStatusValues responseValues) {
        Headers responseHeaders = restAssuredResponse.headers();

        logger.debug("Found response headers: " + responseHeaders.size());
        logger.debug("Starting response headers checks");

        Set<String> requiredHeadersSet = new HashSet<>(requestHeaders.keySet());
        requiredHeadersSet.remove(S2HeaderPattern.HEADER2.getHeaderName());
        requiredHeadersSet.add(S2HeaderPattern.HEADER6.getHeaderName());

        Map<String, String> headersPatterns = new HashMap<>();
        for (S2HeaderPattern headerPattern : S2HeaderPattern.values()) {
            headersPatterns.put(headerPattern.getHeaderName(), headerPattern.getPattern());
        }

        if (responseValues == S2ResponseStatusValues.NO_REQUIRED_HEADER) {
            for (String requiredHeaderName : requiredHeadersSet) {
                switch (requiredHeaderName) {
                    case "header3":
                        softAssert.assertEquals(responseHeaders.getValue(requiredHeaderName), testDataHolder.getDefaultHeader3(),
                                String.format("%s check in response headers failed:", requiredHeaderName));
                    default:
                        softAssert.assertNull(responseHeaders.getValue(requiredHeaderName),
                                String.format("%s check in response headers failed:", requiredHeaderName));
                }
            }
        } else {
            for (String requiredHeaderName : requiredHeadersSet) {
                if (requestHeaders.containsKey(requiredHeaderName)
                        && Pattern.matches(headersPatterns.get(requiredHeaderName), requestHeaders.get(requiredHeaderName))) {
                    softAssert.assertEquals(responseHeaders.getValue(requiredHeaderName), requestHeaders.get(requiredHeaderName),
                            String.format("%s check in response headers failed:", requiredHeaderName));
                } else {
                    softAssert.assertNull(responseHeaders.getValue(requiredHeaderName),
                            String.format("%s check in response headers failed:", requiredHeaderName));
                }
            }
        }

        logger.debug("All response headers checks are completed");
    }
}

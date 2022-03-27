package ru.Chayka.services.service1;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.Chayka.restrequest.RestRequestTestLogData;
import ru.Chayka.restrequest.RestRequestTester;
import ru.Chayka.RestConfig;
import ru.Chayka.entities.Entity3;
import ru.Chayka.repositories.Entity3Repository;
import ru.Chayka.enums.TestClient;
import ru.Chayka.services.service1.enums.S1HeaderPattern;
import ru.Chayka.services.service1.enums.S1ResponseValues;
import ru.Chayka.services.service1.requestbody.S1RequestBody;
import ru.Chayka.services.service1.requestbody.keys.ClientS1Key;
import ru.Chayka.services.service1.requestbody.keys.S1Key1;
import ru.Chayka.services.service1.responsebody.S1ResponseBody;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Класс предназначен для тестирования сервиса Service1
 */
@Component
public final class S1Tester extends RestRequestTester <S1TestDataHolder> {
    private final Entity3Repository entity3Repository;

    public S1Tester(@Autowired S1TestDataHolder testDataHolder,
                    @Autowired Entity3Repository entity3Repository,
                    @Autowired S1TestCounter counter) {
        super(LoggerFactory.getLogger(S1Tester.class.getSimpleName()));

        defaultHeaders.put(S1HeaderPattern.HEADER1.getHeaderName(), testDataHolder.getDefaultHeader1());
        defaultHeaders.put(S1HeaderPattern.HEADER2.getHeaderName(), testDataHolder.getDefaultHeader2());
        defaultHeaders.put(S1HeaderPattern.HEADER3.getHeaderName(), testDataHolder.getDefaultHeader3());
        defaultHeaders.put(S1HeaderPattern.HEADER4.getHeaderName(), testDataHolder.getDefaultHeader4());

        this.testDataHolder = testDataHolder;
        this.entity3Repository = entity3Repository;
        this.counter = counter;

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        baseRequestSpecification
                .baseUri(RestConfig.getUniqueInstance().getBaseUri())
                .port(RestConfig.getUniqueInstance().getPort())
                .basePath(RestConfig.getUniqueInstance().getService1BasePath())
                .urlEncodingEnabled(false)
                .contentType(ContentType.JSON);
    }

    public void baseTest(String testName,
                         S1ResponseValues responseValues,
                         Map<String, String> requestHeaders,
                         TestClient testClient,
                         List<String> parameters,
                         boolean isRequestBodyValid) throws IOException {
        clearSoftAssert();
        logger.debug("Starting test " + testName);

        List<Entity3> allClientEntities3 = entity3Repository.findByClient(testClient);

        S1RequestBody requestBody = S1RequestBody.builder()
                .s1Key1(S1Key1.builder()
                        .clientS1Key(ClientS1Key.builder().buildByClient(testClient))
                        .parameter(parameters)
                        .build())
                .build();

        String requestBodyAsString = mapper.writeValueAsString(requestBody);

        Response restAssuredResponse = sendPostRequest(requestHeaders, requestBodyAsString);
        RestRequestTestLogData testLogData =
                new RestRequestTestLogData(testName, requestHeaders, requestBodyAsString, restAssuredResponse);

        checkResponseHttpCode(restAssuredResponse.statusCode(), responseValues, testLogData);

        validateJsonBody(requestBodyAsString, testDataHolder.getRequestJsonSchema(), isRequestBodyValid);
        validateJsonBody(restAssuredResponse.asString(), testDataHolder.getResponseJsonSchema());

        S1ResponseBody responseBody = deserializeResponseBody(S1ResponseBody.class, testLogData);
        checkResponseStatusCode(responseBody, responseValues, testLogData);

        checkResponseHeaders(requestHeaders, restAssuredResponse, responseValues);

        /*
        Остальные проверки
         */

        finalAssertAll(testName, requestHeaders, requestBodyAsString, restAssuredResponse);
    }

    public void baseTest(String testName,
                         S1ResponseValues responseValues,
                         TestClient testClient,
                         List<String> parameters,
                         boolean isRequestBodyValid) throws IOException {
        specificHeadersTest(testName,
                responseValues,
                new HashMap<>(),
                testClient,
                parameters,
                isRequestBodyValid);
    }

    public void baseTest(String testName,
                         S1ResponseValues responseValues,
                         TestClient testClient,
                         List<String> parameters) throws IOException {
        specificHeadersTest(testName,
                responseValues,
                new HashMap<>(),
                testClient,
                parameters,
                true);
    }

    public void specificHeadersTest(String testName,
                                    S1ResponseValues responseStatusValues,
                                    Map<String, String> requestHeaders,
                                    TestClient testClient,
                                    List<String> parameters,
                                    boolean isRequestBodyValid) throws IOException {
        Map<String, String> localRequestHeaders = new HashMap<>(defaultHeaders);
        localRequestHeaders.put(S1HeaderPattern.HEADER5.getHeaderName(), testDataHolder.formHeader5());
        localRequestHeaders.put(S1HeaderPattern.HEADER6.getHeaderName(), testDataHolder.formHeader6(testName));

        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            if (header.getValue() == null) {
                localRequestHeaders.remove(header.getKey());
            } else {
                localRequestHeaders.put(header.getKey(), header.getValue());
            }
        }

        baseTest(
                testName,
                responseStatusValues,
                localRequestHeaders,
                testClient,
                parameters,
                isRequestBodyValid);
    }

    public void specificHeadersTest(String testName,
                                    S1ResponseValues responseValues,
                                    Map<String, String> requestHeaders,
                                    boolean isRequestBodyValid) throws IOException {
        specificHeadersTest(testName,
                responseValues,
                requestHeaders,
                testDataHolder.getDefaultTestClient(),
                testDataHolder.getDefaultParameter(),
                isRequestBodyValid);
    }

    private void checkResponseHeaders(Map<String, String> requestHeaders,
                                      Response restAssuredResponse,
                                      S1ResponseValues responseStatusValues) {
        Headers responseHeaders = restAssuredResponse.headers();

        logger.debug("Found response headers: " + responseHeaders.size());
        logger.debug("Starting response headers checks");

        Set<String> requiredHeadersSet = new HashSet<>(requestHeaders.keySet());
        requiredHeadersSet.remove(S1HeaderPattern.HEADER2.getHeaderName());
        requiredHeadersSet.add(S1HeaderPattern.HEADER7.getHeaderName());

        Map<String, String> headersPatterns = new HashMap<>();
        for (S1HeaderPattern headerPattern : S1HeaderPattern.values()) {
            headersPatterns.put(headerPattern.getHeaderName(), headerPattern.getPattern());
        }

        if (responseStatusValues == S1ResponseValues.NO_REQUIRED_HEADER) {
            for (String requiredHeaderName : requiredHeadersSet) {
                softAssert.assertNull(responseHeaders.getValue(requiredHeaderName),
                        String.format("%s check in response headers failed:", requiredHeaderName));
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

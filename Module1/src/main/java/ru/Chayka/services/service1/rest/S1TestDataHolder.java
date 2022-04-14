package ru.Chayka.services.service1.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import ru.Chayka.rest.TestDataHolder;
import ru.Chayka.rest.JsonSchemaClass;
import ru.Chayka.TestClient;
import ru.Chayka.services.service1.S1ResponseValues;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Класс предназначен для формирования и хранения тестовых данных для тестирования сервиса Service1
 * <br>Часть тестовых данных считывается из файла S1TestConfig.properties
 */
@Component
@PropertySource("classpath:service1/S1TestConfig.properties")
public final class S1TestDataHolder extends TestDataHolder {
    @Getter
    private final String defaultHeader1;
    @Getter
    private final String defaultHeader2;
    @Getter
    private final String defaultHeader3;
    @Getter
    private final String defaultHeader4;
    @Getter
    private final TestClient defaultTestClient;
    @Getter
    private final List<String> defaultParameter;
    @Getter
    private final JsonSchemaClass requestJsonSchema;
    @Getter
    private final JsonSchemaClass responseJsonSchema;

    private final List<S1PositiveTestData> smokeTestData = new ArrayList<>();
    private final List<S1HeadersTestData> specificHeadersTestData = new ArrayList<>();
    private final List<S1HeadersTestData> nullOrEmptyHeadersTestData = new ArrayList<>();

    public S1TestDataHolder(@Autowired S1TestCounter counter,
                            @Value("${defaultHeader1}") String defaultHeader1,
                            @Value("${defaultHeader2}") String defaultHeader2,
                            @Value("${defaultHeader3}") String defaultHeader3,
                            @Value("${defaultHeader4}") String defaultHeader4,
                            @Value("${defaultTestClient}") TestClient defaultTestClient,
                            @Value("#{${defaultParameter}}") List<String> defaultParameter,
                            @Value("classpath:service1/S1RequestJsonSchema.json") Resource requestJsonSchema,
                            @Value("classpath:service1/S1ResponseJsonSchema.json") Resource responseJsonSchema)
            throws IOException {
        super(LoggerFactory.getLogger(S1TestDataHolder.class.getSimpleName()));
        this.counter = counter;

        this.defaultHeader1 = defaultHeader1;
        this.defaultHeader2 = defaultHeader2;
        this.defaultHeader3 = defaultHeader3;
        this.defaultHeader4 = defaultHeader4;
        this.defaultTestClient = defaultTestClient;
        this.defaultParameter = defaultParameter;
        this.requestJsonSchema = new JsonSchemaClass("Service1 request",
                StreamUtils.copyToString(requestJsonSchema.getInputStream(), StandardCharsets.UTF_8));
        this.responseJsonSchema = new JsonSchemaClass("Service1 response",
                StreamUtils.copyToString(responseJsonSchema.getInputStream(), StandardCharsets.UTF_8));

        smokeTestData.addAll(generateSmokeTestData());
        specificHeadersTestData.addAll(generateSpecificHeadersTestData());
        nullOrEmptyHeadersTestData.addAll(generateNullOrEmptyHeadersTestData());
    }

    public String formHeader5(){
        String strCounter = Integer.toString(counter.getValue());
        String filler = new String(new char[32 - strCounter.length() - 1]). replace("\0", "0");
        return strCounter + "a" + filler;
    }

    public String formHeader6(String testName){
        return "S1_" + testName;
    }

    public Object[][] getSmokeTestData() {
        return testDataToObjectsArray(new ArrayList<Object>(smokeTestData));
    }

    public Object[][] getSpecificHeadersTestData() {
        return testDataToObjectsArray(new ArrayList<Object>(specificHeadersTestData));
    }

    public Object[][] getNullOrEmptyHeadersTestData() {
        return testDataToObjectsArray(new ArrayList<Object>(nullOrEmptyHeadersTestData));
    }

    private List<S1PositiveTestData> generateSmokeTestData(){
        List<S1PositiveTestData> localSmokeTestData = new ArrayList<>();

        localSmokeTestData.add(new S1PositiveTestData(
                "smoke",
                defaultTestClient,
                new String[]{"395", "409"})
        );

        return localSmokeTestData;
    }

    private List<S1HeadersTestData> generateSpecificHeadersTestData(){
        List<S1HeadersTestData> localSpecificHeadersTestData = new ArrayList<>();

        localSpecificHeadersTestData.add(new S1HeadersTestData(
                "null_headers",
                S1ResponseValues.NO_REQUIRED_HEADER,
                new HashMap<>() {{
                    put("header1", null);
                    put("header2", null);
                    put("header3", null);
                    put("header4", null);
                    put("header5", null);
                    put("header6", null);
                }},
                true
        ));

        localSpecificHeadersTestData.add(new S1HeadersTestData(
                "empty_headers",
                S1ResponseValues.INVALID_HEADER,
                new HashMap<>() {{
                    put("header1", "");
                    put("header2", "");
                    put("header3", "");
                    put("header4", "");
                    put("header5", "");
                    put("header6", "");
                }},
                true
        ));

        return localSpecificHeadersTestData;
    }

    private List<S1HeadersTestData> generateNullOrEmptyHeadersTestData(){
        List<S1HeadersTestData> localNullOrEmptyHeadersTestData = new ArrayList<>();

        localNullOrEmptyHeadersTestData.add(new S1HeadersTestData(
                "null_headers",
                S1ResponseValues.NO_REQUIRED_HEADER,
                new HashMap<>() {{
                    put("header1", null);
                    put("header2", null);
                    put("header3", null);
                    put("header4", null);
                    put("header5", null);
                    put("header6", null);
                }},
                true
        ));

        localNullOrEmptyHeadersTestData.add(new S1HeadersTestData(
                "empty_headers",
                S1ResponseValues.INVALID_HEADER,
                new HashMap<>() {{
                    put("header1", "");
                    put("header2", "");
                    put("header3", "");
                    put("header4", "");
                    put("header5", "");
                    put("header6", "");
                }},
                true
        ));

        return localNullOrEmptyHeadersTestData;
    }

    @Getter
    @AllArgsConstructor
    public final static class S1PositiveTestData {
        public final String testName;
        public final TestClient testClient;
        public final List<String> parameters;

        public S1PositiveTestData(String testName, TestClient testClient, String[] parameters) {
            this.testName = testName;
            this.testClient = testClient;
            this.parameters = new ArrayList<>(Arrays.asList(parameters));
        }

        public S1PositiveTestData(String testName, TestClient testClient, String parameter) {
            this.testName = testName;
            this.testClient = testClient;
            this.parameters = new ArrayList<>(Collections.singletonList(parameter));
        }
    }

    @Getter
    @AllArgsConstructor
    public final static class S1HeadersTestData {
        public final String testName;
        public final S1ResponseValues responseValues;
        public final Map<String, String> requestHeaders;
        public final boolean isRequestBodyValid;
    }
}


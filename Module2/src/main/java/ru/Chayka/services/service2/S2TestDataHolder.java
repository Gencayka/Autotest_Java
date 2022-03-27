package ru.Chayka.services.service2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import ru.Chayka.TestDataHolder;
import ru.Chayka.restrequest.JsonSchemaClass;
import ru.Chayka.services.service2.enums.S2HeaderPattern;
import ru.Chayka.services.service2.enums.S2ResponseValues;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Класс предназначен для формирования и хранения тестовых данных для тестирования сервиса Service2
 * <br>Часть тестовых данных считывается из файла S2TestConfig.properties
 */
@Component
@PropertySource("classpath:service2/S2TestConfig.properties")
public final class S2TestDataHolder extends TestDataHolder {
    @Getter
    private final String defaultHeader1;
    @Getter
    private final String defaultHeader2;
    @Getter
    private final String defaultHeader3;
    @Getter
    private final String defaultHeader4;
    @Getter
    private final String defaultParameter;
    @Getter
    private final JsonSchemaClass requestJsonSchema;
    @Getter
    private final JsonSchemaClass responseJsonSchema;

    private final List<S2HeadersTestData> nullOrEmptyHeadersTestData = new ArrayList<>();

    public S2TestDataHolder(@Autowired S2TestCounter counter,
            @Value("${defaultHeader1}") String defaultHeader1,
                            @Value("${defaultHeader2}") String defaultHeader2,
                            @Value("${defaultHeader3}") String defaultHeader3,
                            @Value("${defaultHeader4}") String defaultHeader4,
                            @Value("${defaultParameter}") String defaultParameter,
                            @Value("classpath:service2/S2RequestJsonSchema.json") Resource requestJsonSchema,
                            @Value("classpath:service2/S2ResponseJsonSchema.json") Resource responseJsonSchema)
            throws IOException {
        super(LoggerFactory.getLogger(S2TestDataHolder.class.getSimpleName()));
        this.counter = counter;

        this.defaultHeader1 = defaultHeader1;
        this.defaultHeader2 = defaultHeader2;
        this.defaultHeader3 = defaultHeader3;
        this.defaultHeader4 = defaultHeader4;
        this.defaultParameter = defaultParameter;
        this.requestJsonSchema = new JsonSchemaClass("Service2 request",
                StreamUtils.copyToString(requestJsonSchema.getInputStream(), StandardCharsets.UTF_8));
        this.responseJsonSchema = new JsonSchemaClass("Service2 response",
                StreamUtils.copyToString(responseJsonSchema.getInputStream(), StandardCharsets.UTF_8));

        nullOrEmptyHeadersTestData.addAll(generateNullOrEmptyHeadersTestData());
    }

    public String formHeader5() {
        String strCounter = Integer.toString(counter.getValue());
        String filler = new String(new char[32 - strCounter.length() - 1]).replace("\0", "0");
        return strCounter + "a" + filler;
    }

    public Object[][] getHeadersTestData() {
        return testDataToObjectsArray(new ArrayList<Object>(nullOrEmptyHeadersTestData));
    }

    private List<S2HeadersTestData> generateNullOrEmptyHeadersTestData() {
        List<S2HeadersTestData> testData = new ArrayList<>();

        testData.add(new S2HeadersTestData(
                "null_headers",
                S2ResponseValues.NO_REQUIRED_HEADER,
                new HashMap<>() {{
                    put(S2HeaderPattern.HEADER1.getHeaderName(), null);
                    put(S2HeaderPattern.HEADER2.getHeaderName(), null);
                    put(S2HeaderPattern.HEADER3.getHeaderName(), null);
                    put(S2HeaderPattern.HEADER4.getHeaderName(), null);
                    put(S2HeaderPattern.HEADER5.getHeaderName(), null);
                }},
                true
        ));

        testData.add(new S2HeadersTestData(
                "empty_headers",
                S2ResponseValues.INVALID_HEADER,
                new HashMap<>() {{
                    put(S2HeaderPattern.HEADER1.getHeaderName(), "");
                    put(S2HeaderPattern.HEADER2.getHeaderName(), "");
                    put(S2HeaderPattern.HEADER3.getHeaderName(), "");
                    put(S2HeaderPattern.HEADER4.getHeaderName(), "");
                    put(S2HeaderPattern.HEADER5.getHeaderName(), "");
                }},
                false
        ));

        for(S2HeaderPattern header:S2HeaderPattern.values()){
            if(header == S2HeaderPattern.HEADER2){
                testData.add(generateSingleHeaderData(
                        "null_" + header.getHeaderName(),
                        S2ResponseValues.OK,
                        header.getHeaderName(),
                        null,
                        true
                ));
            } else {
                testData.add(generateSingleHeaderData(
                        "null_" + header.getHeaderName(),
                        S2ResponseValues.NO_REQUIRED_HEADER,
                        header.getHeaderName(),
                        null,
                        true
                ));
            }
        }

        return testData;
    }


    private S2HeadersTestData generateSingleHeaderData(String testName,
                                                       S2ResponseValues responseValues,
                                                       String headerName,
                                                       String header,
                                                       boolean isRequestBodyValid) {
        return new S2HeadersTestData(
                testName,
                responseValues,
                new HashMap<>() {{
                    put(headerName, header);
                }},
                isRequestBodyValid);
    }


    @Getter
    @AllArgsConstructor
    public final static class S2HeadersTestData {
        public final String testName;
        public final S2ResponseValues responseValues;
        public final Map<String, String> requestHeaders;
        public final boolean isRequestBodyValid;
    }
}

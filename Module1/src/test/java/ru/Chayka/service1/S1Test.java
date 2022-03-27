package ru.Chayka.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.Chayka.TestNGSpringStart;
import ru.Chayka.enums.TestClient;
import ru.Chayka.services.service1.S1TestDataHolder;
import ru.Chayka.services.service1.S1Tester;
import ru.Chayka.services.service1.enums.S1ResponseValues;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = TestNGSpringStart.class)
public class S1Test extends AbstractTestNGSpringContextTests {
    @Autowired
    private S1Tester tester;
    @Autowired
    private S1TestDataHolder testData;

    @Override
    @BeforeSuite
    protected void springTestContextPrepareTestInstance() throws Exception {
        super.springTestContextPrepareTestInstance();
    }

    @DataProvider
    public Object[][] dataProviderSmoke() {
        return testData.getSmokeTestData();
    }

    @DataProvider
    public Object[][] dataProviderSpecificHeaders() {
        return testData.getSpecificHeadersTestData();
    }

    @DataProvider
    public Object[][] dataProviderNullOrEmptyHeaders() {
        return testData.getNullOrEmptyHeadersTestData();
    }

    @Test(dataProvider = "dataProviderSmoke")
    public void smokeTest(String testName,
                          TestClient testClient,
                          List<String> strategyCodes) throws IOException {
        tester.positiveTest(testName, testClient, strategyCodes);
    }

    @Test(dataProvider = "dataProviderSpecificHeaders")
    public void headersAndTechKeysTest(String testName,
                                       S1ResponseValues responseValues,
                                       Map<String, String> requestHeaders,
                                       boolean isRequestBodyValid) throws IOException {
        tester.specificHeadersTest(testName, responseValues, requestHeaders, isRequestBodyValid);
    }

    @Test(dataProvider = "dataProviderNullOrEmptyHeaders")
    public void nullOrEmptyHeadersAndTechKeysTest(String testName,
                                       S1ResponseValues responseValues,
                                       Map<String, String> requestHeaders,
                                                  boolean isRequestBodyValid) throws IOException {
        tester.specificHeadersTest(testName, responseValues, requestHeaders, isRequestBodyValid);
    }
}


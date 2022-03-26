package ru.Chayka.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.Chayka.TestNGSpringStart;
import ru.Chayka.services.service2.S2TestDataHolder;
import ru.Chayka.services.service2.S2Tester;
import ru.Chayka.services.service2.enums.S2ResponseStatusValues;

import java.io.IOException;
import java.util.Map;

@SpringBootTest(classes = TestNGSpringStart.class)
public class S2Test extends AbstractTestNGSpringContextTests {
    @Autowired
    private S2Tester tester;
    @Autowired
    private S2TestDataHolder testData;

    @Override
    @BeforeSuite
    protected void springTestContextPrepareTestInstance() throws Exception {
        super.springTestContextPrepareTestInstance();
    }

    @DataProvider
    public Object[][] dataProviderHeaders() {
        return testData.getHeadersTestData();
    }

    @Test(dataProvider = "dataProviderHeaders")
    private void headersTest(String testName,
                                        S2ResponseStatusValues responseValues,
                                        Map<String, String> requestHeaders,
                                        boolean isRequestBodyValid) throws IOException{
        tester.specificHeadersTest(testName, responseValues, requestHeaders, isRequestBodyValid);
    }
}

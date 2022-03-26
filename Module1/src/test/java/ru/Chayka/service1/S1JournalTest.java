package ru.Chayka.service1;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.Chayka.TestNGSpringStart;
import ru.Chayka.services.service1.journal.S1JournalValidator;
import ru.Chayka.services.service1.journal.enums.S1JournalExpectedResults;

import java.io.IOException;

@SpringBootTest(classes = TestNGSpringStart.class)
public class S1JournalTest extends AbstractTestNGSpringContextTests {
    @Test
    public void result1Test() throws IOException {
        S1JournalValidator validator = new S1JournalValidator();
        validator.validateJournal("src/test/resources/journal/S1. Result1",
                S1JournalExpectedResults.RESULT1,
                "4b000000000000000000000000000000",
                "S1_SOAP_FL1",
                "1580999",
                "2345561256",
                "1",
                "1",
                "1");
    }

    @Test
    public void result2Test() throws IOException {
        S1JournalValidator validator = new S1JournalValidator();
        validator.validateJournal("src/test/resources/journal/S1. Result2",
                S1JournalExpectedResults.RESULT2,
                null,
                "S1_SOAP_FL4",
                null,
                "2345561256",
                null,
                null,
                null);
    }
}

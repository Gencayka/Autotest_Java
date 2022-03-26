package ru.Chayka.service2;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.Chayka.TestNGSpringStart;
import ru.Chayka.services.service2.journal.S2JournalValidator;
import ru.Chayka.services.service2.journal.enums.S2JournalExpectedResults;

import java.io.IOException;

@SpringBootTest(classes = TestNGSpringStart.class)
public class S2JournalTest extends AbstractTestNGSpringContextTests {
    @Test
    public void result1Test() throws IOException {
        S2JournalValidator validator = new S2JournalValidator();
        validator.validateJournal(
                "src/test/resources/journal/S2. Result1.json",
                S2JournalExpectedResults.RESULT1,
                "11111111",
                "ddd",
                "25523463");
    }

    @Test
    public void result2Test() throws IOException {
        S2JournalValidator validator = new S2JournalValidator();
        validator.validateJournal(
                "src/test/resources/journal/S2. Result2.json",
                S2JournalExpectedResults.RESULT2,
                "2222222",
                "ddd",
                null);
    }
}

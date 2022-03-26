package ru.Chayka.services.service2.journal;

import ru.Chayka.JournalValidator;
import ru.Chayka.journal.JournalFieldType;
import ru.Chayka.exceptions.NoSuchJournalEntryTypeException;
import ru.Chayka.services.service2.journal.enums.S2JournalEntryType;
import ru.Chayka.services.service2.journal.enums.S2JournalExpectedResults;

import java.io.File;
import java.io.IOException;
import java.util.ListIterator;

/**
 * Класс предназначен для проведения тестов по Журналированию сервиса Service2
 */
public final class S2JournalValidator
        extends JournalValidator<S2JsonJournal, S2ResponseKey, S2JournalEntryType> {
    public S2JournalValidator() {
        super("param13 value for Service2",
                "param14 value for Service2",
                "param10 value for Service2");
    }

    public void validateJournal(String jsonFilePath,
                                S2JournalExpectedResults journalExpectedResults,
                                String param16,
                                String param30,
                                String param18) throws IOException {
        S2JsonJournal journal = mapper.readValue(new File(jsonFilePath), S2JsonJournal.class);
        setEntryTypes(journal);
        checkIfHasAllEntries(journal, journalExpectedResults.getJournalEntryTypes());

        validateJournalFields(
                journal,
                param16,
                param30,
                param18);
    }

    private void validateJournalFields(S2JsonJournal journal,
                                       String param16,
                                       String param30,
                                       String param18) {
        checkBasicFields(journal);
        checkTechFields(journal);

        checkEqualStringField(JournalFieldType.PARAM16, journal, param16, true);
        checkEqualStringField(JournalFieldType.PARAM30, journal, param30, true);
        checkStringFieldByEntryType(JournalFieldType.PARAM18, journal, param18);
        checkEqualStringField(JournalFieldType.PARAM35, journal, true);
        checkIfHasStringField(JournalFieldType.PARAM34, journal, false);

        checkStringFieldByEntryType(JournalFieldType.PARAM20, journal);
        checkStringFieldByEntryType(JournalFieldType.PARAM21, journal);
        checkIfHasStringField(JournalFieldType.PARAM27, journal);
        checkIfHasIntField(JournalFieldType.PARAM26, journal);

        try {
            softAssert.assertAll();
        } catch (AssertionError assertionError) {
            logger.error(System.lineSeparator() + assertionError.getMessage());
            throw assertionError;
        }
    }

    @Override
    protected void setEntryTypes(S2JsonJournal journal) {
        ListIterator<S2ResponseKey> iterator = journal.getResponse().listIterator();
        while (iterator.hasNext()) {
            S2ResponseKey entry = iterator.next();
            try {
                entry.setEntryType(S2JournalEntryType.entryToEntryType(entry));
            } catch (NoSuchJournalEntryTypeException ex) {
                logger.error(ex.getMessage());
                iterator.remove();
            }
        }
    }
}

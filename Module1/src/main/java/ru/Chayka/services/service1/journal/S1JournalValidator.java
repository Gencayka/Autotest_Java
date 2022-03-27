package ru.Chayka.services.service1.journal;

import ru.Chayka.journal.JournalValidator;
import ru.Chayka.journal.JournalFieldType;
import ru.Chayka.exceptions.NoSuchJournalEntryTypeException;
import ru.Chayka.services.service1.journal.enums.S1JournalEntryType;
import ru.Chayka.services.service1.journal.enums.S1JournalExpectedResults;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

/**
 * Класс предназначен для проведения тестов по Журналированию сервиса Service1
 */
public final class S1JournalValidator
        extends JournalValidator<S1JsonJournal, S1ResponseKey, S1JournalEntryType> {
    public S1JournalValidator() {
        super("param13 value for Service1",
                "param14 value for Service1",
                "param10 value for Service1");
    }

    public void validateJournal(String jsonFilePath,
                                S1JournalExpectedResults journalExpectedResults,
                                String param16,
                                String param36,
                                String param18,
                                String param35,
                                String param40,
                                String param41,
                                String param42) throws IOException {
        S1JsonJournal journal = mapper.readValue(new File(jsonFilePath), S1JsonJournal.class);
        setEntryTypes(journal);
        checkIfHasAllEntries(journal, journalExpectedResults);

        validateJournalFields(
                journal,
                param16,
                param36,
                param18,
                param35,
                param40,
                param41,
                param42);
    }

    public void validateJournalFields(S1JsonJournal journal,
                                      String param16,
                                      String param36,
                                      String param18,
                                      String param35,
                                      String param40,
                                      String param41,
                                      String param42) {
        checkBasicFields(journal);
        checkTechFields(journal);

        checkEqualStringField(JournalFieldType.PARAM16, journal, param16, true);
        checkEqualStringField(JournalFieldType.PARAM36, journal, param36, true);
        checkStringFieldByEntryType(JournalFieldType.PARAM18, journal, param18);
        checkStringFieldByEntryType(JournalFieldType.PARAM35, journal, param35);
        checkIfHasStringField(JournalFieldType.PARAM34, journal, false);
        checkIfHasStringField(JournalFieldType.PARAM38, journal, false);
        checkIfHasStringField(JournalFieldType.PARAM39, journal, false);

        checkStringFieldByEntryType(JournalFieldType.PARAM20, journal);
        checkStringFieldByEntryType(JournalFieldType.PARAM21, journal);
        checkIfHasStringField(JournalFieldType.PARAM27, journal);
        checkIfHasIntField(JournalFieldType.PARAM26, journal);
        checkStringFieldByEntryType(JournalFieldType.PARAM40, journal, param40);
        checkStringFieldByEntryType(JournalFieldType.PARAM41, journal, param41);
        checkStringFieldByEntryType(JournalFieldType.PARAM42, journal, param42);

        try {
            softAssert.assertAll();
        } catch (AssertionError assertionError) {
            logger.error(System.lineSeparator() + assertionError.getMessage());
            throw assertionError;
        }
    }

    @Override
    protected void setEntryTypes(S1JsonJournal journal) {
        ListIterator<S1ResponseKey> iterator = journal.getResponse().listIterator();
        while (iterator.hasNext()) {
            S1ResponseKey entry = iterator.next();
            try {
                entry.setEntryType(S1JournalEntryType.entryToEntryType(entry));
            } catch (NoSuchJournalEntryTypeException ex) {
                iterator.remove();
            }
        }
    }

    protected void checkIfHasAllEntries(S1JsonJournal journal,
                                        S1JournalExpectedResults journalExpectedResults) {
        List<S1JournalEntryType> expectedEntriesTypes = journalExpectedResults.getJournalEntryTypes();
        checkIfHasAllEntries(journal, expectedEntriesTypes);
    }
}

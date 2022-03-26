package ru.Chayka.services.service1.journal.enums;

import lombok.Getter;
import ru.Chayka.services.service1.enums.S1ResponseStatusValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enum содержит данные о типах записей, которые должны содержаться в Журнале сервиса
 * Service1 при разных тест-кейсах
 */
@Getter
public enum S1JournalExpectedResults {
    RESULT1(S1ResponseStatusValues.OK,
            new S1JournalEntryType[]{
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_1,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_2,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_4,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_5,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_8,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_9}),

    RESULT2(S1ResponseStatusValues.INVALID_HEADER,
            new S1JournalEntryType[]{
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_1,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_2,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_3,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_3,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_5,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_8,
                    S1JournalEntryType.JOURNAL_ENTRY_TYPE_9});

    private final S1ResponseStatusValues responseValues;
    private final List<S1JournalEntryType> journalEntryTypes;

    S1JournalExpectedResults(S1ResponseStatusValues responseValues,
                             S1JournalEntryType[] journalEntryTypes) {
        this.responseValues = responseValues;
        this.journalEntryTypes = new ArrayList<>(Arrays.asList(journalEntryTypes));
    }
}

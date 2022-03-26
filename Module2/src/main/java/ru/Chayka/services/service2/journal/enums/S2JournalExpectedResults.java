package ru.Chayka.services.service2.journal.enums;

import lombok.Getter;
import ru.Chayka.services.service2.enums.S2ResponseStatusValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enum содержит данные о типах записей, которые должны содержаться в Журнале сервиса
 * Service2 при разных тест-кейсах
 */
@Getter
public enum S2JournalExpectedResults {
    RESULT1(S2ResponseStatusValues.OK,
            new S2JournalEntryType[]{
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_1,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_2,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_4,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_12,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_15}),

    RESULT2(S2ResponseStatusValues.INVALID_HEADER,
            new S2JournalEntryType[]{
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_1,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_2,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_7,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_14,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_15}),

    RESULT3(S2ResponseStatusValues.INVALID_BODY,
            new S2JournalEntryType[]{
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_1,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_2,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_5,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_14,
                    S2JournalEntryType.JOURNAL_ENTRY_TYPE_15});

    private final S2ResponseStatusValues responseValues;
    private final List<S2JournalEntryType> journalEntryTypes;

    S2JournalExpectedResults(S2ResponseStatusValues responseValues, S2JournalEntryType[] journalEntryTypes) {
        this.responseValues = responseValues;
        this.journalEntryTypes = new ArrayList<>(Arrays.asList(journalEntryTypes));
    }
}

package ru.Chayka.services.service2.journal.enums;

import lombok.AllArgsConstructor;
import ru.Chayka.journal.JournalEntryType;
import ru.Chayka.journal.JournalFieldType;
import ru.Chayka.exceptions.NoSuchJournalEntryTypeException;
import ru.Chayka.exceptions.NoSuchJournalFieldTypeInfoException;
import ru.Chayka.services.service2.journal.S2ResponseKey;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Enum содержит данные о типах записей Журнала сервиса Service2
 */
@AllArgsConstructor
public enum S2JournalEntryType implements JournalEntryType {
    JOURNAL_ENTRY_TYPE_1("param2 value1", "param1 value1",
            "^param15 pattern1$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, false),
    JOURNAL_ENTRY_TYPE_2("param2 value1", "param1 value2",
            "^param15 pattern2$",
            "param20 value1", "param21 value1", false, false,
            false, true, false,
            false, false),
    JOURNAL_ENTRY_TYPE_3("param2 value2", "param1 value1",
            "^param15 pattern3$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, true),
    JOURNAL_ENTRY_TYPE_4("param2 value1", "param1 value1",
            "^param15 pattern4$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, false),
    JOURNAL_ENTRY_TYPE_5("param2 value2", "param1 value1",
            "^param15 pattern5$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, true),
    JOURNAL_ENTRY_TYPE_6("param2 value2", "param1 value1",
            "^param15 pattern6$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, true),
    JOURNAL_ENTRY_TYPE_7("param2 value2", "param1 value1",
            "^param15 pattern7$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, true),
    JOURNAL_ENTRY_TYPE_8("param2 value1", "param1 value1",
            "^param15 pattern8$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, false),
    JOURNAL_ENTRY_TYPE_9("param2 value2", "param1 value1",
            "^param15 pattern9$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, true),
    JOURNAL_ENTRY_TYPE_10("param2 value2", "param1 value1",
            "^param15 pattern10$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            true, true),
    JOURNAL_ENTRY_TYPE_11("param2 value2", "param1 value1",
            "^param15 pattern11$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, true),
    JOURNAL_ENTRY_TYPE_12("param2 value4", "param1 value2",
            "^param15 pattern12$",
            "param20 value1", "param21 value1", true, true,
            true, true, true,
            false, false),
    JOURNAL_ENTRY_TYPE_13("param2 value3", "param1 value2",
            "^param15 pattern13$",
            "param20 value1", "param21 value1", true, true,
            true, true, true,
            false, false),
    JOURNAL_ENTRY_TYPE_14("param2 value2", "param1 value2",
            "^param15 pattern14$",
            "param20 value1", "param21 value1", true, true,
            true, true, true,
            false, false),
    JOURNAL_ENTRY_TYPE_15("param2 value1", "param1 value1",
            "^param15 pattern15$",
            "param20 value1", "param21 value1", false, false,
            false, false, false,
            false, false);

    private final String param2;
    private final String param1;
    private final String param15;
    private final String param20;
    private final String param21;
    private final boolean hasParam24;
    private final boolean hasParam25;
    private final boolean hasParam26;
    private final boolean hasParam22;
    private final boolean hasParam23;
    private final boolean hasParam18;
    private final boolean hasParam27;

    public static S2JournalEntryType entryToEntryType(S2ResponseKey entry) {
        for (S2JournalEntryType journalEntryType : S2JournalEntryType.values()) {
            if (Pattern.matches(journalEntryType.param15, entry.getParam15())
                    && Objects.equals(entry.getParam1(), journalEntryType.param1)) {
                return journalEntryType;
            }
        }

        throw new NoSuchJournalEntryTypeException(String.format(
                "Unknown Service2 journal entry type: %s (%s)",
                entry.getNonNullParam15(), entry.getNonNullParam1()));
    }

    @Override
    public String getFieldValueByFieldType(JournalFieldType fieldType)
            throws NoSuchJournalFieldTypeInfoException {
        switch (fieldType) {
            case PARAM2:
                return param2;
            case PARAM1:
                return param1;
            case PARAM15:
                return param15;
            case PARAM20:
                return param20;
            case PARAM21:
                return param21;
            default:
                throw new NoSuchJournalFieldTypeInfoException(
                        "Unexpected field type " + fieldType.getFieldName());
        }
    }

    @Override
    public boolean getFieldBoolInfoByFieldType(JournalFieldType fieldType)
            throws NoSuchJournalFieldTypeInfoException {
        switch (fieldType) {
            case PARAM24:
                return hasParam24;
            case PARAM25:
                return hasParam25;
            case PARAM26:
                return hasParam26;
            case PARAM22:
                return hasParam22;
            case PARAM23:
                return hasParam23;
            case PARAM18:
                return hasParam18;
            case PARAM27:
                return hasParam27;
            default:
                throw new NoSuchJournalFieldTypeInfoException(
                        "Unexpected field type " + fieldType.getFieldName());
        }
    }
}
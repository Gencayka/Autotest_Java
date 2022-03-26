package ru.Chayka.journal;

/**
 * Интерфейс для Enum, в которых хранятся данные о типах записей Журнала
 * <br>Для каждого сервиса используется отдельный Enum, реализущий данный интерфейс
 */
public interface JournalEntryType {
    String getFieldValueByFieldType(JournalFieldType fieldType);
    boolean getFieldBoolInfoByFieldType(JournalFieldType fieldType);
}

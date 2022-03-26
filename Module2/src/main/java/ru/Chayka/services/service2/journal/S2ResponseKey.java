package ru.Chayka.services.service2.journal;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.Chayka.journal.JournalFieldType;
import ru.Chayka.exceptions.NoSuchJournalFieldTypeException;
import ru.Chayka.journal.ResponseKey;

/**
 * Класс предназначен для десериализации и хранения отдельных записей Журнала сервиса Service2
 */
public final class S2ResponseKey extends ResponseKey {
    @JsonProperty("jsonParam43Name")
    private String param43;
    @JsonProperty("jsonParam44Name")
    private String param44;

    @Override
    public String getStringField(JournalFieldType fieldType)
            throws NoSuchJournalFieldTypeException{
        try {
            return super.getStringField(fieldType);
        } catch (NoSuchJournalFieldTypeException exception){
            switch (fieldType){
                case PARAM43: return param43;
                case PARAM44: return param44;
                default: throw exception;
            }
        }
    }
}

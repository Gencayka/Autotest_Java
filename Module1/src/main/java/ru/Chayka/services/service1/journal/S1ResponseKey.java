package ru.Chayka.services.service1.journal;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.Chayka.journal.JournalFieldType;
import ru.Chayka.exceptions.NoSuchJournalFieldTypeException;
import ru.Chayka.journal.ResponseKey;

/**
 * Класс предназначен для десериализации и хранения отдельных записей Журнала сервиса Service1
 */
public final class S1ResponseKey extends ResponseKey {
    @JsonProperty("jsonParam36Name")
    protected String param36;
    protected String param37;
    private String param38;
    private String param39;
    private String param40;
    private String param41;
    private String param42;

    @Override
    public String getStringField(JournalFieldType fieldType)
            throws NoSuchJournalFieldTypeException{
        try {
            return super.getStringField(fieldType);
        } catch (NoSuchJournalFieldTypeException exception){
            switch (fieldType){
                case PARAM36: return param36;
                case PARAM37: return param37;
                case PARAM38: return param38;
                case PARAM39: return param39;
                case PARAM40: return param40;
                case PARAM41: return param41;
                case PARAM42: return param42;
                default: throw exception;
            }
        }
    }
}

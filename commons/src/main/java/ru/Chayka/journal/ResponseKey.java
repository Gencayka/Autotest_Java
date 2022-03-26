package ru.Chayka.journal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.Chayka.exceptions.NoSuchJournalFieldTypeException;

import java.util.Objects;

/**
 * Класс предназначен для десериализации и хранения отдельных записей Журнала
 * <br>Абстрактный класс, для каждого сервиса используется отдельный класс-наследник
 * <br>Класс содержит общие для всех сервисов и модулей параметры записей Журнала, в классах-наследниках могут
 * добавляться дополнительные параметры
 */
@Getter
@Setter
public abstract class ResponseKey {
    protected JournalEntryType entryType;

    protected String param1;
    protected String param2;
    protected Integer param3;
    protected String param4;
    protected String param5;
    protected String param6;
    protected String param7;
    protected String param8;
    protected String param9;
    protected String param10;
    protected String param11;
    protected Long param12;
    @JsonProperty("jsonParam13Name")
    protected String param13;
    protected String param14;
    protected String param15;
    protected String param16;
    protected Integer param17;
    @JsonProperty("jsonParam18Name")
    protected String param18;
    @JsonProperty("jsonParam19Name")
    protected String param19;
    @JsonProperty("jsonParam20Name")
    protected String param20;
    @JsonProperty("jsonParam21Name")
    protected String param21;
    @JsonProperty("jsonParam22Name")
    protected String param22;
    @JsonProperty("jsonParam23Name")
    protected String param23;
    @JsonProperty("jsonParam24Name")
    protected Integer param24;
    @JsonProperty("jsonParam25Name")
    protected Integer param25;
    @JsonProperty("jsonParam26Name")
    protected Integer param26;
    protected String param27;
    protected String param28;
    protected String param29;
    protected String param30;
    protected String param31;
    protected String param32;
    protected String param33;
    protected String param34;
    protected String param35;

    public String getStringField(JournalFieldType fieldType)
            throws NoSuchJournalFieldTypeException{
        switch (fieldType){
            case PARAM1: return param1;
            case PARAM2: return param2;
            case PARAM4: return param4;
            case PARAM5: return param5;
            case PARAM6: return param6;
            case PARAM7: return param7;
            case PARAM8: return param8;
            case PARAM9: return param9;
            case PARAM10: return param10;
            case PARAM11: return param11;
            case PARAM13: return param13;
            case PARAM14: return param14;
            case PARAM15: return param15;
            case PARAM16: return param16;
            case PARAM18: return param18;
            case PARAM19: return param19;
            case PARAM20: return param20;
            case PARAM21: return param21;
            case PARAM22: return param22;
            case PARAM23: return param23;
            case PARAM27: return param27;
            case PARAM28: return param28;
            case PARAM29: return param29;
            case PARAM30: return param30;
            case PARAM31: return param31;
            case PARAM32: return param32;
            case PARAM33: return param33;
            case PARAM34: return param34;
            case PARAM35: return param35;
            default: throw new NoSuchJournalFieldTypeException("Unknown String field type " + fieldType.getFieldName());
        }
    }

    public Integer getIntField(JournalFieldType fieldType){
        switch (fieldType){
            case PARAM3: return param3;
            case PARAM17: return param17;
            case PARAM24: return param24;
            case PARAM25: return param25;
            case PARAM26: return param26;
            default: throw new NoSuchJournalFieldTypeException("Unknown Integer field type " + fieldType.getFieldName());
        }
    }

    public Long getLongField(JournalFieldType fieldType){
        switch (fieldType){
            case PARAM12: return param12;
            default: throw new NoSuchJournalFieldTypeException("Unknown Long field type " + fieldType.getFieldName());
        }
    }

    public String getNonNullParam1(){
        return Objects.requireNonNullElse(param1, "NULLPARAM1");
    }

    public String getNonNullParam15(){
        return Objects.requireNonNullElse(param15, "NULLPARAM15");
    }
}

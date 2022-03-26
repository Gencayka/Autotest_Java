package ru.Chayka.journal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum содержит названия всех параметров записи Журнала во всех модулях и сервисах
 */
@Getter
@AllArgsConstructor
public enum JournalFieldType {
    PARAM1("param1"),
    PARAM2("param2"),
    PARAM3("param3"),
    PARAM4("param4"),
    PARAM5("param5"),
    PARAM6("param6"),
    PARAM7("param7"),
    PARAM8("param8"),
    PARAM9("param9"),
    PARAM10("param10"),
    PARAM11("param11"),
    PARAM12("param12"),
    PARAM13("jsonParam13Name"),
    PARAM14("param14"),
    PARAM15("param15"),
    PARAM16("param16"),
    PARAM17("param17"),
    PARAM18("jsonParam18Name"),
    PARAM19("jsonParam19Name"),
    PARAM20("jsonParam20Name"),
    PARAM21("jsonParam21Name"),
    PARAM22("jsonParam22Name"),
    PARAM23("jsonParam23Name"),
    PARAM24("jsonParam24Name"),
    PARAM25("jsonParam25Name"),
    PARAM26("jsonParam26Name"),
    PARAM27("param27"),
    PARAM28("param28"),
    PARAM29("param29"),
    PARAM30("param30"),
    PARAM31("param31"),
    PARAM32("param32"),
    PARAM33("param33"),
    PARAM34("param34"),
    PARAM35("param35"),

    //Service1
    PARAM36("jsonParam36Name"),
    PARAM37("param37"),
    PARAM38("param38"),
    PARAM39("param39"),
    PARAM40("param40"),
    PARAM41("param41"),
    PARAM42("param42"),

    //Service2
    PARAM43("jsonParam43Name"),
    PARAM44("jsonParam44Name");

    private final String fieldName;
}

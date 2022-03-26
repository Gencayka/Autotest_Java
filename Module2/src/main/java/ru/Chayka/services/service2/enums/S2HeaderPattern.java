package ru.Chayka.services.service2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum содержит паттерны, по которым валидируются заголовки запроса на сервис Service2
 */
@AllArgsConstructor
@Getter
public enum S2HeaderPattern {
    HEADER1("header1","^.*\\S+.*$"),
    HEADER2("header2","^.*\\S+.*$"),
    HEADER3("header3","^.*\\S+.*$"),
    HEADER4("header4","^.*\\S+.*$"),
    HEADER5("header5","^.*\\S+.*$"),
    HEADER6("header6","^.*\\S+.*$"),
    HEADER7("header7","^.*\\S+.*$");

    private final String headerName;
    private final String pattern;
}

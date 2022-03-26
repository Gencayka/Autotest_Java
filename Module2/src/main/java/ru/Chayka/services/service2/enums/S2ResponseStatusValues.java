package ru.Chayka.services.service2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.Chayka.ResponseStatusValues;

/**
 * Enum содержит коды и описания статусов ответов сервиса Service2 при разных тест-кейсах
 */
@Getter
@AllArgsConstructor
public enum S2ResponseStatusValues implements ResponseStatusValues {
    OK(200,0, "Ok"),
    INVALID_HEADER(200,1, "Invalid header"),
    NO_REQUIRED_HEADER(200,2, "No required header"),
    INVALID_BODY(200,3, "Invalid body");

    private final Integer httpCode;
    private final Integer statusCode;
    private final String statusDesc;
}

package ru.Chayka.services.service2.rest.requestbody;

import lombok.Builder;
import lombok.Value;
import ru.Chayka.services.service2.rest.requestbody.keys.S2Key1;

/**
 * Класс предназначен для создания тела запроса на сервис Service2
 */
@Value
@Builder
public class S2RequestBody {
    S2Key1 s2Key1;
}

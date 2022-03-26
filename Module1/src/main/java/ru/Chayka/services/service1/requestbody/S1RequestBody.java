package ru.Chayka.services.service1.requestbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import ru.Chayka.services.service1.requestbody.keys.S1Key1;

/**
 * Класс предназначен для создания тела запроса на сервис Service1
 */
@Value
@Builder
@AllArgsConstructor
public class S1RequestBody {
    S1Key1 s1Key1;
}

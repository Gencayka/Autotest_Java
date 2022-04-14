package ru.Chayka.services.service1.rest;

import org.springframework.stereotype.Component;
import ru.Chayka.rest.TestCounter;

/**
 * Класс-счетчик, значение которого увеличивается при запуске следующего теста для сервиса Service1
 * и используется для формирования header5 запроса на сервис
 */
@Component
public final class S1TestCounter extends TestCounter {
}

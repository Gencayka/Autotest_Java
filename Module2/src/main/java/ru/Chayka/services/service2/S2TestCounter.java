package ru.Chayka.services.service2;

import org.springframework.stereotype.Component;
import ru.Chayka.TestCounter;

/**
 * Класс-счетчик, значение которого увеличивается при запуске следующего теста для сервиса Service2
 * и используется для формирования rqUid запроса на сервис
 */
@Component
public final class S2TestCounter extends TestCounter {
}

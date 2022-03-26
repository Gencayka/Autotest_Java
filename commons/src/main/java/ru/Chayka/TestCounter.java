package ru.Chayka;

import lombok.Getter;

/**
 * Класс-счетчик, значение которого увеличивается при запуске следующего теста для данного сервиса
 * и используется для формирования param5 запроса на сервис
 * <br>Абстрактный класс, для каждого сервиса используется отдельный класс-наследник
 */
@Getter
public abstract class TestCounter {
    private int value = 1;
    public void increase(){
        value++;
    }
}

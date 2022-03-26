package ru.Chayka.journal;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Класс предназначен для десериализации и хранения списка записей Журнала по определенному тест-кейсу
 * <br>Абстрактный класс, для каждого сервиса используется отдельный класс-наследник
 * @param <RK> Класс для хранения отдельных записей Журнала, наследуется от ResponseKey
 */
@Getter
@Setter
public abstract class AbstractJsonJournal<RK extends ResponseKey> {
    private List<RK> response;
}

package ru.Chayka.restrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс предназначен для хранения схем валидации JSON-тел запросов и ответов
 */
@Getter
@AllArgsConstructor
public class JsonSchemaClass {
    private final String name;
    private final String text;
}

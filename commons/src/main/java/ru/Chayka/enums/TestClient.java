package ru.Chayka.enums;

import lombok.Getter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Enum содержит информацию о тестовых клиентах
 */
@Getter
public enum TestClient {
    NATASHA("Наталья",
            "Иванова",
            "Сергеевна",
            "1992-10-25",
            "11 11",
            "222222",
            "2021-08-30"),
    DIMA("Дмитрий",
            "Дмух",
            "Иванович",
            "1988-03-19",
            "22 22",
            "222222",
            "2021-08-30"),
    IVAN("Иван",
            "Кавченко",
            "Анатольевич",
            "1986-08-25",
            "33 33",
            "333333",
            "2022-01-18");

    private final String firstName;
    private final String lastName;
    private final String middleName;

    private final Date birthDateAsDate;
    private final String birthDateAsString;

    private final String identityCardSeries;
    private final String identityCardNumber;
    private final String identityCardIssueDate;

    TestClient(String firstName,
               String lastName,
               String middleName,
               String birthDate,
               String identityCardSeries,
               String identityCardNumber,
               String identityCardIssueDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDateAsString = birthDate;
        this.identityCardSeries = identityCardSeries;
        this.identityCardNumber = identityCardNumber;
        this.identityCardIssueDate = identityCardIssueDate;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.birthDateAsDate = new Date((dateFormat.parse(birthDate)).getTime());
        } catch (ParseException ex) {
            throw new ExceptionInInitializerError();
        }
    }

    public String toString() {
        return String.format("%s %s %s", lastName, firstName, middleName);
    }
}

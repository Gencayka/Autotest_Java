package ru.Chayka.services.service1.requestbody.keys;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.Chayka.services.service1.requestbody.builders.ClientS1KeyBuilder;

import java.sql.Date;

@Value
@AllArgsConstructor
public class ClientS1Key {
    String firstName;
    String lastName;
    String middleName;
    String birthDate;

    public static ClientS1KeyBuilder builder(){
        return new ClientS1KeyBuilder();
    }
}

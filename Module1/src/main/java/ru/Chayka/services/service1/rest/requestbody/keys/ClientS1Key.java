package ru.Chayka.services.service1.rest.requestbody.keys;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.Chayka.services.service1.rest.requestbody.builders.ClientS1KeyBuilder;

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

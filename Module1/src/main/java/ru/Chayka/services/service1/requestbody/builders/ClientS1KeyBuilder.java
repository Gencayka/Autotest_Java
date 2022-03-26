package ru.Chayka.services.service1.requestbody.builders;

import ru.Chayka.services.service1.requestbody.keys.ClientS1Key;
import ru.Chayka.enums.TestClient;

public final class ClientS1KeyBuilder {
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthDate;

    public ClientS1Key build(){
        return new ClientS1Key(firstName, lastName, middleName, birthDate);
    }

    public ClientS1Key buildByClient(TestClient client){
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.middleName = client.getMiddleName();
        this.birthDate = client.getBirthDateAsString();
        return build();
    }
}


package ru.Chayka.services.service1.requestbody.keys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class S1Key1 {
    ClientS1Key clientS1Key;
    @Singular("parameter")
    List<String> parameter;
}

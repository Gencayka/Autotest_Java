package ru.Chayka.services.service2.requestbody.keys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class S2Key1 {
    S2Key2 s2Key2;
}

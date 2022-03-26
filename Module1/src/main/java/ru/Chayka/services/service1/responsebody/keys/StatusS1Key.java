package ru.Chayka.services.service1.responsebody.keys;

import lombok.Getter;
import lombok.Setter;
import ru.Chayka.ResponseBody;

@Getter
@Setter
public final class StatusS1Key implements ResponseBody {
    private Integer statusCode;
    private String statusDesc;
}

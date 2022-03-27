package ru.Chayka.services.service1.responsebody.keys;

import lombok.Getter;
import lombok.Setter;
import ru.Chayka.restrequest.ResponseBody;

@Getter
@Setter
public final class StatusS1Key implements ResponseBody {
    private Integer statusCode;
    private String statusDesc;
}

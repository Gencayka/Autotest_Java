package ru.Chayka.services.service2.responsebody;

import lombok.Getter;
import lombok.Setter;
import ru.Chayka.ResponseBody;
import ru.Chayka.services.service2.responsebody.keys.S2Key1;
import ru.Chayka.services.service2.responsebody.keys.StatusS2Key;

/**
 * Класс предназначен для десериализации и хранения тела ответа сервиса Service2
 */
@Getter
@Setter
public final class S2ResponseBody implements ResponseBody {
    private StatusS2Key status;
    private S2Key1 s2Key1;

    @Override
    public Integer getStatusCode() {
        if(status == null){
            return null;
        } else {
            return status.getStatusCode();
        }
    }

    @Override
    public String getStatusDesc() {
        if(status == null){
            return null;
        } else {
            return status.getStatusDesc();
        }
    }
}

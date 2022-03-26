package ru.Chayka.services.service1.responsebody;

import lombok.Getter;
import lombok.Setter;
import ru.Chayka.ResponseBody;
import ru.Chayka.services.service1.responsebody.keys.S2Key1;
import ru.Chayka.services.service1.responsebody.keys.StatusS1Key;

/**
 * Класс предназначен для десериализации и хранения тела ответа сервиса Service1
 */
@Getter
@Setter
public final class S1ResponseBody implements ResponseBody {
    private StatusS1Key status;
    private S2Key1 investmentInfo;

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

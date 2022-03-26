package ru.Chayka;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Синглтон, предназначен для хранения данных об эндпоинтах тестируемых сервисов из файла restConfig.properties
 */
@Getter
@Component
@Scope("singleton")
@PropertySource("classpath:restConfig.properties")
public final class RestConfig {

    private final String baseUri;
    private final int port;
    private final String service2BasePath;

    @Getter
    private static RestConfig uniqueInstance;

    @Autowired
    private void setUniqueInstance(RestConfig uniqueInstance){
        RestConfig.uniqueInstance = uniqueInstance;
    }

    private RestConfig(@Value("${baseUri}") String baseUri,
                       @Value("${port}") int port,
                       @Value("${service2BasePath}") String service2BasePath){
        this.baseUri = baseUri;
        this.port = port;
        this.service2BasePath = service2BasePath;
    }
}

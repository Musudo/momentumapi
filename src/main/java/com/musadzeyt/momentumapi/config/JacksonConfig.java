package com.musadzeyt.momentumapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.musadzeyt.momentumapi.enums.stat.StackOrderEnum;
import com.musadzeyt.momentumapi.enums.stat.TrendEnum;
import com.musadzeyt.momentumapi.util.CustomLocalDateTimeDeserializer;
import com.musadzeyt.momentumapi.util.CustomLocalDateTimeSerializer;
import com.musadzeyt.momentumapi.util.StackOrderEnumSerializer;
import com.musadzeyt.momentumapi.util.TrendEnumSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Slf4j
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule()); // Java Time support

        // Register custom serializers and deserializers
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        module.addSerializer(TrendEnum.class, new TrendEnumSerializer());
        module.addSerializer(StackOrderEnum.class, new StackOrderEnumSerializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}

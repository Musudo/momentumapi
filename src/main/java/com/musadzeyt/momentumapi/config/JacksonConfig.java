package com.musadzeyt.momentumapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.musadzeyt.momentumapi.enums.TagName;
import com.musadzeyt.momentumapi.enums.statEnums.ColumnName;
import com.musadzeyt.momentumapi.enums.statEnums.StackOrder;
import com.musadzeyt.momentumapi.enums.statEnums.Trend;
import com.musadzeyt.momentumapi.util.customSerializer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Java Time support

        // Register custom serializers and deserializers
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        module.addSerializer(Trend.class, new TrendEnumSerializer());
        module.addSerializer(StackOrder.class, new StackOrderEnumSerializer());
        module.addSerializer(ColumnName.class, new ColumnEnumSerializer());
        module.addSerializer(TagName.class, new TagNameEnumSerializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}

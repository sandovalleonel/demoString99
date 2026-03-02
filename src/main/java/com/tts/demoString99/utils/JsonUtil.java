package com.tts.demoString99.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode readJson(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            return objectMapper.readTree(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

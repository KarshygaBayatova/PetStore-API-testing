package com.petstore.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIUtils {

    public static Map<String, Map<String, Object>> readUserFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, Object>> userDataMap = new HashMap<>();

        try {
            List<Map<String, Object>> users = objectMapper.readValue(
                    new File(filePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class)
            );

            for (Map<String, Object> user : users) {
                String username = (String) user.get("username");
                if (username != null) {
                    userDataMap.put(username, user);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file: " + e.getMessage());
        }

        return userDataMap;
    }

    public static Map<String, Object> readOrderFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> orderDataMap = null;

        try {
            // Read the JSON file and deserialize it into a Map
            orderDataMap = objectMapper.readValue(new File(filePath), Map.class);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file: " + e.getMessage());
        }

        return orderDataMap;
    }

//    public static Map<String, Object> readOrderFromJson(String filePath) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule()); // Register the module to handle OffsetDateTime
//
//        Map<String, Object> orderDataMap = null;
//
//        try {
//            // Read the JSON file and deserialize it into a Map
//            orderDataMap = objectMapper.readValue(new File(filePath), Map.class);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error reading file: " + e.getMessage());
//        }
//
//        return orderDataMap;
//    }

//    public static Map<String, Object> readOrderFromJson(String filePath) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        Map<String, Object> orderDataMap = null;
//
//        try {
//            // Read the JSON file and deserialize it into a Map
//            orderDataMap = objectMapper.readValue(new File(filePath), Map.class);
//
//            // Manually parse the shipDate field if needed
//            if (orderDataMap.containsKey("shipDate")) {
//                String shipDateStr = (String) orderDataMap.get("shipDate");
//                OffsetDateTime shipDate = OffsetDateTime.parse(shipDateStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
//                orderDataMap.put("shipDate", shipDate);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error reading file: " + e.getMessage());
//        }
//
//        return orderDataMap;
//    }
}

package com.example.driveScan.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SearchData {

    @Bean
    @Qualifier("SearchStr")
    public Map<String, Integer> searchStrings() {
        Map<String, Integer> searchStr = new HashMap<>();
        searchStr.put("Define error from Avios Create Token as: UNAUTHORIZED_CLIENT", 0);
        searchStr.put("Define error from Avios Create Token as: GENERAL_FAIL", 0);
        searchStr.put("Define error from Avios Create Token as: INVALID_REQUEST", 0);
        searchStr.put("Profile can't be found by name 'null' or membership id", 0);
        searchStr.put("Create auth token to user:", 0);
        searchStr.put("Read Avios Account operation failed for user", 0);
        searchStr.put("Login in by avios username: (null)", 0);
//
//        searchStr.put("Already logged into HSM", 0);
//        searchStr.put("Profile can't be found by name", 0);
//
//        searchStr.put("No persistent token found for series id", 0);
//        searchStr.put("ProfileServiceImpl", 0);
//
//        searchStr.put("Avios error string: {\"code\":\"SYSTEM_UNAVAILABLE\"", 0);
//        searchStr.put("No username found matching (null)", 0);
        return searchStr;
    }
}

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
        searchStr.put("Read timed out executing PUT", 0);
        searchStr.put("Validation exception occurred", 0);
//        searchStr.put("MissedFieldException: FlightSegment is missed for RPH '0'", 0);
        return searchStr;
    }
}

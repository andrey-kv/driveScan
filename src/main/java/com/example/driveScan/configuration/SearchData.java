package com.example.driveScan.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Profile("default")
public class SearchData {

    @Value("${key.strings}")
    private List<String> keyStrings;

    @Bean
    @Qualifier("SearchStr")
    public Map<String, Integer> searchStrings() {
        Map<String, Integer> searchStr = new HashMap<>();
        for (String key : keyStrings) {
            searchStr.put(key,0);
        }
        return searchStr;
    }
}

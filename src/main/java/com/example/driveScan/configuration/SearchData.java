package com.example.driveScan.configuration;

import com.example.driveScan.searchers.Searcher;
import com.example.driveScan.searchers.SearcherFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SearchData {

    @Value("${key.strings}")
    private List<String> keyStrings;

    @Autowired
    private SearcherFactory searcherFactory;

    @Bean
    @Qualifier("SearchStr")
    public Map<String, Integer> searchStrings() {
        Map<String, Integer> searchStr = new HashMap<>();
        for (String key : keyStrings) {
            searchStr.put(key,0);
        }
        return searchStr;
    }

    @Bean
    @Qualifier("searcherBean")
    public Searcher getSearcher() throws Exception {
        return searcherFactory.getObject();
    }
}

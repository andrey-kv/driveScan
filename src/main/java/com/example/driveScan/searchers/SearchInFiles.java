package com.example.driveScan.searchers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
@Slf4j
public class SearchInFiles implements Searcher {
    @Override
    public void scan(String startFolder) {
        log.info("SearchInFiles: " + startFolder);
    }

    @Override
    public void display() {

    }
}

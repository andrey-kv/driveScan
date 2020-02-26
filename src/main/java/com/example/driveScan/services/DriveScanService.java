package com.example.driveScan.services;

import com.example.driveScan.searchers.Searcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DriveScanService {

    @Autowired
    @Qualifier("searcherBean")
    private Searcher searcher;

    public void scanAll(String startFolder) {
        log.info(searcher.type());
        log.info("Start folder = " + startFolder);
        log.info("Scan ======================================");
        searcher.scan(startFolder);

        log.info("Display ===================================");
        searcher.display();
    }

}

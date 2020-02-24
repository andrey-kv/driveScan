package com.example.driveScan.services;

import com.example.driveScan.data.FileEntry;
import com.example.driveScan.repositories.FileEntryRepository;
import com.example.driveScan.searchers.Searcher;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

@Service
@Slf4j
public class DriveScanService {

    @Autowired
    private Searcher searcher;

    public void scanAll(String startFolder) {
        log.info("Start folder = " + startFolder);
        log.info("= Scan ======================================");
        searcher.scan(startFolder);

        log.info("= Display ===================================");
        searcher.display();
    }

}

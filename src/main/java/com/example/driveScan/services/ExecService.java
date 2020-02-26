package com.example.driveScan.services;

import com.example.driveScan.repositories.FileEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExecService {

    @Autowired
    private FileEntryRepository fileEntryRepository;

    @Autowired
    private DriveScanService driveScanService;

    @Autowired
    private Environment environment;

    @EventListener(ApplicationReadyEvent.class)
    private void exec() {
        log.info("Execution =================================");

        String startFolder = this.environment.getProperty("start.folder");
        driveScanService.scanAll(startFolder);
    }
}

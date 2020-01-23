package com.example.driveScan.services;

import com.example.driveScan.data.FileEntry;
import com.example.driveScan.repositories.FileEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExecService {

    @Autowired
    private FileEntryRepository fileEntryRepository;

    @EventListener(ApplicationReadyEvent.class)
    private void exec() {
        log.info("============ Execution ============");

        FileEntry entry = FileEntry.builder()
                .fullName("C:/Work/OAL/Documents/OAL process.md")
                .build();

        fileEntryRepository.save(entry);
    }
}

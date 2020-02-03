package com.example.driveScan.services;

import com.example.driveScan.data.FileEntry;
import com.example.driveScan.repositories.FileEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigInteger;

@Service
@Slf4j
public class DriveScanService {

    @Autowired
    private FileEntryRepository fileEntryRepository;

    public void scan(String startFolder) {
        File item = new File(startFolder);
        File[] files = item.listFiles();
        for (File f : files) {
            display(f);
        }
    }

    private void display(File f) {
        if (f.isDirectory()) {
            log.info("Reading folder: " + f.getAbsolutePath());
            scan(f.getAbsolutePath());
        } else {
            log.info(f.getAbsolutePath());
            addFileToDatabase(f);
        }
    }

    private void addFileToDatabase(File f) {
        FileEntry entry = FileEntry.builder()
             .fullName(f.getAbsolutePath())
             .justName(f.getName())
             .size(f.length())
             .build();
        fileEntryRepository.save(entry);
    }
}

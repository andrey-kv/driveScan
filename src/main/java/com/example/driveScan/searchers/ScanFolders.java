package com.example.driveScan.searchers;

import com.example.driveScan.data.FileEntry;
import com.example.driveScan.repositories.FileEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;

@Lazy
@Component
@Slf4j
@Qualifier("ScanFolders")
public class ScanFolders implements Searcher {

    @Autowired
    private FileEntryRepository fileEntryRepository;

    @Override
    public void scan(String startFolder) {

    }

    @Override
    public void display() {

    }

    @Override
    public String type() {
        return "ScanFolders";
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

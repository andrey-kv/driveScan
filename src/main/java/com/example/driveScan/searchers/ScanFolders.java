package com.example.driveScan.searchers;

import com.example.driveScan.data.FileEntry;
import com.example.driveScan.repositories.FileEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;

@Lazy
@Component
@Slf4j
@Qualifier("ScanFolders")
public class ScanFolders extends SearcherAbstract implements Searcher {

    @Autowired
    private FileEntryRepository fileEntryRepository;

    @Override
    protected void searchAction(File f) {
        FileEntry entry = FileEntry.builder()
                .fullName(f.getAbsolutePath())
                .justName(f.getName())
                .size(f.length())
                .build();
        fileEntryRepository.save(entry);
        log.info(f.getAbsolutePath());
    }

    @Override
    public void display() {
        log.info("Scan completed ============================");
    }

    @Override
    public String type() {
        return "ScanFolders";
    }

}

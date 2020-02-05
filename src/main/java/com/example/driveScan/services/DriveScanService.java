package com.example.driveScan.services;

import com.example.driveScan.data.FileEntry;
import com.example.driveScan.repositories.FileEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private FileEntryRepository fileEntryRepository;

    @Autowired
    @Qualifier("SearchStr")
    private Map<String, Integer> searchStr;
    private boolean precondition;

    public void scanAll(String startFolder) {
        log.info("Start folder = " + startFolder);
        log.info("===================================");

        scan(startFolder);
        showResult();
    }

    public void scan(String folder) {
        File item = new File(folder);
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
            // addFileToDatabase(f);
            readFile(f);
        }
    }

    private void readFile(File f) {
        if (!f.getPath().endsWith(".log")) {
            return;
        }

        log.info(f.getAbsolutePath());

        Scanner sc = null;

        try (FileInputStream fileInputStream = new FileInputStream(f)) {

            sc = new Scanner(fileInputStream, "UTF-8");
            precondition = false;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                searchSetOfStrings(line);
                // searchWithPrecondition(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchWithPrecondition(String line) {
        if (line.contains("Validation exception occurred.")) {
            precondition = true;
            return;
        }
        if (precondition) {
            String key = line.length() > 123 ? line.substring(0, 123) : line ;
            if (searchStr.containsKey(key)) {
                addOccurence(key);
            } else {
                searchStr.put(key, 1);
            }
        }
        precondition = false;
    }

    private void searchSetOfStrings(String line) {
        for (String s : searchStr.keySet()) {
            if (line.contains(s)) {
                addOccurence(s);
            }
        }
    }

    private void addOccurence(String s) {
        int cnt = searchStr.get(s);
        cnt++;
        searchStr.put(s, cnt);
    }

    private void showResult() {
        int total = 0;
        for (String s : searchStr.keySet()) {
            int cnt = searchStr.get(s);
            log.info(s + ": *found = " + cnt + "*");
            total += cnt;
        }
        log.info("*Total = " + total + "*");
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

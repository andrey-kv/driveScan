package com.example.driveScan.services;

import com.example.driveScan.data.FileEntry;
import com.example.driveScan.repositories.FileEntryRepository;
import com.example.driveScan.searchers.Searcher;
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
    private Searcher searcher;

    @Autowired
    @Qualifier("SearchStr")
    private Map<String, Integer> searchStr;
    private boolean precondition;

    public void scanAll(String startFolder) {
        log.info("Start folder = " + startFolder);
        log.info("= Scan ======================================");

        searcher.scan(startFolder);
        // scan(startFolder);
        log.info("= Display ===================================");
        // showResult();
        searcher.display();
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
        if (!f.getPath().endsWith(".txt")) {
            return;
        }

        log.info(f.getAbsolutePath());

        Scanner sc = null;

        try (FileInputStream fileInputStream = new FileInputStream(f)) {

            sc = new Scanner(fileInputStream, "UTF-8");
            precondition = false;
            String prevLine1 = "";
            String prevLine2 = "";
            String prevLine3 = "";
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // searchSetOfStrings(line);
                // searchWithPrecondition(line);
                searchWithPostcondition(line, prevLine1, prevLine2, prevLine3);
                prevLine3 = prevLine2;
                prevLine2 = prevLine1;
                prevLine1 = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchWithPostcondition(String line, String prevLine1, String prevLine2, String prevLine3) {
        if (line.contains("Failed Login by avios username")) {
            if (!searchSetOfStrings(prevLine1, prevLine2, prevLine3)) {
                // log.info(prevLine1);
            }
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

    private boolean searchSetOfStrings(String line1, String line2, String line3) {
        boolean found = false;
        for (String s : searchStr.keySet()) {
            if (line1.contains(s)) {
                addOccurence(s);
                found = true;
                break;
            } else if (line2.contains(s)) {
                addOccurence(s);
                found = true;
                break;
            } else if (line3.contains(s)) {
                addOccurence(s);
                found = true;
                break;
            }
        }
        if (!found) {
            log.info(line1);
            log.info(line2);
            log.info(line3);
            log.info("+++++++++++++++++++++++++++++++++++++++++");
        }
        return found;
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

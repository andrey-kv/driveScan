package com.example.driveScan.searchers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

@Lazy
@Component
@Slf4j
@Qualifier("SearchInFiles")
public class SearchInFiles extends SearcherAbstract implements Searcher {

    @Autowired
    @Qualifier("SearchStr")
    private Map<String, Integer> searchStr;

    @Value("${trigger.string}")
    private String triggerString;

    @Value("${filename.endwith}")
    private String filenameEnd;

    @Override
    protected void searchAction(File f) {
        if (!f.getPath().endsWith(filenameEnd)) {
            return;
        }

        log.info(f.getAbsolutePath());

        Scanner sc = null;

        try (FileInputStream fileInputStream = new FileInputStream(f)) {

            sc = new Scanner(fileInputStream, "UTF-8");
            String prevLine1 = "";
            String prevLine2 = "";
            String prevLine3 = "";
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                searchWithPostCondition(line, prevLine1, prevLine2, prevLine3);
                prevLine3 = prevLine2;
                prevLine2 = prevLine1;
                prevLine1 = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchWithPostCondition(String line, String prevLine1, String prevLine2, String prevLine3) {
        if (line.contains(triggerString)) {
            if (!searchSetOfStrings(prevLine1, prevLine2, prevLine3)) {
                displayIfNotFound(prevLine1, prevLine2, prevLine3);
            }
        }
    }

    private boolean searchSetOfStrings(String line1, String line2, String line3) {
        for (String s : searchStr.keySet()) {
            if (line1.contains(s) || line2.contains(s) || line3.contains(s)) {
                addOccurence(s);
                return true;
            }
        }
        return false;
    }

    private void displayIfNotFound(String line1, String line2, String line3) {
        log.info(line1);
        log.info(line2);
        log.info(line3);
        log.info("+++++++++++++++++++++++++++++++++++++++++");
    }

    private void addOccurence(String s) {
        int cnt = searchStr.get(s);
        cnt++;
        searchStr.put(s, cnt);
    }

    @Override
    public void display() {
        int total = 0;
        for (String s : searchStr.keySet()) {
            int cnt = searchStr.get(s);
            log.info(s + ": *found = " + cnt + "*");
            total += cnt;
        }
        log.info("*Total = " + total + "*");
    }

    @Override
    public String type() {
        return "SearchInFiles";
    }
}

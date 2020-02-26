package com.example.driveScan.searchers;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public abstract class SearcherAbstract {

    public void scan(String startFolder) {
        File item = new File(startFolder);
        File[] files = item.listFiles();
        for (File f : files) {
            scanNext(f);
        }
    }

    private void scanNext(File f) {
        if (f.isDirectory()) {
            log.info("Reading folder: " + f.getAbsolutePath());
            scan(f.getAbsolutePath());
        } else {
            searchAction(f);
        }
    }

    protected abstract void searchAction(File f);
}

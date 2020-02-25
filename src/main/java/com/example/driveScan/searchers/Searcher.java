package com.example.driveScan.searchers;

public interface Searcher {
    void scan(String startFolder);
    void display();
    String type();
}

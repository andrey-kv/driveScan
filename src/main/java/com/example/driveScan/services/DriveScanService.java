package com.example.driveScan.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@Slf4j
public class DriveScanService {

    public void scan(String startFolder) {
        try (Stream<Path> paths = Files.walk(Paths.get(startFolder))) {
            paths
                    .forEach(f -> display(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void display(Path f) {
        String fullName = String.valueOf(f.getFileName());
        if (Files.isDirectory(f)) {
            log.info("Reading folder: " + fullName);
            //scan(fullName);
        } else {
            log.info(fullName);
        }
    }
}

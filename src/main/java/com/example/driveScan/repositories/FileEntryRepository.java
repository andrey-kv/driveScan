package com.example.driveScan.repositories;

import com.example.driveScan.data.FileEntry;
import org.springframework.data.repository.CrudRepository;

public interface FileEntryRepository extends CrudRepository<FileEntry, Integer> {
    Iterable<FileEntry> findByFullName(String fullName);
}

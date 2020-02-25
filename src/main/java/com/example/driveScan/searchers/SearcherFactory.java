package com.example.driveScan.searchers;

import com.example.driveScan.searchers.Searcher;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class SearcherFactory implements FactoryBean<Searcher> {

    @Value(("${scan.type}"))
    private String scanType;

    @Autowired
    @Lazy
    @Qualifier("ScanFolders")
    private Searcher scanFoldersSearcher;

    @Autowired
    @Lazy
    @Qualifier("SearchInFiles")
    private Searcher searchInFilesSearcher;

    @Override
    public Searcher getObject() throws Exception {
        switch (scanType) {
            case "texts": {
                return searchInFilesSearcher;
            }
            default: {
                return scanFoldersSearcher;
            }
        }
    }

    @Override
    public Class<?> getObjectType() {
        return Searcher.class;
    }
}

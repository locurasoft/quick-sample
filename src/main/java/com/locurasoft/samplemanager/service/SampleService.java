package com.locurasoft.samplemanager.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Iterator;

@Component("sampleService")
public class SampleService {
    @Autowired
    private FileService fileService;

    @Autowired
    private SettingsResource settingsResource;

    public void loadSamples() {
        for (String path : settingsResource.getSearchPaths()) {
            File file = new File(path);
            if (file.exists() && file.isDirectory()) {
                String[] fileEndings = settingsResource.getFileEndings().toArray(new String[0]);
                Iterator<File> iterator = FileUtils.iterateFiles(file, fileEndings, true);
                while (iterator.hasNext()) {
                    File sample = iterator.next();
                    System.out.println(sample.getName());
                }
            }
        }
    }



    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public void setSettingsResource(SettingsResource settingsResource) {
        this.settingsResource = settingsResource;
    }

}

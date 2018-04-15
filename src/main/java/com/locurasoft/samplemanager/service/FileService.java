package com.locurasoft.samplemanager.service;

import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.service.SettingsResource;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Iterator;
import java.util.List;

@Component("fileService")
public class FileService {

    @Autowired
    private SettingsResource settingsResource;

    public List<Sample> scanSamples() {

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
        return null;
    }

    public void setSettingsResource(SettingsResource settingsResource) {
        this.settingsResource = settingsResource;
    }
}

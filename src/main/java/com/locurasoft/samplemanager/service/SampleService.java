package com.locurasoft.samplemanager.service;

import com.locurasoft.samplemanager.dao.ISampleRepository;
import com.locurasoft.samplemanager.domain.ISettings;
import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.domain.analyzer.ISampleAnalyzer;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.locurasoft.samplemanager.domain.Sample.Factory.newSample;

@Component("sampleService")
public class SampleService implements ISampleService {

    @Autowired
    private ISampleRepository sampleRepository;

    @Autowired
    private ISettings settings;

    @Override
    public List<Sample> scanSamples() throws IOException {
        List<ISampleAnalyzer> analyzers = settings.getAnalyzers();
        String[] fileEndings = settings.getFileEndings().toArray(new String[0]);

        for (String pathString : settings.getSearchPaths()) {
            File directory = new File(pathString);
            if (directory.exists() && directory.isDirectory()) {
                Iterator<File> iterator = FileUtils.iterateFiles(directory, fileEndings, true);
                int i = 0;
                while (iterator.hasNext()) {
                    File file = iterator.next();
                    Sample sample = newSample(file);
                    if (sampleRepository.exists(sample)) {
                        sample = sampleRepository.findBySample(sample);
                    } else {
                        sampleRepository.save(sample);
                        System.out.println("Saving sample " + sample.getName());
                    }
                    for (ISampleAnalyzer analyzer : analyzers) {
                        analyzer.updateSample(sample);
                    }
                    if (i == 3) {
                        break;
                    }
                    i++;
                }
            }
        }
        return sampleRepository.findAll();
    }

    @Override
    public List<Sample> getSamples() {
        return sampleRepository.findAll();
    }

    public void setSampleRepository(ISampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public void setSettings(ISettings settings) {
        this.settings = settings;
    }

}

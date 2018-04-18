package com.locurasoft.samplemanager.service;

import com.locurasoft.samplemanager.dao.ISampleRepository;
import com.locurasoft.samplemanager.domain.Category;
import com.locurasoft.samplemanager.domain.ISettings;
import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.domain.SampleFactory;
import com.locurasoft.samplemanager.domain.analyzer.ISampleAnalyzer;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Component("sampleService")
public class SampleService implements ISampleService {

    @Autowired
    private ISampleRepository sampleRepository;

    @Autowired
    private SampleFactory sampleFactory;

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
                while (iterator.hasNext()) {
                    File file = iterator.next();
                    Sample sample = sampleFactory.newInstance(file);
                    if (!sampleRepository.existsByFileHash(sample.getFileHash())) {
//                        sample = sampleRepository.findByFileHash(sample.getFileHash());
//                    } else {
                        for (ISampleAnalyzer analyzer : analyzers) {
                            analyzer.updateSample(sample);
                        }
                        sampleRepository.save(sample);
                        System.out.println("Saving sample " + sample.getName());
                    }
                }
            }
        }
        return sampleRepository.findAll();
    }

    @Override
    public List<Sample> listAllSamples() {
        return sampleRepository.findAll();
    }

    @Override
    public List<Sample> listSamplesByCategory(Category category) {
        return sampleRepository.findByCategory(category);
    }

    public List<Sample> listSamplesByNameLike(String name) {
        return sampleRepository.findByNameLike(String.format("%%%s%%", name));
    }

    @Override
    public void updateCategory(Sample sample, String category) {
        if (category.isEmpty()) {
            sample.setCategory(null);
        } else {
            sample.setCategory(Category.valueOf(category));
        }
        sampleRepository.save(sample);
    }

    void setSampleRepository(ISampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    void setSettings(ISettings settings) {
        this.settings = settings;
    }

    void setSampleFactory(SampleFactory sampleFactory) {
        this.sampleFactory = sampleFactory;
    }
}

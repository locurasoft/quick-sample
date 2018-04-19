package com.locurasoft.samplemanager.service;

import com.locurasoft.samplemanager.domain.Category;
import com.locurasoft.samplemanager.domain.Sample;

import java.io.IOException;
import java.util.List;

public interface ISampleService {
    List<Sample> scanSamples() throws IOException;
    List<Sample> listAllSamples();
    List<Sample> listSamplesByCategory(Category category);

    List<Sample> listSamplesByNameLike(String name);

    void saveSample(Sample sample);
}

package com.locurasoft.samplemanager.service;

import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.domain.Settings;

import java.io.IOException;
import java.util.List;

public interface ISampleService {
    List<Sample> scanSamples() throws IOException;
    List<Sample> listAllSamples();
    List<Sample> listSamplesByCategory(Settings.Category category);

    List<Sample> listSamplesByNameLike(String name);
}

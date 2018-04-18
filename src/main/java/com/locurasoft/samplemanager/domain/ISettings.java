package com.locurasoft.samplemanager.domain;

import com.locurasoft.samplemanager.domain.analyzer.ISampleAnalyzer;

import java.util.List;

public interface ISettings {
    List<String> getCategoryStrings();

    Category[] getCategories();

    List<ISampleAnalyzer> getAnalyzers();

    List<String> getFileEndings();

    List<String> getSearchPaths();
}

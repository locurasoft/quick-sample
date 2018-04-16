package com.locurasoft.samplemanager.domain;

import com.locurasoft.samplemanager.domain.analyzer.ISampleAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("settings")
public class Settings implements ISettings {
    private List<String> searchPaths;
    private List<String> fileEndings;

    @Autowired
    private List<ISampleAnalyzer> analyzers;

    @Override
    public List<String> getCategoryStrings() {
        ArrayList<String> list = new ArrayList<>();
        for (Category category : Category.values()) {
            list.add(category.name());
        }
        return list;
    }

    @Override
    public Category[] getCategories() {
        return Category.values();
    }

    @Override
    public List<ISampleAnalyzer> getAnalyzers() {
        return analyzers;
    }

    @Override
    public List<String> getFileEndings() {
        return fileEndings;
    }

    public void setFileEndings(List<String> fileEndings) {
        this.fileEndings = fileEndings;
    }

    @Override
    public List<String> getSearchPaths() {
        return searchPaths;
    }

    public void setSearchPaths(List<String> searchPaths) {
        this.searchPaths = searchPaths;
    }

    public enum Category {
        Kicks("bd", "bassdrum", "bass drum"),
        Snares("sd"),
        Toms("tom"),
        Hihats("hh"),
        Cymbals("cym"),
        Claps("cp");

        private final String[] matches;

        Category(String... matchesList) {
            this.matches = matchesList;
        }

        public String[] getMatches() {
            return matches.clone();
        }
    }
}

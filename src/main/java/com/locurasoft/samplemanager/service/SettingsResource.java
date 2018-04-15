package com.locurasoft.samplemanager.service;

import com.locurasoft.samplemanager.domain.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("sampleResource")
public class SettingsResource {
    private List<String> searchPaths;
    private List<String> fileEndings;

    public List<String> getCategories() {
        ArrayList<String> list = new ArrayList<>();
        for (Category category : Category.values()) {
            list.add(category.name());
        }
        return list;
    }

    public List<String> getFileEndings() {
        return fileEndings;
    }

    public void setFileEndings(List<String> fileEndings) {
        this.fileEndings = fileEndings;
    }

    public List<String> getSearchPaths() {
        return searchPaths;
    }

    public void setSearchPaths(List<String> searchPaths) {
        this.searchPaths = searchPaths;
    }
}

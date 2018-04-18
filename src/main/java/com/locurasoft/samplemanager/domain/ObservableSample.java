package com.locurasoft.samplemanager.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableSample {

    private final ObservableList<String> tagList;
    private final SimpleStringProperty filePath;
    private final SimpleStringProperty category;
    private final SimpleStringProperty name;
    private Sample sample;

    public ObservableSample() {
        tagList = FXCollections.observableArrayList();
        filePath = new SimpleStringProperty("");
        category = new SimpleStringProperty("");
        name = new SimpleStringProperty("");
    }

    public boolean isActive() {
        return sample != null;
    }

    public void setSample(Sample sample) {
        tagList.addAll(sample.getTags());
        filePath.set(sample.getFilePath());
        if (sample.getCategory() == null) {
            category.set("");
        } else {
            category.set(sample.getCategory().name());
        }
        name.set(sample.getName());
        this.sample = sample;
    }

    public Sample getSample() {
        return sample;
    }

    public void clearSample() {
        sample = null;
        tagList.clear();
        filePath.set("");
        category.set("");
        name.set("");
    }

    public SimpleStringProperty getFilePath() {
        return filePath;
    }

    public ObservableList<String> getTags() {
        return tagList;
    }

    public SimpleStringProperty getCategory() {
        return category;
    }

    public SimpleStringProperty getName() {
        return name;
    }
}

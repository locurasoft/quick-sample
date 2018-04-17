package com.locurasoft.samplemanager.controller;


import com.locurasoft.samplemanager.domain.ISettings;
import com.locurasoft.samplemanager.domain.ObservableSampleListFactory;
import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.service.ISampleService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.DragEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public final class MainController {

    @Autowired
    private ObservableSampleListFactory sampleListFactory;

    @Autowired
    private ISampleService sampleService;

    @Autowired
    private ISettings settings;


    public void rescan(ActionEvent actionEvent) {
        try {
            ObservableList<Sample> observableList = sampleListFactory.getInstance();
            List<Sample> samples = sampleService.scanSamples();
            observableList.clear();
            observableList.addAll(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void configure(ActionEvent actionEvent) {

    }

    public void volumeChange(DragEvent dragEvent) {

    }

    void setSampleService(ISampleService sampleService) {
        this.sampleService = sampleService;
    }

    void setSettings(ISettings settings) {
        this.settings = settings;
    }

    void setSampleListFactory(ObservableSampleListFactory sampleListFactory) {
        this.sampleListFactory = sampleListFactory;
    }
}

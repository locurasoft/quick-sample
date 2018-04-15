package com.locurasoft.samplemanager.controller;

import com.locurasoft.samplemanager.service.SampleService;
import com.locurasoft.samplemanager.service.SettingsResource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import org.springframework.beans.factory.annotation.Autowired;

import static javafx.collections.FXCollections.observableArrayList;

public class MainController {

    @FXML
    public ListView categories;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private SettingsResource settingsResource;

    @FXML
    public void initialize() {
        categories.setItems(observableArrayList(settingsResource.getCategories()));
    }

    public void rescan(ActionEvent actionEvent) {
        sampleService.loadSamples();
    }

    public void configure(ActionEvent actionEvent) {

    }

    public void volumeChange(DragEvent dragEvent) {

    }

    public void filter(ActionEvent actionEvent) {

    }

    public void setSampleService(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    public void setSettingsResource(SettingsResource settingsResource) {
        this.settingsResource = settingsResource;
    }

}

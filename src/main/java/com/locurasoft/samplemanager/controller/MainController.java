package com.locurasoft.samplemanager.controller;


import com.locurasoft.samplemanager.domain.ISettings;
import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.service.ISampleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static javafx.collections.FXCollections.observableList;

public final class MainController {

    @FXML
    private ListView categories;

    @FXML
    private ListView<Sample> samples;

    @Autowired
    private ISampleService sampleService;

    @Autowired
    private ISettings settings;

    @FXML
    public void initialize() {
        categories.setItems(observableList(settings.getCategoryStrings()));
        MultipleSelectionModel<String> selectionModel = categories.getSelectionModel();
        categories.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                categories.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (selectionModel.getSelectedIndices().contains(index)) {
                        selectionModel.clearSelection(index);
                    } else {
                        selectionModel.select(index);
                    }
                    event.consume();
                }
            });
            return cell;
        });

        MultipleSelectionModel<Sample> samplesSelectionModel = samples.getSelectionModel();
        samples.setCellFactory(new Callback<ListView<Sample>, ListCell<Sample>>() {

            @Override
            public ListCell<Sample> call(ListView<Sample> p) {
                ListCell<Sample> cell = new ListCell<Sample>() {

                    @Override
                    public void updateItem(Sample item, boolean bln) {
                        super.updateItem(item, bln);
                        if (item != null) {
                            setText(item.getName());
                        }
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    samples.requestFocus();
                    if (!cell.isEmpty()) {
                        int index = cell.getIndex();
                        if (samplesSelectionModel.getSelectedIndices().contains(index)
                                && (samplesSelectionModel.getSelectedIndices().size() == 1 || event.isControlDown())) {
                            samplesSelectionModel.clearSelection(index);
                        } else if (samplesSelectionModel.getSelectedIndices().contains(index)) {
                            samplesSelectionModel.clearSelection();
                            samplesSelectionModel.select(index);
                        } else if (event.isControlDown()) {
                            samplesSelectionModel.select(index);
                        } else {
                            samplesSelectionModel.clearSelection();
                            samplesSelectionModel.select(index);
                        }
                        event.consume();
                    }
                });
                return cell;
            }
        });
        samples.setItems(observableList(sampleService.getSamples()));
        samples.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void rescan(ActionEvent actionEvent) {
        try {
            List<Sample> sampleList = sampleService.scanSamples();
            this.samples.setItems(observableList(sampleList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void configure(ActionEvent actionEvent) {

    }

    public void volumeChange(DragEvent dragEvent) {

    }

    public void filter(ActionEvent actionEvent) {

    }

    public void setSampleService(ISampleService sampleService) {
        this.sampleService = sampleService;
    }

    public void setSettings(ISettings settings) {
        this.settings = settings;
    }

    public void setCategories(ListView categories) {
        this.categories = categories;
    }

    public void setSamples(ListView<Sample> samples) {
        this.samples = samples;
    }
}

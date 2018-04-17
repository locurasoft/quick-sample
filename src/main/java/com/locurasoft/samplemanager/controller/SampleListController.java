package com.locurasoft.samplemanager.controller;

import com.locurasoft.samplemanager.domain.ObservableSampleListFactory;
import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.service.ISampleService;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

public class SampleListController {

    @Autowired
    private ObservableSampleListFactory sampleListFactory;
    @FXML
    private ListView<Sample> samples;

    @Autowired
    private ISampleService sampleService;

    @FXML
    public void initialize() {
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
        sampleListFactory.getInstance().addAll(sampleService.listAllSamples());
        samples.setItems(sampleListFactory.getInstance());
        samples.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    void setSamples(ListView<Sample> samples) {
        this.samples = samples;
    }

    void setSampleService(ISampleService sampleService) {
        this.sampleService = sampleService;
    }

    void setSampleListFactory(ObservableSampleListFactory sampleListFactory) {
        this.sampleListFactory = sampleListFactory;
    }
}

package com.locurasoft.samplemanager.controller;

import com.locurasoft.samplemanager.domain.ISettings;
import com.locurasoft.samplemanager.domain.ObservableSampleListFactory;
import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.domain.Settings;
import com.locurasoft.samplemanager.service.ISampleService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static javafx.collections.FXCollections.observableList;

public class FilterController {

    @Autowired
    private ObservableSampleListFactory sampleListFactory;

    @Autowired
    private ISampleService sampleService;

    @Autowired
    private ISettings settings;

    @FXML
    private ListView categories;

    @FXML
    private TextField textFilter;

    @FXML
    public void initialize() {
        ObservableList<Sample> observableList = sampleListFactory.getInstance();
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
                        List<Sample> samples = sampleService.listAllSamples();
                        observableList.clear();
                        observableList.addAll(samples);

                    } else {
                        selectionModel.select(index);
                        List<Sample> samples = sampleService.listSamplesByCategory(
                                Settings.Category.valueOf(cell.textProperty().getValue()));
                        observableList.clear();
                        observableList.addAll(samples);
                    }
                    event.consume();
                }
            });
            return cell;
        });
    }

    public void filter(ActionEvent actionEvent) {
        List<Sample> samples = null;
        if (textFilter.getText().isEmpty()) {
            samples = sampleService.listAllSamples();
        } else {
            samples = sampleService.listSamplesByNameLike(textFilter.getText());
        }
        ObservableList<Sample> observableList = sampleListFactory.getInstance();
        observableList.clear();
        observableList.addAll(samples);
    }

    void setCategories(ListView categories) {
        this.categories = categories;
    }

    void setSettings(ISettings settings) {
        this.settings = settings;
    }

    void setSampleListFactory(ObservableSampleListFactory sampleListFactory) {
        this.sampleListFactory = sampleListFactory;
    }

    void setSampleService(ISampleService sampleService) {
        this.sampleService = sampleService;
    }

    void setTextFilter(TextField textFilter) {
        this.textFilter = textFilter;
    }
}

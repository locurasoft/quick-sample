package com.locurasoft.samplemanager.controller;


import com.locurasoft.samplemanager.domain.Category;
import com.locurasoft.samplemanager.domain.ISettings;
import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.service.ISampleService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableList;

public final class MainController {

    @FXML
    private ComboBox sampleCategoryCombo;

    @FXML
    private ListView sampleTagsListView;

    @FXML
    private Label sampleFilepathLabel;

    @FXML
    private Label sampleFilenameLabel;

    @Autowired
    private ISampleService sampleService;

    @Autowired
    private ISettings settings;

    @FXML
    private ListView categoriesListView;

    @FXML
    private TextField filterTextField;

    @FXML
    private ListView<Sample> sampleListView;

    private ObservableList<Sample> sampleList;
    private Sample selectedSample;

    @FXML
    public void initialize() {
        sampleList = observableArrayList();
        initCategories();
        initSampleList();
        initSampleDetail();
    }

    void initSampleDetail() {
        ObservableList<Category> categoryList = observableArrayList();
        categoryList.add(null);
        categoryList.addAll(asList(Category.values()));
        sampleCategoryCombo.setItems(categoryList);
    }

    void clearSelectedSample() {
        selectedSample = null;
        sampleFilenameLabel.setText("");
        sampleFilepathLabel.setText("");
        sampleCategoryCombo.setValue(null);
        sampleTagsListView.setItems(observableArrayList());
    }

    void selectSample(Sample sample) {
        if (selectedSample != null) {
            sampleService.saveSample(selectedSample);
        }
        selectedSample = sample;
        sampleFilenameLabel.textProperty().bindBidirectional(sample.getNameObservable());
        sampleFilepathLabel.textProperty().bindBidirectional(sample.getFilePathObservable());
        sampleCategoryCombo.valueProperty().bindBidirectional(sample.getCategoryObservable());
        sampleTagsListView.setItems(sample.getTagsObservable());
    }

    void initSampleList() {
        MultipleSelectionModel<Sample> samplesSelectionModel = sampleListView.getSelectionModel();
        sampleListView.setCellFactory(new Callback<ListView<Sample>, ListCell<Sample>>() {

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
                    sampleListView.requestFocus();
                    if (!cell.isEmpty()) {
                        int index = cell.getIndex();
                        if (samplesSelectionModel.getSelectedIndices().contains(index)
                                && (samplesSelectionModel.getSelectedIndices().size() == 1 || event.isControlDown())) {
                            samplesSelectionModel.clearSelection(index);
                            clearSelectedSample();
                        } else if (samplesSelectionModel.getSelectedIndices().contains(index)) {
                            samplesSelectionModel.clearSelection();
                            samplesSelectionModel.select(index);
                            selectSample(sampleList.get(index));
                        } else if (event.isControlDown()) {
                            samplesSelectionModel.select(index);
                            clearSelectedSample();
                        } else {
                            samplesSelectionModel.clearSelection();
                            samplesSelectionModel.select(index);
                            selectSample(sampleList.get(index));
                        }
                        event.consume();
                    }
                });
                return cell;
            }
        });

        sampleList.addAll(sampleService.listAllSamples());
        sampleListView.setItems(sampleList);
        sampleListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    void initCategories() {
        categoriesListView.setItems(observableList(settings.getCategoryStrings()));
        MultipleSelectionModel<String> selectionModel = categoriesListView.getSelectionModel();
        categoriesListView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                categoriesListView.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (selectionModel.getSelectedIndices().contains(index)) {
                        selectionModel.clearSelection(index);
                        sampleList.setAll(sampleService.listAllSamples());

                    } else {
                        selectionModel.select(index);
                        sampleList.setAll(sampleService.listSamplesByCategory(
                                Category.valueOf(cell.textProperty().getValue())));
                    }
                    event.consume();
                }
            });
            return cell;
        });
    }

    public void filter(ActionEvent actionEvent) {
        List<Sample> samples;
        if (filterTextField.getText().isEmpty()) {
            samples = sampleService.listAllSamples();
        } else {
            samples = sampleService.listSamplesByNameLike(filterTextField.getText());
        }
        sampleList.setAll(samples);
    }

    public void onRescan(ActionEvent actionEvent) {
        try {
            List<Sample> samples = sampleService.scanSamples();
            sampleList.setAll(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onConfigure(ActionEvent actionEvent) {

    }

    public void onVolumeChange(DragEvent dragEvent) {

    }

    void setSampleService(ISampleService sampleService) {
        this.sampleService = sampleService;
    }

    void setSettings(ISettings settings) {
        this.settings = settings;
    }

    void setCategoriesListView(ListView categoriesListView) {
        this.categoriesListView = categoriesListView;
    }

    void setFilterTextField(TextField filterTextField) {
        this.filterTextField = filterTextField;
    }

    void setSampleListView(ListView<Sample> sampleListView) {
        this.sampleListView = sampleListView;
    }

    void setSampleCategoryCombo(ComboBox sampleCategoryCombo) {
        this.sampleCategoryCombo = sampleCategoryCombo;
    }

    void setSampleTagsListView(ListView sampleTagsListView) {
        this.sampleTagsListView = sampleTagsListView;
    }

    void setSampleFilepathLabel(Label sampleFilepathLabel) {
        this.sampleFilepathLabel = sampleFilepathLabel;
    }

    void setSampleFilenameLabel(Label sampleFilenameLabel) {
        this.sampleFilenameLabel = sampleFilenameLabel;
    }
}

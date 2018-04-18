package com.locurasoft.samplemanager.controller;


import com.locurasoft.samplemanager.domain.Category;
import com.locurasoft.samplemanager.domain.ISettings;
import com.locurasoft.samplemanager.domain.ObservableSample;
import com.locurasoft.samplemanager.domain.ObservableSampleList;
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
    private ObservableSampleList sampleList;

    @Autowired
    private ISampleService sampleService;

    @Autowired
    private ISettings settings;

    @FXML
    private ListView categoriesListView;

    @FXML
    private TextField filterTextField;

    @Autowired
    private ObservableSample sampleDetail;
    @FXML
    private ListView<Sample> sampleListView;

    @FXML
    public void initialize() {
        initCategories();
        initSampleList();
        initSampleDetail();
    }

    void initSampleDetail() {
        sampleFilenameLabel.textProperty().bindBidirectional(sampleDetail.getName());
        sampleFilepathLabel.textProperty().bindBidirectional(sampleDetail.getFilePath());
        ObservableList<String> categoryList = observableArrayList();
        categoryList.add("");
        categoryList.addAll(asList(Category.getNames()));
        sampleCategoryCombo.setItems(categoryList);
        sampleCategoryCombo.valueProperty().bindBidirectional(sampleDetail.getCategory());
        sampleTagsListView.setItems(sampleDetail.getTags());
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
                            sampleDetail.clearSample();
                        } else if (samplesSelectionModel.getSelectedIndices().contains(index)) {
                            samplesSelectionModel.clearSelection();
                            samplesSelectionModel.select(index);
                            sampleDetail.clearSample();
                            sampleDetail.setSample(sampleList.get(index));
                        } else if (event.isControlDown()) {
                            samplesSelectionModel.select(index);
                            sampleDetail.clearSample();
                        } else {
                            samplesSelectionModel.clearSelection();
                            samplesSelectionModel.select(index);
                            sampleDetail.clearSample();
                            sampleDetail.setSample(sampleList.get(index));
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
                        sampleList.clearAndAddAll(sampleService.listAllSamples());

                    } else {
                        selectionModel.select(index);
                        sampleList.clearAndAddAll(sampleService.listSamplesByCategory(
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
        sampleList.clearAndAddAll(samples);
    }

    public void onRescan(ActionEvent actionEvent) {
        try {
            List<Sample> samples = sampleService.scanSamples();
            sampleList.clearAndAddAll(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onConfigure(ActionEvent actionEvent) {

    }

    public void onVolumeChange(DragEvent dragEvent) {

    }

    public void onCategoryChanged(ActionEvent actionEvent) {
        if (sampleDetail.isActive()) {
            sampleService.updateCategory(sampleDetail.getSample(), (String) sampleCategoryCombo.getValue());
        }
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

    void setSampleList(ObservableSampleList sampleList) {
        this.sampleList = sampleList;
    }

    void setFilterTextField(TextField filterTextField) {
        this.filterTextField = filterTextField;
    }

    void setSampleListView(ListView<Sample> sampleListView) {
        this.sampleListView = sampleListView;
    }

    void setSampleDetail(ObservableSample sampleDetail) {
        this.sampleDetail = sampleDetail;
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

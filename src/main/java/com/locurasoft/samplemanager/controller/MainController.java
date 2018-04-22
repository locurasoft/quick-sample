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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableList;

public final class MainController {

    public static final int BUTTON_HEIGHT = 20;
    public static final int TAGS_SPACING = 10;
    //little image as 15x15 for example
    private Image deletePng = new Image("/delete.png");

    @FXML
    private AnchorPane tagsPane;

    @FXML
    private ComboBox<Category> sampleCategoryCombo;

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

    //box is the pane where this buttons will be placed
    void tagButton(HBox box, String tag) {
        ImageView closeImg = new ImageView(deletePng);
        HBox button = new HBox();
        button.setStyle("-fx-padding:4;"
                + "        -fx-border-width: 2;"
                + "        -fx-border-color: black;"
                + "        -fx-border-radius: 4;"
                + "        -fx-background-color: f1f1f1;"
                + "        -fx-border-insets: 5;");
        button.setPrefHeight(BUTTON_HEIGHT);
        button.getChildren().addAll(new Label(tag), closeImg);

        closeImg.setOnMouseClicked(event -> {
            box.getChildren().remove(button);
            selectedSample.getTags().remove(tag);
        });
        button.setOnMouseClicked(event -> {
        });

        box.getChildren().add(button);
    }

    public void onApplicationExit() {
        if (selectedSample != null) {
            sampleService.saveSample(selectedSample);
        }
    }

    void clearSelectedSample() {
        selectedSample = null;
        sampleFilenameLabel.setText("");
        sampleFilepathLabel.setText("");
        sampleCategoryCombo.setValue(null);
        tagsPane.getChildren().clear();
        tagsPane.getChildren().add(new BorderPane());
    }

    void selectSample(Sample sample) {
        if (selectedSample != null) {
            sampleService.saveSample(selectedSample);
        }
        selectedSample = sample;
        sampleFilenameLabel.textProperty().bindBidirectional(sample.getNameObservable());
        sampleFilepathLabel.textProperty().bindBidirectional(sample.getFilePathObservable());
        sampleCategoryCombo.valueProperty().bindBidirectional(sample.getCategoryObservable());

        BorderPane tagsRoot = new BorderPane();
        HBox tagsBox = new HBox(TAGS_SPACING);
        tagsBox.setStyle("-fx-border-color: #F1F1F1;"
                + "          -fx-border-width: 1px;"
                + "          -fx-border-radius: 10;"
                + "          -fx-border-insets: 5");
        tagsRoot.setBottom(tagsBox);

        TextField textField = new TextField();
        textField.setPromptText("Tag name - ENTER to add");
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                tagButton(tagsBox, textField.getText());
                selectedSample.getTags().add(textField.getText());
                textField.clear();
            }
        });

        for (String tag : selectedSample.getTags()) {
            tagButton(tagsBox, tag);
        }
        tagsRoot.setTop(textField);
        tagsPane.getChildren().clear();
        tagsPane.getChildren().add(tagsRoot);
    }

    void initSampleList() {
        MultipleSelectionModel<Sample> samplesSelectionModel = sampleListView.getSelectionModel();
        sampleListView.setCellFactory(new Callback<ListView<Sample>, ListCell<Sample>>() {

            @Override
            public ListCell<Sample> call(ListView<Sample> p) {
                ListCell<Sample> cell = new ListCell<Sample>() {

                    @Override
                    public void updateItem(Sample item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
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

    void setSampleCategoryCombo(ComboBox<Category> sampleCategoryCombo) {
        this.sampleCategoryCombo = sampleCategoryCombo;
    }

    void setSampleFilepathLabel(Label sampleFilepathLabel) {
        this.sampleFilepathLabel = sampleFilepathLabel;
    }

    void setSampleFilenameLabel(Label sampleFilenameLabel) {
        this.sampleFilenameLabel = sampleFilenameLabel;
    }

    void setTagsPane(AnchorPane tagsPane) {
        this.tagsPane = tagsPane;
    }
}

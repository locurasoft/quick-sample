package com.locurasoft.samplemanager.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableSampleListFactory {

    private final ObservableList<Sample> list;

    public ObservableSampleListFactory() {
        list = FXCollections.observableArrayList();
    }

    public ObservableList<Sample> getInstance() {
        return list;
    }
}

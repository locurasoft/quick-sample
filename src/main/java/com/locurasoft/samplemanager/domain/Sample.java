package com.locurasoft.samplemanager.domain;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.stereotype.Component;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Component("sample")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String filePath;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags;
    private Category category;
    private String name;
    private String fileHash;

    public Sample() {
        this.tags = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getFileHash() {
        return fileHash;
    }

    void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public SimpleStringProperty getFilePathObservable() {
        return new SimpleStringProperty(filePath);
    }

    public SimpleObjectProperty<Category> getCategoryObservable() {
        return new SimpleObjectProperty<>(category);
    }

    public SimpleStringProperty getNameObservable() {
        return new SimpleStringProperty(name);
    }

    @Override
    public String toString() {
        return "Sample{" + "name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sample sample = (Sample) o;
        return Objects.equals(name, sample.name)
                && Objects.equals(fileHash, sample.fileHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fileHash);
    }
}

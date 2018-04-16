package com.locurasoft.samplemanager.domain;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Entity
@Component("sample")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String filePath;

    @ElementCollection
    private List<String> tags;
    private Settings.Category category;
    private String name;
    private String fileHash;

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

    public Settings.Category getCategory() {
        return category;
    }

    public void setCategory(Settings.Category category) {
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

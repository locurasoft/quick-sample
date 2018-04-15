package com.locurasoft.samplemanager;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.File;
import java.util.List;

@Entity
@Component("sampleResource")
public class Sample {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private File file;
    private List<String> tags;
    private Category category;
    private String name;
    private long hashCode;

    public long getHashCode() {
        return hashCode;
    }

    public void setHashCode(long hashCode) {
        this.hashCode = hashCode;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

    public void setName(String name) {
        this.name = name;
    }
}

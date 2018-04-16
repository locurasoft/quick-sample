package com.locurasoft.samplemanager.domain.analyzer;

import com.locurasoft.samplemanager.domain.Sample;
import com.locurasoft.samplemanager.domain.Settings;
import org.springframework.beans.factory.annotation.Autowired;

public class CategorySampleAnalyzer implements ISampleAnalyzer {
    @Autowired
    private Settings settings;

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void updateSample(Sample sample) {
        String name = sample.getName();
        for (Settings.Category category : settings.getCategories()) {
            String[] matches = category.getMatches();
            for (String match : matches) {
                if (name.contains(match)) {
                    sample.setCategory(category);
                    System.out.println("Matched sample " + name + " to category " +  category.name());
                    return;
                }
            }
        }
    }
}

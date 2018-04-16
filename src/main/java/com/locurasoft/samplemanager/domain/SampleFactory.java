package com.locurasoft.samplemanager.domain;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component("sampleFactory")
public class SampleFactory {

    public Sample newInstance(File file) throws IOException {
        Sample sample = new Sample();
        sample.setFilePath(file.getAbsolutePath());
        sample.setName(file.getName());
        FileInputStream fileInputStream = new FileInputStream(file);
        sample.setFileHash(DigestUtils.md5Hex(IOUtils.toByteArray(fileInputStream)));
        fileInputStream.close();
        return sample;
    }
}

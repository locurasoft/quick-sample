package com.locurasoft.samplemanager.dao;

import com.locurasoft.samplemanager.domain.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemorySampleRepositoryImpl implements ISampleRepository {

    private List<Sample> sampleList;

    public MemorySampleRepositoryImpl() {
        sampleList = new ArrayList<>();
    }

    @Override
    public List<Sample> findSamplesByName(String name) {
        return sampleList.stream().filter(s -> s.getName().contains(name)).collect(Collectors.toList());
    }

    @Override
    public <S extends Sample> S save(S s) {
        sampleList.add(s);
        return s;
    }

    @Override
    public <S extends Sample> Iterable<S> saveAll(Iterable<S> iterable) {
        for (S s : iterable) {
            sampleList.add(s);
        }
        return iterable;
    }

    @Override
    public Optional<Sample> findById(String s) {
        return sampleList.stream().filter(sample -> sample.getId() == Long.parseLong(s)).findFirst();
    }

    @Override
    public boolean existsById(String s) {
        return sampleList.stream().anyMatch(sample -> sample.getId() == Long.parseLong(s));
    }

    @Override
    public List<Sample> findAll() {
        return sampleList;
    }

    @Override
    public boolean exists(Sample sample) {
        return false;
    }

    @Override
    public Sample findBySample(Sample sample) {
        return null;
    }

    @Override
    public Iterable<Sample> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return sampleList.size();
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Sample sample) {
        sampleList.remove(sample);
    }

    @Override
    public void deleteAll(Iterable<? extends Sample> iterable) {
        for (Sample sample : iterable) {
            delete(sample);
        }
    }

    @Override
    public void deleteAll() {
        sampleList = new ArrayList<>();
    }
}
package com.locurasoft.samplemanager.dao;

import com.locurasoft.samplemanager.domain.Sample;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISampleRepository extends CrudRepository<Sample, String> {
    List<Sample> findSamplesByName(String name);
    List<Sample> findAll();

    boolean exists(Sample sample);

    Sample findBySample(Sample sample);
//    @Query("select emp from Employee emp where emp.age >= ?1 and emp.age <= ?2")
//    List<Sample> findEmployeesBetweenAge(int from, int to);
//    Page<Sample> findEmployeesByAgeGreaterThan(int age, Pageable pageable);
}

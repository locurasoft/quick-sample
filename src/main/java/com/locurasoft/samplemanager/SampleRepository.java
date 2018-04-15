package com.locurasoft.samplemanager;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SampleRepository extends CrudRepository<Sample,String> {
    List<Sample> findSamplesByName(String name);
//    @Query("select emp from Employee emp where emp.age >= ?1 and emp.age <= ?2")
//    List<Sample> findEmployeesBetweenAge(int from, int to);
//    Page<Sample> findEmployeesByAgeGreaterThan(int age, Pageable pageable);
}

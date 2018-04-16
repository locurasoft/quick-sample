package com.locurasoft.samplemanager.dao;

import com.locurasoft.samplemanager.domain.Sample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISampleRepository extends CrudRepository<Sample, String> {
    List<Sample> findByName(String name);
    List<Sample> findAll();

    boolean existsByFileHash(String fileHash);

    Sample findByFileHash(String fileHash);
//    @Query("select emp from Employee emp where emp.age >= ?1 and emp.age <= ?2")
//    List<Sample> findEmployeesBetweenAge(int from, int to);
//    Page<Sample> findEmployeesByAgeGreaterThan(int age, Pageable pageable);
}

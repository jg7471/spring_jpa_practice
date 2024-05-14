package com.study.jpa.chap04_relation.repository;

import com.study.jpa.chap04_relation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> { //엔터티 타입, PK 타입



    //JPQL : entity 사용 : Department d // d.employees(JOIN FETCH)
    @Query("SELECT d FROM Department d JOIN FETCH d.employees")
    List<Department> findAllIncludesEmployees();
}

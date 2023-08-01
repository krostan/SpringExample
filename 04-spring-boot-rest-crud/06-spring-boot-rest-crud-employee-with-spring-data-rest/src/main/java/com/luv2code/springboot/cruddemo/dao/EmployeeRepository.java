package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// 可自訂義 將http://localhost:8080/magic-api/employees  改成 http://localhost:8080/magic-api/members
// @RepositoryRestResource(path = "members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer>  {

    // that's it ... no need to write any code !
    // NO need for implementation class

}

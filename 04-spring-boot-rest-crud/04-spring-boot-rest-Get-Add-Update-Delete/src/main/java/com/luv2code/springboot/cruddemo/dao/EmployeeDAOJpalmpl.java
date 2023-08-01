package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAOJpalmpl implements EmployeeDAO{

    // define field for entitymanager
    private EntityManager entityManager;

    // set up constructor injection

    @Autowired
    public EmployeeDAOJpalmpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<Employee> findAll() {

        // create a query
        TypedQuery<Employee> theQuery = entityManager.createQuery("FROM Employee",Employee.class);

        // execute query and get result list
        List<Employee> employees = theQuery.getResultList();

        // return the results
        return employees;

    }

    @Override
    public Employee findById(int theId) {

        //get employee
        Employee theEmployee = entityManager.find(Employee.class,theId);

        // return employee

        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {

        // save employee
        // 根據實體的實際ID 執行保存或更新
        // 如果ID為0 就是一個插入
        // 否則就是一個更新
        Employee dbEmployee = entityManager.merge(theEmployee);

        // return the dbEmployee
        return dbEmployee;
    }

    @Override
    public void deleteById(int theId) {

        // find employee by id
        Employee theEmployee = entityManager.find(Employee.class,theId);

        // remove employee
        entityManager.remove(theEmployee);
    }
}

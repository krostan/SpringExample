package com.luv2code.springboot.thymeleafdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	// that's it ... no need to write any code LOL!

    // add a method to sort by last name
    // 在 spring data jpa中 它們將解析方法名稱 將尋找特定的格式和模式 並在幕後為你創建適當的查詢
    // findAllBy 是一種模式 按照OrderByLastNameASC
    // www.luv2code.com/query-methods
    public List<Employee> findAllByOrderByLastNameAsc();

}

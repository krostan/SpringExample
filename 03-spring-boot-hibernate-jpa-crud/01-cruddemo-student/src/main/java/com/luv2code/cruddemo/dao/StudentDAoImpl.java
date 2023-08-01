package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.util.List;

@Repository
public class StudentDAoImpl implements StudentDAO{

    // define field for entity manager
    private EntityManager entityManager;

    // inject entity manager using constructor injection

    @Autowired
    public StudentDAoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // implement save method
    // 添加@Transactional 因為正在對DB執行更新
    // 實際上是在DB中保存或存儲一個對象
    @Override
    @Transactional
    public void save(Student theStudent) {
        entityManager.persist(theStudent);
    }

    @Override
    public Student findById(Integer id) {
        return entityManager.find(Student.class,id);
    }

    @Override
    public List<Student> findAll() {
        // create query
        TypedQuery<Student> theQuery = entityManager.createQuery("FROM Student",Student.class);

        // return query results
        return theQuery.getResultList();
    }

    @Override
    public List<Student> findByLastName(String theLastName) {

        // create query
        TypedQuery<Student> theQuery =entityManager.createQuery(
                "FROM Student WHERE lastName=:theData",Student.class);

        // set query parameters
        theQuery.setParameter("theData",theLastName);

        //return query results
        return theQuery.getResultList();
    }

    //因為要在DB更新資料 所以需要加上@Transactional
    @Override
    @Transactional
    public void update(Student theStudent) {
        entityManager.merge(theStudent);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // retrieve the student
        Student theStudent =entityManager.find(Student.class,id);

        // delete the student
        entityManager.remove(theStudent);
    }

    // executeUpdate 只是一個術語
    // 簡單地說就是 修改DB
    @Override
    @Transactional
    public int deleteAll() {

        int numRowsDeleted =entityManager.createQuery("DELETE FROM Student").executeUpdate();

        return numRowsDeleted;
    }


}

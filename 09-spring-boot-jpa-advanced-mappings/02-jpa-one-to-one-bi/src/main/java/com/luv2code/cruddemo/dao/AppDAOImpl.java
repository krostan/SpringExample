package com.luv2code.cruddemo.dao;


import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AppDAOImpl implements AppDAO{

    // 為實體管理器 定義 field
    private EntityManager entityManager;

    // 透過 構造函數 注入實體管理器
    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    // 添加 Transactional 因為正在對資料庫做更新
    @Override
    @Transactional
    public void save(Instructor theInStructor) {
        // 儲存theInStructor 也會儲存到 InStructorDetail
        entityManager.persist(theInStructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class,theId);
    }

    // 添加 Transactional 因為要對資料庫進行修改
    @Override
    @Transactional
    public void deleteInstructorById(int theId) {

        // 找到 instructor
        Instructor tempInstructor = entityManager.find(Instructor.class,theId);

        // 刪除 instructor
        entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {

        // 找到 InstructorDetail
        InstructorDetail tempInstructorDetail = entityManager.find(InstructorDetail.class,theId);

        // 刪除關聯的對象引用
        // 斷開雙向鏈接
        tempInstructorDetail.getInstructor().setInstructorDetail(null);

        // 刪除 InstructorDetail
        entityManager.remove(tempInstructorDetail);
    }
}

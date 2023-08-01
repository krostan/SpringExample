package com.luv2code.cruddemo.dao;


import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import com.luv2code.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        // 獲得 courses
        List<Course> courses = tempInstructor.getCourses();

        // 解除 instructor 與 其全部courses 的關聯
        for(Course tempCourse : courses){
            tempCourse.setInstructor(null);
        }

        // 刪除 instructor
        // 這只會刪除instructor
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

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {

        // 創建 query
        // 這裡的Course 是entity的類別名
        // 這裡的instructor.id 是 類別instructor 的id
        TypedQuery<Course> query = entityManager.createQuery(
                "FROM Course WHERE instructor.id = :data", Course.class);
        query.setParameter("data",theId);

        // execute query
        List<Course> courses = query.getResultList();

        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {

        // 創建 query
        // @OneToMany default FETCH 是 LAZY
        // 因為使用JOIN FETCH 這類似於FETCHTYPE.EAGER加載
        TypedQuery<Instructor> query = entityManager.createQuery(
                "SELECT i FROM Instructor i " +
                        "JOIN FETCH i.courses " +
                        "JOIN FETCH i.instructorDetail " +
                        "WHERE i.id = :data", Instructor.class);

        query.setParameter("data",theId);

        // execute query
        Instructor instructor = query.getSingleResult();

        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
        // merge 將會更新Instructor
        entityManager.merge(tempInstructor);
    }

    @Override
    @Transactional
    public void update(Course tempCourse) {
        entityManager.merge(tempCourse);
    }

    @Override
    public Course findCourseById(int theId) {

        return entityManager.find(Course.class,theId);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {

        // 取得 Course
        Course tempCourse =entityManager.find(Course.class,theId);

        // 刪除 Course
        entityManager.remove(tempCourse);
    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        entityManager.persist(theCourse);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {

        // create query
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c " +
                        "JOIN FETCH c.reviews " +
                        "WHERE c.id = :data", Course.class);

        query.setParameter("data",theId);

        // execute query
        Course course =query.getSingleResult();

        return course;
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int theId) {

        // create query
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c " +
                        "JOIN FETCH c.students " +
                        "WHERE c.id = :data", Course.class);
        query.setParameter("data",theId);

        // execute query
        Course course = query.getSingleResult();

        return course;
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int theId) {

        // create query

        TypedQuery<Student> query = entityManager.createQuery(
                "SELECT s FROM Student s " +
                        "JOIN FETCH s.courses " +
                        "WHERE s.id = :data", Student.class);
        query.setParameter("data", theId);

        // execute query
        Student student = query.getSingleResult();

        return student;
    }

    @Override
    @Transactional
    public void update(Student tempStudent) {
        entityManager.merge(tempStudent);
    }

    @Override
    @Transactional
    public void deleteStudentById(int theId) {

        // retrieve the student
        Student tempStudent = entityManager.find(Student.class, theId);

        // delete the student
        entityManager.remove(tempStudent);
    }
}

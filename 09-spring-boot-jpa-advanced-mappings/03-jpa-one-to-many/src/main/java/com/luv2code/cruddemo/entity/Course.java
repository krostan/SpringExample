package com.luv2code.cruddemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="course")
public class Course {

    // 將類別注釋實體 並映射到資料庫

    // 定義 field

    // 用資料庫欄位名稱 注釋 field

    // ** 設定 映射 InstructorDetail 實體
    // 創造構造函數

    // 創造 getter/setter 方法

    // 創造 toString 方法

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="instructor_id")
    private Instructor instructor;

    public Course(){

    }

    public Course(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}

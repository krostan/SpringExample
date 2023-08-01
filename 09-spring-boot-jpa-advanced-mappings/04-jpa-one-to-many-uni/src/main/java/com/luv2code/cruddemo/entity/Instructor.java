package com.luv2code.cruddemo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="instructor")
public class Instructor {

    // 將類別注釋實體 並映射到資料庫

    // 定義 field

    // 用資料庫欄位名稱 注釋 field

    // ** 設定 映射 InstructorDetail 實體
    // 創造構造函數

    // 創造 getter/setter 方法

    // 創造 toString 方法

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="instructor_detail_id")
    private InstructorDetail instructorDetail;


    // 這裡的mappedBy = "instructor" 指的是 Course類別裡的private Instructor instructor;
    @OneToMany(mappedBy = "instructor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                        CascadeType.PERSIST , CascadeType.REFRESH})
    private List<Course> courses;

    public Instructor(){

    }

    public Instructor(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public InstructorDetail getInstructorDetail() {
        return instructorDetail;
    }

    public void setInstructorDetail(InstructorDetail instructorDetail) {
        this.instructorDetail = instructorDetail;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", instructorDetail=" + instructorDetail +
                '}';
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
	
	// 雙向1對多
    // 加入 instructor與 Course的關聯
    public void add(Course tempCourse){
        if(courses ==null){
            courses = new ArrayList<>();
        }
        // 在instructor 裡的courses 加入
        courses.add(tempCourse);
        // 在 course 裡的 Instructor 加入 Instructor
        tempCourse.setInstructor(this);
    }
}

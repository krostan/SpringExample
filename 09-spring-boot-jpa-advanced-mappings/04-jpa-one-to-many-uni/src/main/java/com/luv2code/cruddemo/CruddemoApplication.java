package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import com.luv2code.cruddemo.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO){

		return runner->{

			// createCourseAndReviews(appDAO);

			// retrieveCourseAndReviews(appDAO);

			deleteCourseAndReviews(appDAO);
		};
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {

		int theId = 10;

		System.out.println("Deleting Course id: " + theId);

		// 因為 cascade的關係 會連帶刪除 參考到此Course 的所有Review
		appDAO.deleteCourseById(theId);

		System.out.println("Done!");
	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {

		// get the course and reviews
		int theId = 10;
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);

		// print the course
		System.out.println(tempCourse);

		// print the reviews
		System.out.println(tempCourse.getReviews());

		System.out.println("Done!");
	}

	private void createCourseAndReviews(AppDAO appDAO) {

		// add a course
		Course tempCourse = new Course("Pacman - How To Score One Million Points");

		// add some reviews
		tempCourse.add(new Review("Great course .... loved it"));
		tempCourse.add(new Review("Cool course, job well done."));
		tempCourse.add(new Review("What a dumb course, you are an idiot!"));

		// save the course ... and leverage the cascade all review
		// 儲存 course , 透過聯集關係(cascade) 聯集所有review
		System.out.println("Saving the course");
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());

		appDAO.save(tempCourse);

		System.out.println("Done!");
	}

	private void deleteCourse(AppDAO appDAO) {

		int theId = 10;

		System.out.println("Deleting Course id: " + theId);

		appDAO.deleteCourseById(theId);

		System.out.println("Done!");
	}

	private void updateCourse(AppDAO appDAO) {

		int theId = 10;
		//  找到Course 的id
		System.out.println("Finding Course id: " + theId);
		Course tempCourse = appDAO.findCourseById(theId);

		// 更新數據
		System.out.println("Updating Course id : " + theId);
		tempCourse.setTitle("Enjoy the Simple Things");

		appDAO.update(tempCourse);

		System.out.println("Done!");
	}

	private void updateInstructor(AppDAO appDAO) {

		int theId = 1;
		//  找到instructor 的id
		System.out.println("Finding instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		// 更新數據
		System.out.println("Updating instructor id : " + theId);
		tempInstructor.setLastName("TESTER");

		appDAO.update(tempInstructor);

		System.out.println("Done!");
	}

	private void findInstructorWithCourseJoinFetch(AppDAO appDAO) {

		int theId = 1;
		//  找到instructor 的id
		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);

		System.out.println("tempInstructor : "+ tempInstructor);
		System.out.println("the associated Instructor : "+ tempInstructor.getCourses());

		System.out.println("Done!");
	}

	private void findCourseForInstructor(AppDAO appDAO) {

		int theId = 1;
		//  找到instructor 的id
		System.out.println("Finding instructor id: " + theId);

		// 因為fetchType.LAZY的關係 只會讀取Instructor 而 Courses 不會被讀取
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor : "+ tempInstructor);

		// find courses for instructor
		System.out.println("Finding course for instructor id : "+ theId);
		List<Course> courses= appDAO.findCoursesByInstructorId(theId);

		// 將兩者關聯
		tempInstructor.setCourses(courses);

		System.out.println("the associated Instructor : "+ tempInstructor.getCourses());
		System.out.println("Done!");

	}

	private void findInstructorWithCourse(AppDAO appDAO) {

		int theId = 1;
		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor : "+ tempInstructor);

		System.out.println("the associated Instructor : "+ tempInstructor.getCourses());
		System.out.println("Done!");
	}

	private void createInstructorWithCourse(AppDAO appDAO) {

		// create the instructor
		Instructor tempInstructor = new Instructor("Susan","Public","Susan@luv2code.com");

		// create the instructor Detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail("http://www.youtube.com","Video Games");

		// 關聯對象
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// create some Course
		Course tempCourse1 = new Course("Air Guitar - The Ultimate Guide");
		Course tempCourse2 = new Course("The Pinball Masterclass");

		// add courses to instructor
		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);

		// save the Instructor
		// 這同樣會將courses 儲存
		// 因為 CascadeType.PERSIST
		System.out.println("Saving instructor : " + tempInstructor);
		System.out.println("The courses : " + tempInstructor.getCourses());
		appDAO.save(tempInstructor);
		System.out.println("Done!");
	}

	private void deleteInstructorDetail(AppDAO appDAO) {

		int theId = 3;
		System.out.println("Deleting instructor detail id : " + theId);

		appDAO.deleteInstructorDetailById(theId);
		System.out.println("Done!");
	}

	private void findInstructorDetail(AppDAO appDAO) {

		// 得到 InstructorDetail 物件
		int theId = 2;
		InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);

		// 印出 Instructor Detail
		System.out.println("InstructorDetail : " + tempInstructorDetail);

		// 印出 有關聯的 Instructor
		System.out.println("the associated Instructor : " + tempInstructorDetail.getInstructor() );

	}

	private void deleteInstructor(AppDAO appDAO) {

		int theId = 1;
		System.out.println("Deleting instructor id: " + theId);

		appDAO.deleteInstructorById(theId);
		System.out.println("Done!");
	}

	private void findInstructor(AppDAO appDAO) {

		int theId = 1;

		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor : " + tempInstructor);
		System.out.println("the associate instructorDetail only : " + tempInstructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {

		/*
		// create the instructor
		Instructor tempInstructor = new Instructor("Chad","Dardy","darby@luv2code.com");

		// create the instructor Detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail("http://www.lu2code.com/youtube","Luv 2 Code!");
		 */

		// create the instructor
		Instructor tempInstructor = new Instructor("Leo","Patt","Leo@luv2code.com");

		// create the instructor Detail
		InstructorDetail tempInstructorDetail =
				new InstructorDetail("http://www.lu2code.com/youtube","Guitar");

		// 關聯對象
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// 儲存instructor
		// NOTE: 這樣會儲存InstructorDetail
		// 因為 CascadeType.ALL
		System.out.println("Saving instructor: " + tempInstructor);
		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}
}









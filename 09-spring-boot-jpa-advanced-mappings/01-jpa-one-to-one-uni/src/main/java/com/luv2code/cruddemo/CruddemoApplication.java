package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO){

		return runner->{
			// createInstructor(appDAO);

			// findInstructor(appDAO);

			deleteInstructor(appDAO);
		};
	}

	private void deleteInstructor(AppDAO appDAO) {

		int theId=1;
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









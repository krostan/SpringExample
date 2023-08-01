package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.StudentDAO;
import com.luv2code.cruddemo.entity.Student;
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
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO){
		return runner -> {
//			createStudent(studentDAO);

			// 回家先執行下列 1.
			createMultiplesStudents(studentDAO);

			// 2.
//			readStudent(studentDAO);

			// 3.
//			queryForStudents(studentDAO);

			// 4.
//			queryForStudentsByLastName(studentDAO);

			// 5.
//			updateStudent(studentDAO);

			// 6.
//			deleteStudent(studentDAO);

			// 7.
//			deleteAllStudents(studentDAO);


		};
	}

	private void deleteAllStudents(StudentDAO studentDAO) {

		System.out.println("Deleting all students");
		int numRowsDeleted=studentDAO.deleteAll();

		System.out.println("Deleted row count: "+numRowsDeleted);
	}

	private void deleteStudent(StudentDAO studentDAO) {

		//delete the student
		int studentId=3;
		System.out.println("Deleting student id: "+ studentId);
		studentDAO.delete(studentId);
	}

	private void updateStudent(StudentDAO studentDAO) {

		// retreve student based on the id: primary key
		int studentId=1;
		System.out.println("Getting student with id: "+ studentId);
		Student myStudent =studentDAO.findById(studentId);

		// change first name to "Scooby" ->"John"
		System.out.println("Updating student ...");
		myStudent.setFirstName("John");

		// update the student
		studentDAO.update(myStudent);

		// display updated student
		System.out.println("Updated student: "+myStudent);

	}

	private void queryForStudentsByLastName(StudentDAO studentDAO) {

		// get a list of students
		List<Student> theStudents =studentDAO.findByLastName("Duck");

		//display list of students
		for(Student tempStudent:theStudents){
			System.out.println(tempStudent);
		}
	}

	private void queryForStudents(StudentDAO studentDAO) {

		// get a list of students
		List<Student> theStudents =studentDAO.findAll();

		//display list of students
		for(Student tempStudent : theStudents){
			System.out.println(tempStudent);
		}
	}

	private void readStudent(StudentDAO studentDAO) {

		// create a student object
		System.out.println("Creating new student object ...");
		Student tempStudent = new Student("Daffy","Duck","daffy@luv2code.com");

		// save the student object
		System.out.println("Saving the student ...");
		studentDAO.save(tempStudent);

		//display id of the saved student
		int theId = tempStudent.getId();
		System.out.println("Saved student. Generated id: "+ theId);

		//retrieve student based on the id:primary key
		System.out.println("Retrieving student with id: "+theId);
		Student myStudent = studentDAO.findById(theId);

		//display student
		System.out.println("Found the student: "+ myStudent);


	}

	private void createMultiplesStudents(StudentDAO studentDAO) {

		// create mutliple student
		System.out.println("Creating 3 student object ...");
		Student tempStudent1 = new Student("Paul","Doe","paul@luv2code.com");
		Student tempStudent2 = new Student("Mary","Public","mary@luv2code.com");
		Student tempStudent3 = new Student("bonita","Applebum","bonita@luv2code.com");

		// save the student object
		System.out.println("Saving the student ...");
		studentDAO.save(tempStudent1);
		studentDAO.save(tempStudent2);
		studentDAO.save(tempStudent3);

	}

	private void createStudent(StudentDAO studentDAO) {

		// create the student object
		System.out.println("Creating new student object ...");
		Student tempStudent = new Student("Paul","Doe","paul@luv2code.com");

		// save the student object
		System.out.println("Saving the student ...");
		studentDAO.save(tempStudent);

		//display id of the saved student
		System.out.println("Saved student. Generated id: "+ tempStudent.getId());
	}
}










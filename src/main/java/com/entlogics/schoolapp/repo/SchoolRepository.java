package com.entlogics.schoolapp.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import com.entlogics.schoolapp.models.Exam;
import com.entlogics.schoolapp.models.School;
import com.entlogics.schoolapp.models.SchoolClass;
import com.entlogics.schoolapp.models.SchoolSubject;
import com.entlogics.schoolapp.models.Student;
import com.entlogics.schoolapp.models.Subject;

@Repository
@Component
public class SchoolRepository implements ISchoolRepository {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");

	public SchoolRepository() {
		super();

	}

	// method to find list of schools
	@Override
	public List<School> findAllSchools() {
		System.out.println("Inside findAllSchools() method in SchoolRepository");
		EntityManager entityManager = factory.createEntityManager();
		// start transaction
		entityManager.getTransaction().begin();
		// getting list of schools from database
		List<School> schools = entityManager.createNativeQuery("select * from dt_school",School.class).getResultList();
		System.out.println("printing list of schools " + schools.size() + "\n" + schools);
		// commit
		entityManager.getTransaction().commit();

		return schools;

	}
	// method to find one school

	@Override
	public School findSchool(int schoolId) {
		System.out.println("Inside findSchool() method in SchoolRepository");
		EntityManager entityManager = factory.createEntityManager();
		// find details of one school
		 entityManager.getTransaction().begin();
		// getting school with particular schoolId
		School school = entityManager.find(School.class, schoolId);
		System.out.println("School details :" + school);
		 entityManager.getTransaction().commit();

		return school;
	}

	// method to find All classes of school
	@Override
	public List<SchoolClass> findAllClasses(int schoolId) {
		EntityManager entityManager = factory.createEntityManager();
		 entityManager.getTransaction().begin();
		// find all classes in a school
		List<SchoolClass> classes =entityManager
				.createNativeQuery("select * from dt_class where school_id = :school_id", SchoolClass.class)
				.setParameter("school_id", schoolId).getResultList();

		entityManager.getTransaction().commit();
		return classes;
	}

	// method to find All subjects of school
	@Override
	public List<Subject> findAllSubjects(int schoolId) {
		System.out.println("Inside findAllSubjects() method in SchoolRepository");
		EntityManager entityManager = factory.createEntityManager();
		 entityManager.getTransaction().begin();
		School school =  entityManager.find(School.class, schoolId);
		
		// getting list of schoolsubject Objects of school
		List<SchoolSubject> ss = school.getSubjectsOfSchool();

		// for each entry with particular schoolId find subjects
		ListIterator litr = ss.listIterator();
		List<Subject> subjects = new ArrayList<Subject>();

		while (litr.hasNext()) {
			SchoolSubject ss1 = (SchoolSubject) litr.next();
			subjects.add(ss1.getSubject());
		}
		 entityManager.getTransaction().commit();
		return subjects;
	}
	// method to find All students of school
	@Override
	public List<Student> findAllStudents(int schoolId) {
		System.out.println("Inside findAllStudents() method in SchoolRepository");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		School school = entityManager.find(School.class, schoolId);
		// getting list of classes of school
		List<SchoolClass> classes = school.getClassesOfSchool();
		// for each entry with particular schoolId find students
		ListIterator litr = classes.listIterator();
		List<Student> students = new ArrayList<Student>();

		while (litr.hasNext()) {
			SchoolClass class1 = (SchoolClass) litr.next();
			students.addAll(class1.getStudentsOfClass());
		}
		entityManager.getTransaction().commit();
		return students;
	}
	// method to find All Exams of school
	@Override
	public List<Exam> findAllExams(int schoolId) {
		System.out.println("Inside findAllExams() method in SchoolRepository");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		School school = entityManager.find(School.class, schoolId);
		List<SchoolClass> sc = school.getClassesOfSchool();
		ListIterator litr = sc.listIterator();
		List<Exam> exams = new ArrayList<Exam>();
		while (litr.hasNext()) {

			SchoolClass sc1 = (SchoolClass) litr.next();

			exams.addAll(sc1.getExamsOfClass());
		}
		entityManager.getTransaction().commit();
		return exams;
	}
	// method to create school
	@Override
	public int createSchool(School school) {
		System.out.println("Inside createSchool() method in SchoolRepository");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		
		entityManager.persist(school);
		entityManager.getTransaction().commit();
		return school.getSchoolId();

	}
	// method for editing school details
	@Override
	public void editSchool(School school) {
		System.out.println("Inside editSchool() method in SchoolRepository");
		EntityManager entityManager = factory.createEntityManager();

		try {
			System.out.println("INSIDE editschool()");
			entityManager.getTransaction().begin();
			entityManager.clear();
			entityManager.merge(school);
			entityManager.getTransaction().commit();
			System.out.println("EDITED");
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}
	// method for deleting school 
	@Override
	public void deleteSchool(int schoolId) {
		System.out.println("Inside deleteSchool() method in SchoolRepository");
		EntityManager entityManager = factory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			School school = entityManager.find(School.class, schoolId);

			entityManager.remove(school);
			entityManager.getTransaction().commit();
			System.out.println("deleted school :" + schoolId);
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}

}
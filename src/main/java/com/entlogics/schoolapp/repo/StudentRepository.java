package com.entlogics.schoolapp.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.entlogics.schoolapp.models.Student;
import com.entlogics.schoolapp.models.StudentExam;
import com.entlogics.schoolapp.models.StudentSubject;

@Repository
public class StudentRepository implements IStudentRepository {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");

	@Override
	public int addStudent(Student s) {
		System.out.println("Inside addStudent() in StudentRepository");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(s);
		entityManager.getTransaction().commit();
		return s.getStudentId();

	}

	@Override
	public Student findStudent(int studentId) {
		EntityManager entityManager = factory.createEntityManager();

		entityManager.getTransaction().begin();
		Student student = 	entityManager.find(Student.class, studentId);
		System.out.println("Student details :" + student);
		entityManager.getTransaction().commit();

		return student;
	}

	@Override
	public List<Student> findStudents(List<Integer> studentClassId) {
		System.out.println("inside findStudents() Method in repo");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		List<Student> ls =	entityManager.createNativeQuery("select * from dt_student",Student.class).getResultList();
		System.out.println(ls);
		List<Student> students = new ArrayList<Student>();
		int i = 0;
		for (Integer j : studentClassId) {
			for (Student s : ls) {
				if (s.getStudentClassId() == j)
					System.out.println("count =" + i++);
				if (s.getStudentClassId() == j) {

					students.add(s);
				}
			}
		}
		System.out.println(students);
		entityManager.getTransaction().commit();
		return students;
	}

	@Override
	public void addSubject(StudentSubject stsubjects) {

		System.out.println("Inside addSubject() in StudentRepository");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery(
				"INSERT INTO  lt_student_subject(student_id,subject_id,attendance_percentage,number_of_assignments) VALUES (?,?,?,?)")
				.setParameter(1, stsubjects.getStudentId()).setParameter(2, stsubjects.getSubjectId())
				.setParameter(3, stsubjects.getAttendancePercentage())
				.setParameter(4, stsubjects.getNumberOfAssignments()).executeUpdate();

		entityManager.getTransaction().commit();

	}

	@Override
	public void addExam(StudentExam stexams) {
		System.out.println("Inside addExam() in StudentRepository");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery(
				"INSERT INTO  lt_student_exam(student_id,exam_id,marks,is_present,result_status,grade) VALUES (?,?,?,?,?,?)")
				.setParameter(1, stexams.getStudentId()).setParameter(2, stexams.getExamId())
				.setParameter(3, stexams.getMarks()).setParameter(4, stexams.isPresent())
				.setParameter(5, stexams.getResultstatus()).setParameter(6, stexams.getGrade()).executeUpdate();
		entityManager.getTransaction().commit();

	}

}

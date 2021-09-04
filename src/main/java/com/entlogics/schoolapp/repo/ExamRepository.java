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
import org.springframework.stereotype.Repository;

import com.entlogics.schoolapp.models.Exam;

@Repository
public class ExamRepository implements IExamRepository {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");
	@Override
	public int addExam(Exam e) {
		System.out.println("Inside addExam() in ExamRepository");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(e);
		entityManager.getTransaction().commit();
		return e.getExamId();
	}

	@Override
	public List<Exam> findExams(int classId, int subjectId) {
		System.out.println("Inside findExams() in ExamRepository");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		List<Exam> exams = entityManager.createNativeQuery("select * from dt_exam",Exam.class).getResultList();
		ListIterator litr = exams.listIterator();
		List<Exam> exams1 = new ArrayList<Exam>();

		for (Exam e : exams) {
			if (e.getClassId() == classId && e.getSubjectId() == subjectId)

				exams1.add(e);
		}
		entityManager.getTransaction().commit();
		return exams1;

	}

}

package com.entlogics.schoolapp.repo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.entlogics.schoolapp.models.Subject;

@Repository
public class SubjectRepository implements ISubjectRepository {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");

	@Override
	public int addSubject(Subject sub) {
		System.out.println("Inside addSubject() in SubjectRepository");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(sub);
		entityManager.getTransaction().commit();
		return sub.getSubjectId();

	}

}

package com.entlogics.schoolapp.repo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entlogics.schoolapp.models.SchoolClass;

@Repository
public class ClassRepository implements IClassRepository {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");

	@Override
	public int addClass(SchoolClass c) {
		System.out.println("Inside addClass() in ClassRepository");
		System.out.println("school id " + c.getClassSchoolId());
		System.out.println("class name" + c.getClassName());
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(c);
		entityManager.getTransaction().commit();
		return c.getClassId();
	}
}

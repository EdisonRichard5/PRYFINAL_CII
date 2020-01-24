package com.proyecto.cii.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.cii.app.models.entity.Employee;

//@Repository("clientDao") Si tenemos varias implementaciones, utilizamos
//un nombre para cada una. Si no, lo hacemos así
@Repository
public class EmployeeDaoImpl implements IEmployeeDao{

	@PersistenceContext
	private EntityManager em;


	@Override
	public Employee findOne(Long id) {
		return em.find(Employee.class, id);
	}
	
	@Override
	public List<Employee> findAll() {
		return em.createQuery("from Employee").getResultList();
	}

	@Override
	public void save(Employee employee) {
		if(employee.getId() != null && employee.getId() > 0) {
			em.merge(employee);
		} else {
			em.persist(employee);
		}
	}

	@Override
	public void delete(Long id) {
		if(id != null && id > 0) {
			em.remove(findOne(id));
		}
	}
}

package com.proyecto.cii.app.models.dao;

import java.util.List;

import com.proyecto.cii.app.models.entity.Employee;

public interface IEmployeeDao {
	public List<Employee> findAll();
	
	public void save(Employee employee);
	
	public Employee findOne(Long id);
	
	public void delete(Long id);
}

package com.proyecto.cii.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.cii.app.models.entity.Employee;
import com.proyecto.cii.app.models.entity.Inventory;
import com.proyecto.cii.app.models.entity.Product;
import com.proyecto.cii.app.reporting.LlaveValor;
import com.proyecto.cii.app.reporting.LlaveValor2;

public interface IEmployeeService {
	public List<Employee> findAll();
	
	public Page<Employee> findAll(Pageable page);

	public void save(Employee employee);

	public Employee findOne(Long id);

	public void delete(Long id);
	
	public List<Product> findByName(String search);
	
	public Product findProductById(Long id);
	
	public void saveInventory(Inventory inventory);
	
	public Inventory findInvoiceById(Long id);
	
	public void deleteInvoice(Long id);
	
	public Inventory fetchByIdWithClientWithInvoiceLineWithProduct(Long id);

	public Employee fetchByIdWithInvoice(Long id);
	
	public List<LlaveValor> countproduct();
	
	public List<LlaveValor2> countdate(Integer id);
}

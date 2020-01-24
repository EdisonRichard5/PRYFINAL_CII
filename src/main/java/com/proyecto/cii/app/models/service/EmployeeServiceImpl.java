package com.proyecto.cii.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.cii.app.models.dao.IEmployeeDao2;
import com.proyecto.cii.app.models.dao.InventoryDao;
import com.proyecto.cii.app.models.dao.IProductDao;
import com.proyecto.cii.app.models.entity.Employee;
import com.proyecto.cii.app.models.entity.Inventory;
import com.proyecto.cii.app.models.entity.Product;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeDao2 clientDao;

	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private InventoryDao invoiceDao;
	

	@Override
	@Transactional(readOnly=true)
	public List<Employee> findAll() {
		return (List<Employee>) clientDao.findAll();
	}

	@Override
	@Transactional
	public void save(Employee employee) {
		clientDao.save(employee);
	}

	@Override
	@Transactional(readOnly=true)
	public Employee findOne(Long id) {
		//return clientDao.findOne(id); //Spring Boot 1.5.10
		return clientDao.findById(id).orElse(null);	//Spring Boot 2
	}
	
	@Override
	@Transactional(readOnly=true)
	public Employee fetchByIdWithInvoice(Long id) {
		return clientDao.fetchByIdWithInvoice(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		//clientDao.delete(id);
		clientDao.deleteById(id);	//Spring Boot 2
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Employee> findAll(Pageable page) {
		return clientDao.findAll(page);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> findByName(String search) {
		return productDao.findByNameLikeIgnoreCase("%" + search + "%");
	}

	@Override
	@Transactional
	public void saveInventory(Inventory inventory) {
		invoiceDao.save(inventory);
	}

	@Override
	@Transactional(readOnly=true)
	public Product findProductById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Inventory findInvoiceById(Long id) {
		return invoiceDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteInvoice(Long id) {
		invoiceDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Inventory fetchByIdWithClientWithInvoiceLineWithProduct(Long id) {
		return invoiceDao.fetchByIdWithClientWithInvoiceLineWithProduct(id);
	}



}

package com.proyecto.cii.app.models.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.proyecto.cii.app.models.dao.IProductDao; 
import com.proyecto.cii.app.models.entity.Product; 
@Service
public class ProductService implements IProductService{

	@Autowired
	private IProductDao dao;
	
	@Override
	@Transactional
	public void save(Product producto) {
		dao.save(producto);
	}

	@Override
	@Transactional
	public Product findById(Long id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
		
	}

	@Override
	@Transactional
	public List<Product> findAll() {
		return (List<Product>)dao.findAll();
	}
	
}
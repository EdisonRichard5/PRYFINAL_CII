package com.proyecto.cii.app.models.service;

import java.util.List;

import com.proyecto.cii.app.models.entity.Product;

public interface IProductService {
	public void save(Product product);
	public Product findById(Long id);
	public void delete(Long id);
	public List<Product> findAll();
}

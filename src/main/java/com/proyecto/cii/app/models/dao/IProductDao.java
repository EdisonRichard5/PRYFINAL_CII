package com.proyecto.cii.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyecto.cii.app.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByName(String search);
	
	public List<Product> findByNameLikeIgnoreCase(String search);
}

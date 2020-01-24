package com.proyecto.cii.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyecto.cii.app.models.entity.Inventory; 

public interface InventoryDao extends CrudRepository<Inventory, Long> {

	//Si utilizamos el método findById() de la interfaz CrudRepository
	//al tener varias relaciones, se realizan demasiadas consultas
	//a la base de datos. Para evitarlo, y mejorar el rendimiento general
	//de la aplicación, personalizamos la query usando joins
	@Query("select i from Inventory i join fetch i.employee c join fetch i.lines l join fetch l.product where i.id=?1")
	public Inventory fetchByIdWithClientWithInvoiceLineWithProduct(Long id);
 
	
}

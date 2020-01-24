package com.proyecto.cii.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.proyecto.cii.app.models.entity.Employee;

/*
 * Extendiendo de CrudRepository(Modelo, Tipo de PK) podemos
 * ahorrarnos el implementar los métodos típicos de CRUD.
 * De esta manera no necesitamos implementar la clase
 * ClientDaoImpl
 */
//public interface IClientDao2 extends CrudRepository<Client, Long> {

//Podemos extender de PagingAndSortingRepository para facilitar
//la implementación de un sistema de paginación
public interface IEmployeeDao2 extends PagingAndSortingRepository<Employee, Long> {
	
	@Query("select c from Employee c left join fetch c.inventories i where c.id=?1")
	public Employee fetchByIdWithInvoice(Long id);
	
}

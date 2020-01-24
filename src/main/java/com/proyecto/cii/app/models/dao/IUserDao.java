package com.proyecto.cii.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.cii.app.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long> {

	public User findByUsername(String username);
	
}

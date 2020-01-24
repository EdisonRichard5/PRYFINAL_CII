package com.proyecto.cii.app.models.dao;

import java.util.List;

import com.proyecto.cii.app.models.entity.Client;

public interface IClientDao {
	public List<Client> findAll();
	
	public void save(Client client);
	
	public Client findOne(Long id);
	
	public void delete(Long id);
}

package com.proyecto.cii.app.models.service;

import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.cii.app.models.dao.IClientDao2;
import com.proyecto.cii.app.models.dao.IInvoiceDao;
import com.proyecto.cii.app.models.dao.IProductDao;
import com.proyecto.cii.app.models.entity.Client;
import com.proyecto.cii.app.models.entity.Invoice;
import com.proyecto.cii.app.models.entity.Product;
import com.proyecto.cii.app.reporting.LlaveValor;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDao2 clientDao;

	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IInvoiceDao invoiceDao;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly=true)
	public List<Client> findAll() {
		return (List<Client>) clientDao.findAll();
	}

	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);
	}

	@Override
	@Transactional(readOnly=true)
	public Client findOne(Long id) {
		//return clientDao.findOne(id); //Spring Boot 1.5.10
		return clientDao.findById(id).orElse(null);	//Spring Boot 2
	}
	
	@Override
	@Transactional(readOnly=true)
	public Client fetchByIdWithInvoice(Long id) {
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
	public Page<Client> findAll(Pageable page) {
		return clientDao.findAll(page);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> findByName(String search) {
		return productDao.findByNameLikeIgnoreCase("%" + search + "%");
	}

	@Override
	@Transactional
	public void saveInvoice(Invoice invoice) {
		invoiceDao.save(invoice);
	}

	@Override
	@Transactional(readOnly=true)
	public Product findProductById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Invoice findInvoiceById(Long id) {
		return invoiceDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteInvoice(Long id) {
		invoiceDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Invoice fetchByIdWithClientWithInvoiceLineWithProduct(Long id) {
		return invoiceDao.fetchByIdWithClientWithInvoiceLineWithProduct(id);
	}


	@Override	
	public List<LlaveValor> countproduct() {		
		StoredProcedureQuery consulta = em.createStoredProcedureQuery("venta");
		consulta.execute();
		List<Object[]> datos = consulta.getResultList();
		return datos.stream()
				.map(r -> new LlaveValor((String)r[1], (BigDecimal)r[0]))
				.collect(Collectors.toList());		
	}
}

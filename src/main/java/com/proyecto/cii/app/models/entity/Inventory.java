package com.proyecto.cii.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="inventories")
public class Inventory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String description;

	private String information; 

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;
 
	@ManyToOne(fetch=FetchType.LAZY)
	private Employee employee;
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="inventory_id")
	private List<InventoryLine> lines;

	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}

	public Inventory() {
		this.lines = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
 
	public List<InventoryLine> getLines() {
		return lines;
	}

	public void setLines(List<InventoryLine> lines) {
		this.lines = lines;
	}

	public void addLine(InventoryLine line) {
		this.lines.add(line);
	}

	public Double getTotal() {
		Double total = 0.0;
		for(InventoryLine line : lines) {
			total+= line.calculatePrice();
		}
		return total;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return id + " " + description;
	}
	
	
}

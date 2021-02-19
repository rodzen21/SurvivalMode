package com.oopclass.breadapp.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Entity
@Table(name = "Orders")
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String productName;
	
	private Integer quantity;
        
        private LocalDate doo;
        
        private LocalDate dod;


	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
        
	public LocalDate getDoo() {
		return doo;
	}

	public void setDoo(LocalDate doo) {
		this.doo = doo;
	}
        
        public LocalDate getDod() {
		return dod;
	}

	public void setDod(LocalDate dod) {
		this.dod = dod;
	}
	

	@Override
	public String toString() {
		return "Order [id=" + id + ", productName=" + productName + ", quantity=" + quantity + ", doo=" + doo + ", dod=" + dod + "]";
	}

	
}

package com.example.demo.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_tax", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id","tax_id"})})
public class ProductTax {
	@Id
	@GenericGenerator(name = "uuid", type = UUIDGenerator.class)
	@GeneratedValue(generator = "uuid")
	private String id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false) 
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tax_id", nullable = false) 
	private Tax tax;
}

package com.example.demo.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
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
@Table(name = "transaction_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"tx_id","product_id"})})
public class TransactionItem extends BaseAuditedEntity {
	@Id
	@GenericGenerator(name = "uuid", type = UUIDGenerator.class)
	@GeneratedValue(generator = "uuid")
	private String id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tx_id") 
	private Transaction transaction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "amt")
	private Integer amount;
}

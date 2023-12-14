package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "tax")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Tax {	
	@Id
	@GenericGenerator(name = "uuid", type = UUIDGenerator.class)
	@GeneratedValue(generator = "uuid")
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "value", nullable = false)
	private BigDecimal value;

	@JsonBackReference
	@OneToMany(mappedBy = "tax", fetch = FetchType.LAZY)
	private List<ProductTax> productTaxes;
}

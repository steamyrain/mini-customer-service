package com.example.demo.entity;

import java.time.Instant;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "customer")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Customer extends BaseAuditedEntity {
	@Id
	@GenericGenerator(name = "uuid", type = UUIDGenerator.class)
	@GeneratedValue(generator = "uuid")
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "birthplace", nullable = false)
	private String birthPlace;

	@Column(name = "birthdate", nullable = false)
	private Instant birthDate;
}

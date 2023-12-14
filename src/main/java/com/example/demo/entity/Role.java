package com.example.demo.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;

import com.example.demo.common.enums.RoleEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role extends BaseAuditedEntity {
	@Id
	@GenericGenerator(name = "uuid", type = UUIDGenerator.class)
	@GeneratedValue(generator = "uuid")
	private String id;

	@Column(unique = true, nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleEnum name;
}

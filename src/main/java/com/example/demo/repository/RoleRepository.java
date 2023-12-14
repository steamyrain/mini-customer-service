package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.common.enums.RoleEnum;
import com.example.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	public Optional<Role> findByName(RoleEnum name);
}

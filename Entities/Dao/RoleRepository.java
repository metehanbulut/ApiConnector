package com.linklegel.apiContact.Entities.Dao;

import java.util.Optional;

import com.linklegel.apiContact.Entities.Auth.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(String name);
}

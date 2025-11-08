package com.example.__iteration_1.repository;

import com.example.__iteration_1.classes.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, String> {
}

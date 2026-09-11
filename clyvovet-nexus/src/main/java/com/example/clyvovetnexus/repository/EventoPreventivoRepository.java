package com.example.clyvovetnexus.repository;

import com.example.clyvovetnexus.entity.EventoPreventivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoPreventivoRepository extends JpaRepository<EventoPreventivo, Long> {
}
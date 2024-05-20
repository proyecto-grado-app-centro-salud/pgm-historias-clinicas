package com.example.microserviciohistoriasclinicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;

public interface HistoriaClinicaRepositoryJPA extends JpaRepository<HistoriaClinicaEntity, Integer> {
    List<HistoriaClinicaEntity> findByIdPaciente(int idPaciente);
}
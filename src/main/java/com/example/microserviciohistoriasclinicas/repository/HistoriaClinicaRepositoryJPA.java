package com.example.microserviciohistoriasclinicas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;

public interface HistoriaClinicaRepositoryJPA extends JpaRepository<HistoriaClinicaEntity, Integer>,JpaSpecificationExecutor<HistoriaClinicaEntity> {

    List<HistoriaClinicaEntity> findByPaciente(UsuarioEntity paciente);
    Optional<HistoriaClinicaEntity> findByIdHistoriaClinicaAndDeletedAtIsNull(int idHistoriaClinica);

}
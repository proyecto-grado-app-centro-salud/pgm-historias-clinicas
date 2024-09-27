package com.example.microserviciohistoriasclinicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciohistoriasclinicas.model.EspecialidadesEntity;

import java.util.List;
import java.util.Optional;

public interface EspecialidadesRepositoryJPA extends JpaRepository<EspecialidadesEntity, Integer> {
    List<EspecialidadesEntity> findAllByDeletedAtIsNull();
    Optional<EspecialidadesEntity> findByIdEspecialidadAndDeletedAtIsNull(int idEspecialidad);
}
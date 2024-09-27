package com.example.microserviciohistoriasclinicas.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;



public interface UsuariosRepositoryJPA extends JpaRepository<UsuarioEntity, Integer>{
    List<UsuarioEntity> findAllByDeletedAtIsNull();
    Optional<UsuarioEntity> findByCiAndDeletedAtIsNull(String ci);
    Optional<UsuarioEntity> findByIdUsuarioAndDeletedAtIsNull(int idUsuario);
    Optional<UsuarioEntity> findByCi(String ci);

}

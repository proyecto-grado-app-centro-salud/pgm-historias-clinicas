package com.example.microserviciohistoriasclinicas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;

public interface HistoriaClinicaRepositoryJPA extends JpaRepository<HistoriaClinicaEntity, Integer>,JpaSpecificationExecutor<HistoriaClinicaEntity> {

    List<HistoriaClinicaEntity> findByPaciente(UsuarioEntity paciente);
    Optional<HistoriaClinicaEntity> findByIdHistoriaClinicaAndDeletedAtIsNull(int idHistoriaClinica);
    
    @Modifying
    @Query(value = "UPDATE notas_evolucion SET id_historia_clinica = ?2 WHERE id_historia_clinica = ?1", nativeQuery = true)
    void unirNotasEvolucionDeHistoriaAUnirAHistoriaDestino(int idHistoriaAUnir, int idHistoriaDestino);

    @Modifying
    @Query(value = "UPDATE examenes_complementarios SET id_historia_clinica = ?2 WHERE id_historia_clinica = ?1", nativeQuery = true)
    void unirExamenesComplementariosDeHistoriaAUnirAHistoriaDestino(int idHistoriaAUnir, int idHistoriaDestino);

    @Modifying
    @Query(value = "UPDATE notas_referencia SET id_historia_clinica = ?2 WHERE id_historia_clinica = ?1", nativeQuery = true)
    void unirNotasReferenciaDeHistoriaAUnirAHistoriaDestino(int idHistoriaAUnir, int idHistoriaDestino);

    @Modifying
    @Query(value = "UPDATE papeletas_internacion SET id_historia_clinica = ?2 WHERE id_historia_clinica = ?1", nativeQuery = true)
    void unirPapeletasInternacionDeHistoriaAUnirAHistoriaDestino(int idHistoriaAUnir, int idHistoriaDestino);

    @Modifying
    @Query(value = "UPDATE recetas SET id_historia_clinica = ?2 WHERE id_historia_clinica = ?1", nativeQuery = true)
    void unirRecetasDeHistoriaAUnirAHistoriaDestino(int idHistoriaAUnir, int idHistoriaDestino);

    @Modifying
    @Query(value = "UPDATE solicitudes_interconsulta SET id_historia_clinica = ?2 WHERE id_historia_clinica = ?1", nativeQuery = true)
    void unirSolicitudesInterconsultaDeHistoriaAUnirAHistoriaDestino(int idHistoriaAUnir, int idHistoriaDestino);

}
package com.example.microserviciohistoriasclinicas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;
import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;

public interface NotaEvolucionRepository extends JpaRepository<NotaEvolucionEntity, Integer>,JpaSpecificationExecutor<NotaEvolucionEntity> {
    @Query(value="SELECT id_nota_evolucion,hc.id_historia_clinica,cambios_paciente_resultados_tratamiento,ne.id_medico,ne.created_at,ne.updated_at,ne.deleted_at,CONCAT(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno),p.ci "+
    "FROM notas_evolucion ne "+
    "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = ne.id_historia_clinica "+
    "INNER JOIN pacientes p ON hc.id_paciente = p.id_paciente "+
    "INNER JOIN medicos m ON m.id_medico = ne.id_medico ",
    nativeQuery=true)
    List<Object[]> obtenerNotasEvolucion();
    @Query(value="SELECT id_nota_evolucion,hc.id_historia_clinica,cambios_paciente_resultados_tratamiento,ne.id_medico,ne.created_at,ne.updated_at,ne.deleted_at,CONCAT(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno),p.ci "+
    "FROM notas_evolucion ne "+
    "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = ne.id_historia_clinica "+
    "INNER JOIN pacientes p ON hc.id_paciente = p.id_paciente "+
    "INNER JOIN medicos m ON m.id_medico = ne.id_medico "+
    "WHERE ne.id_nota_evolucion=?1",
    nativeQuery=true)
    List<Object[]> obtenerNotaEvolucionPorId(int idNotaEvolucionSolicitada);

    @Query("SELECT ne FROM NotaEvolucionEntity ne "
    + "JOIN ne.historiaClinica hc "
    + "JOIN hc.paciente p "
    + "WHERE p.idUsuario = :idPaciente")
    List<NotaEvolucionEntity> obtenerNotasEvolucionPaciente(@Param("idPaciente") int idPaciente);
    Optional<NotaEvolucionEntity> findByIdNotaEvolucionAndDeletedAtIsNull(int idNotaEvolucion);
}
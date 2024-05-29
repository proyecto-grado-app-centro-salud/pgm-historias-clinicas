package com.example.microserviciohistoriasclinicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;

public interface NotaEvolucionRepository extends JpaRepository<NotaEvolucionEntity, Integer> {
    @Query(value="SELECT id_nota_evolucion,hc.id_historia_clinica,cambios_paciente_resultados_tratamiento,id_medico,ne.created_at,ne.updated_at,ne.deleted_at,CONCAT(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno),p.ci "+
    "FROM notas_evolucion ne "+
    "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = ne.id_historia_clinica "+
    "INNER JOIN pacientes p ON hc.id_paciente = p.id_paciente "+
    "INNER JOIN medicos m ON m.id_medico = ne.id_medico ",
    nativeQuery=true)
    List<Object[]> obtenerNotasEvolucion();
    @Query(value="SELECT id_nota_evolucion,hc.id_historia_clinica,cambios_paciente_resultados_tratamiento,id_medico,ne.created_at,ne.updated_at,ne.deleted_at,CONCAT(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno),p.ci "+
    "FROM notas_evolucion ne "+
    "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = ne.id_historia_clinica "+
    "INNER JOIN pacientes p ON hc.id_paciente = p.id_paciente "+
    "INNER JOIN medicos m ON m.id_medico = ne.id_medico "+
    "WHERE p.id_paciente=?1",nativeQuery=true)
    List<Object[]> obtenerRecetasPaciente(int idPaciente);
    @Query(value="SELECT id_nota_evolucion,hc.id_historia_clinica,cambios_paciente_resultados_tratamiento,id_medico,ne.created_at,ne.updated_at,ne.deleted_at,CONCAT(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno),p.ci "+
    "FROM notas_evolucion ne "+
    "INNER JOIN historias_clinicas hc ON hc.id_historia_clinica = ne.id_historia_clinica "+
    "INNER JOIN pacientes p ON hc.id_paciente = p.id_paciente "+
    "INNER JOIN medicos m ON m.id_medico = ne.id_medico "+
    "WHERE ne.id_nota_evolucion=?1",
    nativeQuery=true)
    List<Object[]> obtenerRecetaPorId(int id);
    Object obtenerNotaEvolucionPorId(int idNotaEvolucionSolicitada);
}
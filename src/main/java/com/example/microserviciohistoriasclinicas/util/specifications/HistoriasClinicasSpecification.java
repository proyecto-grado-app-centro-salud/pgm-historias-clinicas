package com.example.microserviciohistoriasclinicas.util.specifications;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.example.microserviciohistoriasclinicas.model.EspecialidadesEntity;
import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class HistoriasClinicasSpecification {
    public static Specification obtenerHistoriasClinicasPorParametros(Date minDate, Date maxDate,
            String ciPaciente, String nombrePaciente, String nombreMedico, String nombreEspecialidad,
            String diagnosticoPresuntivo) {
          try {
            return (root,query,builder) -> { 
                Join<HistoriaClinicaEntity, EspecialidadesEntity> especialidadJoin = root.join("especialidad");
                Join<HistoriaClinicaEntity, UsuarioEntity> medicoJoin = root.join("medico");
                Join<HistoriaClinicaEntity, UsuarioEntity> pacienteJoin = root.join("paciente");
                Predicate predicadoFinal = builder.isNull(root.get("deletedAt"));

                if (minDate != null) {
                    Predicate predicadoFechaMin = builder.greaterThanOrEqualTo(root.get("updatedAt"), minDate);
                    predicadoFinal = builder.and(predicadoFinal, predicadoFechaMin);
                }
                if (maxDate != null) {
                    Predicate predicadoFechaMax = builder.lessThanOrEqualTo(root.get("updatedAt"), maxDate);
                    predicadoFinal = builder.and(predicadoFinal, predicadoFechaMax);
                }
                if(ciPaciente!=null){
                    predicadoFinal = builder.and(predicadoFinal, builder.like(pacienteJoin.get("ci"), "%"+ciPaciente+"%"));
                }
                if(nombrePaciente!=null){
                    Predicate predicadoNombres = builder.or(
                        builder.like(builder.lower(pacienteJoin.get("nombres")), "%" + nombrePaciente.toLowerCase() + "%"),
                        builder.like(builder.lower(pacienteJoin.get("apellidoPaterno")), "%" + nombrePaciente.toLowerCase() + "%"),
                        builder.like(builder.lower(pacienteJoin.get("apellidoMaterno")), "%" + nombrePaciente.toLowerCase() + "%")
                    );
                    predicadoFinal = builder.and(predicadoFinal, predicadoNombres);
                }
                if(nombreMedico!=null){
                    Predicate predicadoNombres = builder.or(
                        builder.like(builder.lower(medicoJoin.get("nombres")), "%" + nombreMedico.toLowerCase() + "%"),
                        builder.like(builder.lower(medicoJoin.get("apellidoPaterno")), "%" + nombreMedico.toLowerCase() + "%"),
                        builder.like(builder.lower(medicoJoin.get("apellidoMaterno")), "%" + nombreMedico.toLowerCase() + "%")
                    );
                    predicadoFinal = builder.and(predicadoFinal,predicadoNombres);
                }
                if(nombreEspecialidad!=null){
                    predicadoFinal = builder.and(predicadoFinal, builder.like(especialidadJoin.get("nombre"), "%"+nombreEspecialidad+"%"));
                }
                if(diagnosticoPresuntivo!=null){
                    predicadoFinal = builder.and(predicadoFinal, builder.like(root.get("diagnosticoPresuntivo"), "%"+diagnosticoPresuntivo+"%"));
                }
                query.orderBy(builder.desc(root.get("updatedAt")));
                return predicadoFinal;
            };
        } catch (Exception e) {
            throw new RuntimeException("Error obtener notas evo por parametros");
        }
    }
     public static Specification obtenerHistoriasClinicasDePacientePorParametros(String idPaciente,Date minDate, Date maxDate,
            String nombreMedico, String nombreEspecialidad,
            String diagnosticoPresuntivo) {
          try {
            return (root,query,builder) -> {
                Join<HistoriaClinicaEntity, EspecialidadesEntity> especialidadJoin = root.join("especialidad");
                Join<HistoriaClinicaEntity, UsuarioEntity> medicoJoin = root.join("medico");
                Join<HistoriaClinicaEntity, UsuarioEntity> pacienteJoin = root.join("paciente");
                Predicate predicadoFinal = builder.and(builder.isNull(root.get("deletedAt")),builder.equal(pacienteJoin.get("idUsuario"), idPaciente));
                if (minDate != null) {
                    Predicate predicadoFechaMin = builder.greaterThanOrEqualTo(root.get("updatedAt"), minDate);
                    predicadoFinal = builder.and(predicadoFinal, predicadoFechaMin);
                }
                if (maxDate != null) {
                    Predicate predicadoFechaMax = builder.lessThanOrEqualTo(root.get("updatedAt"), maxDate);
                    predicadoFinal = builder.and(predicadoFinal, predicadoFechaMax);
                }
                if(nombreMedico!=null){
                    Predicate predicadoNombres = builder.or(
                        builder.like(builder.lower(medicoJoin.get("nombres")), "%" + nombreMedico.toLowerCase() + "%"),
                        builder.like(builder.lower(medicoJoin.get("apellidoPaterno")), "%" + nombreMedico.toLowerCase() + "%"),
                        builder.like(builder.lower(medicoJoin.get("apellidoMaterno")), "%" + nombreMedico.toLowerCase() + "%")
                    );
                    predicadoFinal = builder.and(predicadoFinal,predicadoNombres);
                }
                if(nombreEspecialidad!=null){
                    predicadoFinal = builder.and(predicadoFinal, builder.like(especialidadJoin.get("nombre"), "%"+nombreEspecialidad+"%"));
                }
                if(diagnosticoPresuntivo!=null){
                    predicadoFinal = builder.and(predicadoFinal, builder.like(root.get("diagnosticoPresuntivo"), "%"+diagnosticoPresuntivo+"%"));
                }
                query.orderBy(builder.desc(root.get("updatedAt")));
                return predicadoFinal;
            };
        } catch (Exception e) {
            throw new RuntimeException("Error obtener notas evo por parametros");
        }
    }
}

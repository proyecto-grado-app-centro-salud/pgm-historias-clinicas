package com.example.microserviciohistoriasclinicas.model.dtos;

import java.util.Date;

import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotaEvolucionDto {
    Integer id;
    String cambiosPacienteResultadosTratamiento;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    Integer idHistoriaClinica;
    String diagnosticoPresuntivo;
    Integer idEspecialidad;
    String nombreEspecialidad;
    Integer idMedico;
    String nombreMedico;
    Integer idPaciente;
    String pacientePropietario;
    String ciPropietario;
    public NotaEvolucionDto convertirNotaEvolucionEntityANotaEvolucionDto(NotaEvolucionEntity notaEvolucionEntity) {
        NotaEvolucionDto notaEvolucionDto = new NotaEvolucionDto();
        notaEvolucionDto.setId(notaEvolucionEntity.getIdNotaEvolucion());
        notaEvolucionDto.setCambiosPacienteResultadosTratamiento(notaEvolucionEntity.getCambiosPacienteResultadosTratamiento());
        notaEvolucionDto.setCreatedAt(notaEvolucionEntity.getCreatedAt());
        notaEvolucionDto.setUpdatedAt(notaEvolucionEntity.getUpdatedAt());
        notaEvolucionDto.setDeletedAt(notaEvolucionEntity.getDeletedAt());
        notaEvolucionDto.setIdHistoriaClinica(notaEvolucionEntity.getHistoriaClinica().getIdHistoriaClinica());
        notaEvolucionDto.setDiagnosticoPresuntivo(notaEvolucionEntity.getHistoriaClinica().getDiagnosticoPresuntivo());
        notaEvolucionDto.setIdEspecialidad(notaEvolucionEntity.getHistoriaClinica().getEspecialidad().getIdEspecialidad());
        notaEvolucionDto.setNombreEspecialidad(notaEvolucionEntity.getHistoriaClinica().getEspecialidad().getNombre());
        notaEvolucionDto.setIdMedico(notaEvolucionEntity.getMedico().getIdUsuario());
        notaEvolucionDto.setNombreMedico(notaEvolucionEntity.getMedico().getNombres()+" "+notaEvolucionEntity.getMedico().getApellidoPaterno()+" "+notaEvolucionEntity.getMedico().getApellidoMaterno());
        notaEvolucionDto.setIdPaciente(notaEvolucionEntity.getHistoriaClinica().getPaciente().getIdUsuario());
        notaEvolucionDto.setPacientePropietario(notaEvolucionEntity.getHistoriaClinica().getPaciente().getNombres()+" "+notaEvolucionEntity.getHistoriaClinica().getPaciente().getApellidoPaterno()+" "+notaEvolucionEntity.getHistoriaClinica().getPaciente().getApellidoMaterno());
        notaEvolucionDto.setCiPropietario(notaEvolucionEntity.getHistoriaClinica().getPaciente().getCi());
        return notaEvolucionDto;
    }
}

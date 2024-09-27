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
    Integer idNotaEvolucion;
    Integer idHistoriaClinica;
    String cambiosPacienteResultadosTratamiento;
    Integer idMedico;
    Date createdAt;
    Date updatedAt;
    Date deletedAt;
    String pacientePropietario;
    String ciPropietario;
    
    public NotaEvolucionDto convertirNotaEvolucionEntityANotaEvolucionDto(NotaEvolucionEntity notaEvolucionEntity) {
        NotaEvolucionDto notaEvolucionDto = new NotaEvolucionDto();
        notaEvolucionDto.setIdNotaEvolucion(notaEvolucionEntity.getIdNotaEvolucion());
        notaEvolucionDto.setIdHistoriaClinica(notaEvolucionEntity.getIdHistoriaClinica());
        notaEvolucionDto.setCambiosPacienteResultadosTratamiento(notaEvolucionEntity.getCambiosPacienteResultadosTratamiento());
        notaEvolucionDto.setIdMedico(notaEvolucionEntity.getIdMedico());
        notaEvolucionDto.setCreatedAt(notaEvolucionEntity.getCreatedAt());
        notaEvolucionDto.setUpdatedAt(notaEvolucionEntity.getUpdatedAt());
        notaEvolucionDto.setDeletedAt(notaEvolucionEntity.getDeletedAt());
        return notaEvolucionDto;
    }
}

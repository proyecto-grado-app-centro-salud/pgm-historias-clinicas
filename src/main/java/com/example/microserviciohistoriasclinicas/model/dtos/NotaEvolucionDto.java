package com.example.microserviciohistoriasclinicas.model.dtos;

import java.util.Date;

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
    
}

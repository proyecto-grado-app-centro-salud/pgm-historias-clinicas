package com.example.microserviciohistoriasclinicas.model.dtos;

import java.util.Date;

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

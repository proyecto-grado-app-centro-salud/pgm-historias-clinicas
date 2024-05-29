package com.example.microserviciohistoriasclinicas.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotaEvolucionEntity {
    private int idNotaEvolucion;
    private int idHistoriaClinica;
    private String cambiosPacienteResultadosTratamiento;
    private int idMedico;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}

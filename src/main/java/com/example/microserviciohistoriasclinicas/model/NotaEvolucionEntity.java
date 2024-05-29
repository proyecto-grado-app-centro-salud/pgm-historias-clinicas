package com.example.microserviciohistoriasclinicas.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notas_evolucion")
public class NotaEvolucionEntity {
    @Id
    @Column(name = "id_nota_evolucion")
    private int idNotaEvolucion;
    @Column(name = "id_historia_clinica")
    private int idHistoriaClinica;
    @Column(name = "cambios_paciente_resultados_tratamiento")
    private String cambiosPacienteResultadosTratamiento;
    @Column(name = "id_medico")
    private int idMedico;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "deleted_at")
    private Date deletedAt;
}

package com.example.microserviciohistoriasclinicas.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public void markAsDeleted() {
        deletedAt = new Date();
    }
}

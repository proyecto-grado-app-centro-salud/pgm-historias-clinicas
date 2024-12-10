package com.example.microserviciohistoriasclinicas.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinicaDto {
    private Integer id;
    private String amnesis;
    private String antecedentesFamiliares;
    private String antecedentesGinecoobstetricos;
    private String antecedentesNoPatologicos;
    private String antecedentesPatologicos;
    private String antecedentesPersonales;
    private String diagnosticoPresuntivo;
    private String diagnosticosDiferenciales;
    private String examenFisico;
    private String examenFisicoEspecial;
    private String propuestaBasicaDeConducta;
    private String tratamiento;
    private Integer idPaciente;
    private String pacientePropietario;
    private String ciPropietario;
    private Integer idMedico;
    private String nombreMedico;
    private Integer idEspecialidad;
    private String nombreEspecialidad;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    public static HistoriaClinicaDto convertirHistoriaClinicaEntityAHistoriaClinicaDto(HistoriaClinicaEntity historiaClinicaEntity) {
        HistoriaClinicaDto historiaClinicaDto = new HistoriaClinicaDto();
        historiaClinicaDto.setId(historiaClinicaEntity.getIdHistoriaClinica());
        historiaClinicaDto.setAmnesis(historiaClinicaEntity.getAmnesis());
        historiaClinicaDto.setAntecedentesFamiliares(historiaClinicaEntity.getAntecedentesFamiliares());
        historiaClinicaDto.setAntecedentesGinecoobstetricos(historiaClinicaEntity.getAntecedentesGinecoobstetricos());
        historiaClinicaDto.setAntecedentesNoPatologicos(historiaClinicaEntity.getAntecedentesNoPatologicos());
        historiaClinicaDto.setAntecedentesPatologicos(historiaClinicaEntity.getAntecedentesPatologicos());
        historiaClinicaDto.setAntecedentesPersonales(historiaClinicaEntity.getAntecedentesPersonales());
        historiaClinicaDto.setDiagnosticoPresuntivo(historiaClinicaEntity.getDiagnosticoPresuntivo());
        historiaClinicaDto.setDiagnosticosDiferenciales(historiaClinicaEntity.getDiagnosticosDiferenciales());
        historiaClinicaDto.setExamenFisico(historiaClinicaEntity.getExamenFisico());
        historiaClinicaDto.setExamenFisicoEspecial(historiaClinicaEntity.getExamenFisicoEspecial());
        historiaClinicaDto.setPropuestaBasicaDeConducta(historiaClinicaEntity.getPropuestaBasicaDeConducta());
        historiaClinicaDto.setTratamiento(historiaClinicaEntity.getTratamiento());
        historiaClinicaDto.setIdPaciente(historiaClinicaEntity.getPaciente().getIdUsuario());
        historiaClinicaDto.setPacientePropietario(historiaClinicaEntity.getPaciente().getNombres()+" "+historiaClinicaEntity.getPaciente().getApellidoPaterno()+" "+historiaClinicaEntity.getPaciente().getApellidoMaterno());
        historiaClinicaDto.setCiPropietario(historiaClinicaEntity.getPaciente().getCi());
        historiaClinicaDto.setIdMedico(historiaClinicaEntity.getMedico().getIdUsuario());
        historiaClinicaDto.setNombreMedico(historiaClinicaEntity.getMedico().getNombres()+" "+historiaClinicaEntity.getMedico().getApellidoPaterno()+" "+historiaClinicaEntity.getMedico().getApellidoMaterno());
        historiaClinicaDto.setIdEspecialidad(historiaClinicaEntity.getEspecialidad().getIdEspecialidad());
        historiaClinicaDto.setNombreEspecialidad(historiaClinicaEntity.getEspecialidad().getNombre());
        historiaClinicaDto.setCreatedAt(historiaClinicaEntity.getCreatedAt());
        historiaClinicaDto.setUpdatedAt(historiaClinicaEntity.getUpdatedAt());
        historiaClinicaDto.setDeletedAt(historiaClinicaEntity.getDeletedAt());
        return historiaClinicaDto;
    }
}


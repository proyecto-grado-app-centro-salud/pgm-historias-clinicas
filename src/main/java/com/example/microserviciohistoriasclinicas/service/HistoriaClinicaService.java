package com.example.microserviciohistoriasclinicas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciohistoriasclinicas.model.EspecialidadesEntity;
import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;
import com.example.microserviciohistoriasclinicas.model.dtos.HistoriaClinicaDto;
import com.example.microserviciohistoriasclinicas.repository.EspecialidadesRepositoryJPA;
import com.example.microserviciohistoriasclinicas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microserviciohistoriasclinicas.repository.UsuariosRepositoryJPA;

@Service
public class HistoriaClinicaService {
    @Autowired
    HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;

    @Autowired
    UsuariosRepositoryJPA usuariosRepositoryJPA;

    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    public List<HistoriaClinicaDto> obtenerHistoriasClinicas() {
        List<HistoriaClinicaEntity> historiasClinicasEntities = historiaClinicaRepositoryJPA.findAll();
        List<HistoriaClinicaDto> historiasClinicasDtos = new ArrayList<>();
        for (HistoriaClinicaEntity comunicadoEntity : historiasClinicasEntities) {
            HistoriaClinicaDto historiaClinicaDto = new HistoriaClinicaDto().convertirHistoriaClinicaEntityAHistoriaClinicaDto(comunicadoEntity);
            historiasClinicasDtos.add(historiaClinicaDto);
        }
        return historiasClinicasDtos;
    }
    public HistoriaClinicaDto actualizarHistoriaClinica(Integer idHistoriaClinica, HistoriaClinicaDto historiaClinicaDto) {
        UsuarioEntity pacienteEntity = usuariosRepositoryJPA.findById(historiaClinicaDto.getIdPaciente())
        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(historiaClinicaDto.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Medico no encontrado"));
        EspecialidadesEntity especialidadesEntity = especialidadesRepositoryJPA.findById(historiaClinicaDto.getIdEspecialidad())
        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(idHistoriaClinica)
        .orElseThrow(() -> new RuntimeException("Historia clinica no encontrada"));
        historiaClinicaEntity.setAmnesis(historiaClinicaDto.getAmnesis());
        historiaClinicaEntity.setAntecedentesFamiliares(historiaClinicaDto.getAntecedentesFamiliares());
        historiaClinicaEntity.setAntecedentesGinecoobstetricos(historiaClinicaDto.getAntecedentesGinecoobstetricos());
        historiaClinicaEntity.setAntecedentesNoPatologicos(historiaClinicaDto.getAntecedentesNoPatologicos());
        historiaClinicaEntity.setAntecedentesPatologicos(historiaClinicaDto.getAntecedentesPatologicos());
        historiaClinicaEntity.setAntecedentesPersonales(historiaClinicaDto.getAntecedentesPersonales());
        historiaClinicaEntity.setDiagnosticoPresuntivo(historiaClinicaDto.getDiagnosticoPresuntivo());
        historiaClinicaEntity.setDiagnosticosDiferenciales(historiaClinicaDto.getDiagnosticosDiferenciales());
        historiaClinicaEntity.setExamenFisico(historiaClinicaDto.getExamenFisico());
        historiaClinicaEntity.setExamenFisicoEspecial(historiaClinicaDto.getExamenFisicoEspecial());
        historiaClinicaEntity.setPropuestaBasicaDeConducta(historiaClinicaDto.getPropuestaBasicaDeConducta());
        historiaClinicaEntity.setTratamiento(historiaClinicaDto.getTratamiento());
        historiaClinicaEntity.setPaciente(pacienteEntity);
        historiaClinicaEntity.setMedico(medicoEntity);
        historiaClinicaEntity.setEspecialidad(especialidadesEntity);
        historiaClinicaRepositoryJPA.save(historiaClinicaEntity);
        return new HistoriaClinicaDto().convertirHistoriaClinicaEntityAHistoriaClinicaDto(historiaClinicaEntity);
    }
    public List<HistoriaClinicaDto> obtenerHistoriasClinicasDePaciente(int idPaciente) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findById(idPaciente)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<HistoriaClinicaEntity> historiasClinicasEntities = historiaClinicaRepositoryJPA.findByPaciente(usuarioEntity);
        List<HistoriaClinicaDto> historiasClinicasDtos = new ArrayList<>();
        for (HistoriaClinicaEntity comunicadoEntity : historiasClinicasEntities) {
            HistoriaClinicaDto historiaClinicaDto = new HistoriaClinicaDto().convertirHistoriaClinicaEntityAHistoriaClinicaDto(comunicadoEntity);
            historiasClinicasDtos.add(historiaClinicaDto);
        }
        return historiasClinicasDtos;
    }
    public HistoriaClinicaDto crearHistoriaClinica(HistoriaClinicaDto historiaClinicaDto) {
        UsuarioEntity pacienteEntity = usuariosRepositoryJPA.findById(historiaClinicaDto.getIdPaciente())
        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(historiaClinicaDto.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Medico no encontrado"));
        EspecialidadesEntity especialidadesEntity = especialidadesRepositoryJPA.findById(historiaClinicaDto.getIdEspecialidad())
        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        HistoriaClinicaEntity historiaClinicaEntity = new HistoriaClinicaEntity();
        historiaClinicaEntity.setAmnesis(historiaClinicaDto.getAmnesis());
        historiaClinicaEntity.setAntecedentesFamiliares(historiaClinicaDto.getAntecedentesFamiliares());
        historiaClinicaEntity.setAntecedentesGinecoobstetricos(historiaClinicaDto.getAntecedentesGinecoobstetricos());
        historiaClinicaEntity.setAntecedentesNoPatologicos(historiaClinicaDto.getAntecedentesNoPatologicos());
        historiaClinicaEntity.setAntecedentesPatologicos(historiaClinicaDto.getAntecedentesPatologicos());
        historiaClinicaEntity.setAntecedentesPersonales(historiaClinicaDto.getAntecedentesPersonales());
        historiaClinicaEntity.setDiagnosticoPresuntivo(historiaClinicaDto.getDiagnosticoPresuntivo());
        historiaClinicaEntity.setDiagnosticosDiferenciales(historiaClinicaDto.getDiagnosticosDiferenciales());
        historiaClinicaEntity.setExamenFisico(historiaClinicaDto.getExamenFisico());
        historiaClinicaEntity.setExamenFisicoEspecial(historiaClinicaDto.getExamenFisicoEspecial());
        historiaClinicaEntity.setPropuestaBasicaDeConducta(historiaClinicaDto.getPropuestaBasicaDeConducta());
        historiaClinicaEntity.setTratamiento(historiaClinicaDto.getTratamiento());
        historiaClinicaEntity.setPaciente(pacienteEntity);
        historiaClinicaEntity.setMedico(medicoEntity);
        historiaClinicaEntity.setEspecialidad(especialidadesEntity);
        HistoriaClinicaEntity savedEntity = historiaClinicaRepositoryJPA.save(historiaClinicaEntity);
        return new HistoriaClinicaDto().convertirHistoriaClinicaEntityAHistoriaClinicaDto(savedEntity);
    }
    public HistoriaClinicaDto obtenerHistoriaClinicaPorId(int idHistoriaClinica) {
        HistoriaClinicaEntity historiaEntity = historiaClinicaRepositoryJPA.findByIdHistoriaClinicaAndDeletedAtIsNull(idHistoriaClinica)
            .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));
        return new HistoriaClinicaDto().convertirHistoriaClinicaEntityAHistoriaClinicaDto(historiaEntity);
    }
}

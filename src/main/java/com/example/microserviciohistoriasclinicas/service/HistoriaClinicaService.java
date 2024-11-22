package com.example.microserviciohistoriasclinicas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.microserviciohistoriasclinicas.model.EspecialidadesEntity;
import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;
import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;
import com.example.microserviciohistoriasclinicas.model.dtos.HistoriaClinicaDto;
import com.example.microserviciohistoriasclinicas.model.dtos.NotaEvolucionDto;
import com.example.microserviciohistoriasclinicas.repository.EspecialidadesRepositoryJPA;
import com.example.microserviciohistoriasclinicas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microserviciohistoriasclinicas.repository.UsuariosRepositoryJPA;
import com.example.microserviciohistoriasclinicas.util.specifications.HistoriasClinicasSpecification;
import com.example.microserviciohistoriasclinicas.util.specifications.NotasEvolucionSpecification;

import net.sf.jasperreports.engine.JRException;

@Service
public class HistoriaClinicaService {
    @Autowired
    HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;

    @Autowired
    UsuariosRepositoryJPA usuariosRepositoryJPA;

    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;

    @Autowired
    PDFService pdfService;

    @Autowired
    private ConvertirTiposDatosService convertirTiposDatosService;
    public List<HistoriaClinicaDto> obtenerHistoriasClinicas(String fechaInicio, String fechaFin, String ciPaciente, String nombrePaciente, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
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
    public List<HistoriaClinicaDto> obtenerHistoriasClinicasDePaciente(int idPaciente, String fechaInicio, String fechaFin, String ciPaciente, String nombrePaciente, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findById(idPaciente)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<HistoriaClinicaEntity> historiasEntities = new ArrayList<>();
        Specification<HistoriaClinicaEntity> spec = Specification.where(HistoriasClinicasSpecification.obtenerHistoriasClinicasDePacientePorParametros(idPaciente,convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),nombreMedico,nombreEspecialidad,diagnosticoPresuntivo));
        if(page!=null && size!=null){
            Pageable pageable = PageRequest.of(page, size);
            Page<HistoriaClinicaEntity> historiasEntitiesPage=historiaClinicaRepositoryJPA.findAll(spec,pageable);
            historiasEntities=historiasEntitiesPage.getContent();
        }else{
            historiasEntities=historiaClinicaRepositoryJPA.findAll(spec);
        }  
        return historiasEntities.stream()
                    .map(nota -> new HistoriaClinicaDto().convertirHistoriaClinicaEntityAHistoriaClinicaDto(nota))
                    .toList();
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
    public byte[] obtenerPDFHistoriaClinica(HistoriaClinicaDto historiaClinicaDto) {
        try {
            return pdfService.generatePdfReportHistoriaClinica(historiaClinicaDto);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF de la historia clinica.", e);
        }
    }
}

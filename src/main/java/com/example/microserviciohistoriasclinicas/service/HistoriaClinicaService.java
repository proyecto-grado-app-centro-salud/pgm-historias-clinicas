package com.example.microserviciohistoriasclinicas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Page<HistoriaClinicaDto> obtenerHistoriasClinicas(String fechaInicio, String fechaFin, String ciPaciente, String nombrePaciente, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if(page!=null && size!=null){
            pageable = PageRequest.of(page, size);
        } 
        Specification<HistoriaClinicaEntity> spec = Specification.where(HistoriasClinicasSpecification.obtenerHistoriasClinicasPorParametros(convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),ciPaciente,nombrePaciente,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo));
        Page<HistoriaClinicaEntity> historiasEntitiesPage=historiaClinicaRepositoryJPA.findAll(spec,pageable);
        return historiasEntitiesPage.map(HistoriaClinicaDto::convertirHistoriaClinicaEntityAHistoriaClinicaDto);
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
    public Page<HistoriaClinicaDto> obtenerHistoriasClinicasDePaciente(int idPaciente, String fechaInicio, String fechaFin, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if(page!=null && size!=null){
            pageable = PageRequest.of(page, size);
        } 
        UsuarioEntity usuarioEntity = usuariosRepositoryJPA.findById(idPaciente)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Specification<HistoriaClinicaEntity> spec = Specification.where(HistoriasClinicasSpecification.obtenerHistoriasClinicasDePacientePorParametros(idPaciente,convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),nombreMedico,nombreEspecialidad,diagnosticoPresuntivo));
        Page<HistoriaClinicaEntity> historiasEntitiesPage=historiaClinicaRepositoryJPA.findAll(spec,pageable);
        return historiasEntitiesPage.map(HistoriaClinicaDto::convertirHistoriaClinicaEntityAHistoriaClinicaDto);
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
    public void delete(int id) {
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findByIdHistoriaClinicaAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException("Historia clinica no encontrada"));
        historiaClinicaEntity.markAsDeleted();
        historiaClinicaRepositoryJPA.save(historiaClinicaEntity);
    }
    public void unirHistorias(Map<String,Integer> historias) {
        int idHistoriaDestino=historias.get("idHistoriaDestino");
        int idHistoriaAUnir=historias.get("idHistoriaAUnir");
        delete(idHistoriaAUnir);
        historiaClinicaRepositoryJPA.unirNotasEvolucionDeHistoriaAUnirAHistoriaDestino(idHistoriaAUnir,idHistoriaDestino);
        historiaClinicaRepositoryJPA.unirExamenesComplementariosDeHistoriaAUnirAHistoriaDestino(idHistoriaAUnir,idHistoriaDestino);
        historiaClinicaRepositoryJPA.unirNotasReferenciaDeHistoriaAUnirAHistoriaDestino(idHistoriaAUnir,idHistoriaDestino);
        historiaClinicaRepositoryJPA.unirPapeletasInternacionDeHistoriaAUnirAHistoriaDestino(idHistoriaAUnir,idHistoriaDestino);
        historiaClinicaRepositoryJPA.unirRecetasDeHistoriaAUnirAHistoriaDestino(idHistoriaAUnir,idHistoriaDestino);
        historiaClinicaRepositoryJPA.unirSolicitudesInterconsultaDeHistoriaAUnirAHistoriaDestino(idHistoriaAUnir,idHistoriaDestino);

    }
}

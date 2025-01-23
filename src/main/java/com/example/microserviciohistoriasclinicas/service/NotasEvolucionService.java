package com.example.microserviciohistoriasclinicas.service;

import com.example.microserviciohistoriasclinicas.model.EspecialidadesEntity;
import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;
import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;
import com.example.microserviciohistoriasclinicas.model.dtos.NotaEvolucionDto;
import com.example.microserviciohistoriasclinicas.repository.EspecialidadesRepositoryJPA;
import com.example.microserviciohistoriasclinicas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microserviciohistoriasclinicas.repository.NotaEvolucionRepository;
import com.example.microserviciohistoriasclinicas.repository.UsuariosRepositoryJPA;
import com.example.microserviciohistoriasclinicas.util.specifications.NotasEvolucionSpecification;

import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotasEvolucionService {
    
    @Autowired
    private NotaEvolucionRepository notaEvolucionRepository;

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

    public NotaEvolucionDto registrarNotaEvolucion(NotaEvolucionDto notaEvolucionDto) {
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(notaEvolucionDto.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Medico no encontrado"));
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findByIdHistoriaClinicaAndDeletedAtIsNull(notaEvolucionDto.getIdHistoriaClinica())
        .orElseThrow(() -> new RuntimeException("Historia clinica no encontrada"));
        NotaEvolucionEntity notaEvolucionEntity = new NotaEvolucionEntity();
        notaEvolucionEntity.setCambiosPacienteResultadosTratamiento(notaEvolucionDto.getCambiosPacienteResultadosTratamiento());
        notaEvolucionEntity.setHistoriaClinica(historiaClinicaEntity);
        notaEvolucionEntity.setMedico(medicoEntity);
        notaEvolucionEntity = notaEvolucionRepository.save(notaEvolucionEntity);
        return new NotaEvolucionDto().convertirNotaEvolucionEntityANotaEvolucionDto(notaEvolucionEntity);
    }

    public Page<NotaEvolucionDto> obtenerTodasNotasEvolucion(String fechaInicio, String fechaFin, String ciPaciente, String nombrePaciente, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if(page!=null && size!=null){
            pageable = PageRequest.of(page, size);
        } 
        Specification<NotaEvolucionEntity> spec = Specification.where(NotasEvolucionSpecification.obtenerNotasEvolucionPorParametros(convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),ciPaciente,nombrePaciente,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo));
        Page<NotaEvolucionEntity> notasEntitiesPage=notaEvolucionRepository.findAll(spec,pageable);
        return notasEntitiesPage.map(NotaEvolucionDto::convertirNotaEvolucionEntityANotaEvolucionDto);
    }
    public Page<NotaEvolucionDto> obtenerTodasNotasEvolucionDePaciente(int idPaciente,String fechaInicio, String fechaFin, String nombreMedico, String nombreEspecialidad, String diagnosticoPresuntivo, Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if(page!=null && size!=null){
            pageable = PageRequest.of(page, size);
        } 
        Specification<NotaEvolucionEntity> spec = Specification.where(NotasEvolucionSpecification.obtenerNotasEvolucionDePacientePorParametros(idPaciente,convertirTiposDatosService.convertirStringADate(fechaInicio),convertirTiposDatosService.convertirStringADate(fechaFin),nombreMedico,nombreEspecialidad,diagnosticoPresuntivo));
        Page<NotaEvolucionEntity> notasEntitiesPage=notaEvolucionRepository.findAll(spec,pageable);
        return notasEntitiesPage
                    .map(NotaEvolucionDto::convertirNotaEvolucionEntityANotaEvolucionDto);
    }

    public NotaEvolucionDto obtenerNotaEvolucionPorId(Integer id) {
        NotaEvolucionEntity notaEvolucionEntity = notaEvolucionRepository.findByIdNotaEvolucionAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Nota de evolución no encontrada"));
        return new NotaEvolucionDto().convertirNotaEvolucionEntityANotaEvolucionDto(notaEvolucionEntity);
    }
    public byte[] obtenerPDFNotaEvolucion(NotaEvolucionDto notaEvolucionDto) {
        try {
            return pdfService.generatePdfReportNotaEvolucion(notaEvolucionDto);
        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el PDF de la nota de evolución.", e);
        }
    }

    public NotaEvolucionDto actualizarNotaEvolucion(Integer idNotaEvolucion, NotaEvolucionDto notaEvolucionDto) {
        NotaEvolucionEntity notaEvolucionEntity = notaEvolucionRepository.findByIdNotaEvolucionAndDeletedAtIsNull(idNotaEvolucion)
            .orElseThrow(() -> new RuntimeException("Nota de evolución no encontrada"));
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findByIdUsuarioAndDeletedAtIsNull(notaEvolucionDto.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Medico no encontrado"));
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findByIdHistoriaClinicaAndDeletedAtIsNull(notaEvolucionDto.getIdHistoriaClinica())
        .orElseThrow(() -> new RuntimeException("Historia clinica no encontrada"));
        notaEvolucionEntity.setCambiosPacienteResultadosTratamiento(notaEvolucionDto.getCambiosPacienteResultadosTratamiento());
        notaEvolucionEntity.setHistoriaClinica(historiaClinicaEntity);
        notaEvolucionEntity.setMedico(medicoEntity);
        notaEvolucionEntity = notaEvolucionRepository.save(notaEvolucionEntity);
        notaEvolucionRepository.save(notaEvolucionEntity);
        return new NotaEvolucionDto().convertirNotaEvolucionEntityANotaEvolucionDto(notaEvolucionEntity);
    }

    public void delete(int id) {
        NotaEvolucionEntity notaEvolucionEntity = notaEvolucionRepository.findByIdNotaEvolucionAndDeletedAtIsNull(id)
            .orElseThrow(() -> new RuntimeException("Nota de evolución no encontrada"));
            notaEvolucionEntity.markAsDeleted();
        notaEvolucionRepository.save(notaEvolucionEntity);
    }

    @Transactional
    public void deleteNotasEvolucionDeHistoriaClinica(int idHistoriaClinica) {
       notaEvolucionRepository.markAsDeletedAllNotasEvolucionFromHistoriaClinica(idHistoriaClinica,new Date());
    }
}

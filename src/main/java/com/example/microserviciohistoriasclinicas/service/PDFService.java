package com.example.microserviciohistoriasclinicas.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microserviciohistoriasclinicas.model.EspecialidadesEntity;
import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;
import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;
import com.example.microserviciohistoriasclinicas.model.dtos.HistoriaClinicaDto;
import com.example.microserviciohistoriasclinicas.model.dtos.NotaEvolucionDto;
import com.example.microserviciohistoriasclinicas.repository.EspecialidadesRepositoryJPA;
import com.example.microserviciohistoriasclinicas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microserviciohistoriasclinicas.repository.NotaEvolucionRepository;
import com.example.microserviciohistoriasclinicas.repository.UsuariosRepositoryJPA;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PDFService {
    @Autowired
    UsuariosRepositoryJPA usuariosRepositoryJPA;
    @Autowired
    EspecialidadesRepositoryJPA especialidadesRepositoryJPA;
    @Autowired
    HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;
    @Autowired
    NotaEvolucionRepository notaEvolucionRepository;
    public byte[] generatePdfReportNotaEvolucion(NotaEvolucionDto notaEvolucionDto) throws JRException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        InputStream jrxmlInputStream = getClass().getClassLoader().getResourceAsStream("reports/nota_evolucion.jrxml");
        Optional<NotaEvolucionEntity> notaEvolucionEntityOptional=(notaEvolucionDto.getId()!=null)?notaEvolucionRepository.findById(notaEvolucionDto.getId()):Optional.empty();
        if (jrxmlInputStream == null) {
            throw new JRException("No se pudo encontrar el archivo .jrxml en el classpath.");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
        Map<String, Object> parameters = new HashMap<>();
        if(notaEvolucionEntityOptional.isPresent()){
            notaEvolucionDto=new NotaEvolucionDto().convertirNotaEvolucionEntityANotaEvolucionDto(notaEvolucionEntityOptional.get());
        }else{
            notaEvolucionDto.setCreatedAt(new Date());
            notaEvolucionDto.setUpdatedAt(new Date());
        }
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(notaEvolucionDto.getIdHistoriaClinica()).orElseThrow(() -> new RuntimeException("Historia clinica no encontrada"));
        UsuarioEntity pacienteEntity = usuariosRepositoryJPA.findById(historiaClinicaEntity.getPaciente().getIdUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(notaEvolucionDto.getIdMedico()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        EspecialidadesEntity especialidadesEntity = especialidadesRepositoryJPA.findById(historiaClinicaEntity.getEspecialidad().getIdEspecialidad()).orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        if (jrxmlInputStream == null) {
            throw new JRException("No se pudo encontrar el archivo .jrxml en el classpath.");
        }
       
        parameters.put("cambiosPacienteResultadosTratamiento", notaEvolucionDto.getCambiosPacienteResultadosTratamiento());
        parameters.put("apellidoPaterno", pacienteEntity.getApellidoPaterno());
        parameters.put("apellidoMaterno", pacienteEntity.getApellidoMaterno());
        parameters.put("nombres", pacienteEntity.getNombres());
        parameters.put("nhc", historiaClinicaEntity.getIdHistoriaClinica()+"");
        parameters.put("edad", pacienteEntity.getEdad()+"");
        parameters.put("sexo", pacienteEntity.getSexo());
        parameters.put("estadoCivil",pacienteEntity.getEstadoCivil());
        parameters.put("unidad", especialidadesEntity.getNombre());
        parameters.put("fecha", formato.format(notaEvolucionDto.getUpdatedAt()));

        parameters.put("nombreCompletoPaciente", pacienteEntity.getNombres()+" "+pacienteEntity.getApellidoPaterno());
        parameters.put("nombreCompletoMedico", medicoEntity.getNombres()+" "+medicoEntity.getApellidoPaterno());
        parameters.put("firmaPaciente", "");
        parameters.put("firmaMedico", "");
        parameters.put("IMAGE_PATH", getClass().getClassLoader().getResource("images/logo.jpeg").getPath());
        List<NotaEvolucionDto> list = new ArrayList<>();
        list.add(notaEvolucionDto);

        JRBeanCollectionDataSource emptyDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, emptyDataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

       return outputStream.toByteArray();
    }

    public byte[] generatePdfReportHistoriaClinica(HistoriaClinicaDto historiaClinicaDto) throws JRException{
        Optional<HistoriaClinicaEntity> historiaClinicaEntityOptional=(historiaClinicaDto.getId()!=null)?historiaClinicaRepositoryJPA.findById(historiaClinicaDto.getId()):Optional.empty();
        if(historiaClinicaEntityOptional.isPresent()){
            historiaClinicaDto=new HistoriaClinicaDto().convertirHistoriaClinicaEntityAHistoriaClinicaDto(historiaClinicaEntityOptional.get());
        }else{
            historiaClinicaDto.setCreatedAt(new Date());
            historiaClinicaDto.setUpdatedAt(new Date());
        }
        InputStream jrxmlInputStream = getClass().getClassLoader().getResourceAsStream("reports/historia_clinica.jrxml");
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(historiaClinicaDto.getIdMedico()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UsuarioEntity pacienteEntity = usuariosRepositoryJPA.findById(historiaClinicaDto.getIdPaciente()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        EspecialidadesEntity especialidadesEntity = especialidadesRepositoryJPA.findById(historiaClinicaDto.getIdEspecialidad()).orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        if (jrxmlInputStream == null) {
            throw new JRException("No se pudo encontrar el archivo .jrxml en el classpath.");
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("antecedentesPersonales",historiaClinicaDto.getAntecedentesPersonales() );
        parameters.put("antecedentesFamiliares",historiaClinicaDto.getAntecedentesFamiliares() );
        parameters.put("antecedentesPatologicos",historiaClinicaDto.getAntecedentesPatologicos() );
        parameters.put("antecedentesNoPatologicos",historiaClinicaDto.getAntecedentesNoPatologicos() );
        parameters.put("antecedentesGinecoobstetricos",historiaClinicaDto.getAntecedentesGinecoobstetricos() );
        parameters.put("condicionesActuales",historiaClinicaDto.getExamenFisico() );
        parameters.put("examenFisicoGeneral",historiaClinicaDto.getExamenFisicoEspecial() );
        parameters.put("examenFisicoEspecial",historiaClinicaDto.getExamenFisicoEspecial() );
        parameters.put("diagnosticoPresuntivo",historiaClinicaDto.getDiagnosticoPresuntivo() );
        parameters.put("diagnosticoDiferencial",historiaClinicaDto.getDiagnosticosDiferenciales() );
        parameters.put("tratamiento",historiaClinicaDto.getTratamiento() );
        parameters.put("propuestaBasicaDeConducta",historiaClinicaDto.getPropuestaBasicaDeConducta() );

        parameters.put("apellidoPaterno", pacienteEntity.getApellidoPaterno());
        parameters.put("apellidoMaterno", pacienteEntity.getApellidoMaterno());
        parameters.put("nombres", pacienteEntity.getNombres());
        parameters.put("nhc", historiaClinicaDto.getId()+"");
        parameters.put("edad", pacienteEntity.getEdad()+"");
        parameters.put("sexo", pacienteEntity.getSexo());
        parameters.put("estadoCivil", pacienteEntity.getEstadoCivil());
        parameters.put("unidad", especialidadesEntity.getNombre());
        
        parameters.put("fecha", formato.format(historiaClinicaDto.getUpdatedAt()));
        parameters.put("nombreCompletoPaciente", pacienteEntity.getNombres()+" "+pacienteEntity.getApellidoPaterno());
        parameters.put("nombreCompletoMedico", medicoEntity.getNombres()+" "+medicoEntity.getApellidoPaterno());
        parameters.put("firmaPaciente", "");
        parameters.put("firmaMedico", "");
        parameters.put("IMAGE_PATH", getClass().getClassLoader().getResource("images/logo.jpeg").getPath());
        List<HistoriaClinicaDto> list = new ArrayList<>();
        list.add(historiaClinicaDto);

        JRBeanCollectionDataSource emptyDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, emptyDataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

       return outputStream.toByteArray();
    }
}

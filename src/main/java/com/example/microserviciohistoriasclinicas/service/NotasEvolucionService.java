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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public NotaEvolucionDto registrarNotaEvolucion(NotaEvolucionDto notaEvolucionDto) {
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(notaEvolucionDto.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Medico no encontrado"));
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(notaEvolucionDto.getIdHistoriaClinica())
        .orElseThrow(() -> new RuntimeException("Historia clinica no encontrada"));
        NotaEvolucionEntity notaEvolucionEntity = new NotaEvolucionEntity();
        notaEvolucionEntity.setCambiosPacienteResultadosTratamiento(notaEvolucionDto.getCambiosPacienteResultadosTratamiento());
        notaEvolucionEntity.setHistoriaClinica(historiaClinicaEntity);
        notaEvolucionEntity.setMedico(medicoEntity);
        notaEvolucionEntity = notaEvolucionRepository.save(notaEvolucionEntity);
        return new NotaEvolucionDto().convertirNotaEvolucionEntityANotaEvolucionDto(notaEvolucionEntity);
    }

    public List<NotaEvolucionDto> obtenerTodasNotasEvolucion() {
        List<NotaEvolucionEntity> notas = notaEvolucionRepository.findAll();
        return notas.stream()
                    .map(nota -> new NotaEvolucionDto().convertirNotaEvolucionEntityANotaEvolucionDto(nota))
                    .toList();
    }
    public List<NotaEvolucionDto> obtenerTodasNotasEvolucionDePaciente(int idPaciente) {
    
        List<NotaEvolucionEntity> notas = notaEvolucionRepository.obtenerNotasEvolucionPaciente(idPaciente);
        return notas.stream()
                    .map(nota -> new NotaEvolucionDto().convertirNotaEvolucionEntityANotaEvolucionDto(nota))
                    .toList();
    }

    public NotaEvolucionDto obtenerNotaEvolucionPorId(Integer id) {
        NotaEvolucionEntity notaEvolucionEntity = notaEvolucionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nota de evolución no encontrada"));
        return new NotaEvolucionDto().convertirNotaEvolucionEntityANotaEvolucionDto(notaEvolucionEntity);
    }

    public NotaEvolucionDto actualizarNotaEvolucion(Integer idNotaEvolucion, NotaEvolucionDto notaEvolucionDto) {
        NotaEvolucionEntity notaEvolucionEntity = notaEvolucionRepository.findById(idNotaEvolucion)
            .orElseThrow(() -> new RuntimeException("Nota de evolución no encontrada"));
        UsuarioEntity medicoEntity = usuariosRepositoryJPA.findById(notaEvolucionDto.getIdMedico())
        .orElseThrow(() -> new RuntimeException("Medico no encontrado"));
        HistoriaClinicaEntity historiaClinicaEntity = historiaClinicaRepositoryJPA.findById(notaEvolucionDto.getIdHistoriaClinica())
        .orElseThrow(() -> new RuntimeException("Historia clinica no encontrada"));
        notaEvolucionEntity.setCambiosPacienteResultadosTratamiento(notaEvolucionDto.getCambiosPacienteResultadosTratamiento());
        notaEvolucionEntity.setHistoriaClinica(historiaClinicaEntity);
        notaEvolucionEntity.setMedico(medicoEntity);
        notaEvolucionEntity = notaEvolucionRepository.save(notaEvolucionEntity);
        notaEvolucionRepository.save(notaEvolucionEntity);
        return new NotaEvolucionDto().convertirNotaEvolucionEntityANotaEvolucionDto(notaEvolucionEntity);
    }
}

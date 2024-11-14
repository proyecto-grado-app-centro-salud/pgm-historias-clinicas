package com.example.microserviciohistoriasclinicas.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.dtos.HistoriaClinicaDto;
import com.example.microserviciohistoriasclinicas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microserviciohistoriasclinicas.service.ContainerMetadataService;
import com.example.microserviciohistoriasclinicas.service.HistoriaClinicaService;
@RestController
@RequestMapping(path = "/historias-clinicas")
public class HistoriaClinicaController {
    @Autowired
    HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;
    @Autowired
    HistoriaClinicaService historiaClinicaService;

    @Autowired
	private ContainerMetadataService containerMetadataService;

    Logger logger = LoggerFactory.getLogger(HistoriaClinicaController.class);
    @PostMapping()
    public ResponseEntity<HistoriaClinicaDto> registrarHistoriaClinica(@RequestBody HistoriaClinicaDto historiaClinicaDto) {
        try {
            HistoriaClinicaDto historiaClinicaCreada = historiaClinicaService.crearHistoriaClinica(historiaClinicaDto);
            return new ResponseEntity<>(historiaClinicaCreada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinicaDto> actualizarHistoriaClinica(@PathVariable Integer id, @RequestBody HistoriaClinicaDto actualizada) {
        try {
            HistoriaClinicaDto historiaClinicaActualizada = historiaClinicaService.actualizarHistoriaClinica(id,actualizada);
            return new ResponseEntity<HistoriaClinicaDto>(historiaClinicaActualizada, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<HistoriaClinicaDto>> controllerMethod(@PathVariable int idPaciente) {
        try {
            return new ResponseEntity<List<HistoriaClinicaDto>>(historiaClinicaService.obtenerHistoriasClinicasDePaciente(idPaciente),HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @GetMapping("/{idHistoriaClinica}")
    public ResponseEntity<HistoriaClinicaDto> obtenerDetalleHistoriaClinica(@PathVariable int idHistoriaClinica) {
        try {
            HistoriaClinicaDto historiaClinicaDto = historiaClinicaService.obtenerHistoriaClinicaPorId(idHistoriaClinica);
            return new ResponseEntity<>(historiaClinicaDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // @GetMapping("/nueva-ci-tarde")
    // public @ResponseBody String obtenerNuevaCi() {
    //     return "OK nueva ci tarde ";
    // }

    @GetMapping("/nueva-ci-noche")
    public @ResponseBody String obtenerNuevaCi() {
        return "OK nueva ci noche ";
    }
    @GetMapping()
    public ResponseEntity<List<HistoriaClinicaDto>> obtenerTodasHistoriasClinicas() {
        try {
            return new ResponseEntity<List<HistoriaClinicaDto>>(historiaClinicaService.obtenerHistoriasClinicas(),HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
}

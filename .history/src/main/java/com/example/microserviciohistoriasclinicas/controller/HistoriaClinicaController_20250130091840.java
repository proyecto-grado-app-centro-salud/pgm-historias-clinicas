package com.example.microserviciohistoriasclinicas.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.model.dtos.HistoriaClinicaDto;
import com.example.microserviciohistoriasclinicas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microserviciohistoriasclinicas.service.ContainerMetadataService;
import com.example.microserviciohistoriasclinicas.service.HistoriaClinicaService;

import jakarta.annotation.security.PermitAll;
@RestController
@RequestMapping(path = "/historias-clinicas")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
public class HistoriaClinicaController {
    @Autowired
    HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;
    @Autowired
    HistoriaClinicaService historiaClinicaService;

    @Autowired
	private ContainerMetadataService containerMetadataService;

    Logger logger = LoggerFactory.getLogger(HistoriaClinicaController.class);

    @PreAuthorize("hasAnyAuthority('MEDICO')")
    @PostMapping()
    public ResponseEntity<HistoriaClinicaDto> registrarHistoriaClinica(@RequestBody HistoriaClinicaDto historiaClinicaDto) {
        try {
            HistoriaClinicaDto historiaClinicaCreada = historiaClinicaService.crearHistoriaClinica(historiaClinicaDto);
            return new ResponseEntity<>(historiaClinicaCreada, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('MEDICO')")
    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinicaDto> actualizarHistoriaClinica(@PathVariable Integer id, @RequestBody HistoriaClinicaDto actualizada) {
        try {
            HistoriaClinicaDto historiaClinicaActualizada = historiaClinicaService.actualizarHistoriaClinica(id,actualizada);
            return new ResponseEntity<HistoriaClinicaDto>(historiaClinicaActualizada, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/unir-historias-clinicas")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR','SUPERUSUARIO')")
    public ResponseEntity<Void> unirHistorias(@RequestBody Map<String,Integer> historias) {
        try {
            historiaClinicaService.unirHistorias(historias);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PermitAll
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<Page<HistoriaClinicaDto>> controllerMethod(@PathVariable String idPaciente,@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin ,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            return new ResponseEntity<Page<HistoriaClinicaDto>>(historiaClinicaService.obtenerHistoriasClinicasDePaciente(idPaciente,fechaInicio,fechaFin,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PermitAll
    @GetMapping("/{idHistoriaClinica}")
    public ResponseEntity<HistoriaClinicaDto> obtenerDetalleHistoriaClinica(@PathVariable int idHistoriaClinica) {
        try {
            HistoriaClinicaDto historiaClinicaDto = historiaClinicaService.obtenerHistoriaClinicaPorId(idHistoriaClinica);
            return new ResponseEntity<>(historiaClinicaDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // @GetMapping("/nueva-ci-tarde")
    // public @ResponseBody String obtenerNuevaCi() {
    //     return "OK nueva ci tarde ";
    // }


    @PermitAll
    @GetMapping("/nueva-ci-noche")
    public @ResponseBody String obtenerNuevaCi() {
        return "OK nueva ci noche ";
    }

    @PermitAll
    @GetMapping()
    public ResponseEntity<Page<HistoriaClinicaDto>> obtenerTodasHistoriasClinicas(@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,@RequestParam(required = false) String ciPaciente,@RequestParam(required = false) String nombrePaciente,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            return new ResponseEntity<Page<HistoriaClinicaDto>>(historiaClinicaService.obtenerHistoriasClinicas(fechaInicio,fechaFin,ciPaciente,nombrePaciente,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR','SUPERUSUARIO')")
     @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try{
            historiaClinicaService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PermitAll
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> obtenerPDFDeHistoriaClinica(HistoriaClinicaDto historiaClinicaDto) {
        try {
            byte[] pdfBytes = historiaClinicaService.obtenerPDFHistoriaClinica(historiaClinicaDto);
            // Path path = Paths.get("HistoriaClinica.pdf");
            // Files.write(path, pdfBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=HistoriaClinica.pdf");
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Length", "" + pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }


    @PermitAll
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }

    @GetMapping("/info")
    @PermitAll
    public ResponseEntity<String> ultimaActualizacion() {
        return new ResponseEntity<String>("012922025",HttpStatus.OK);
    }
}

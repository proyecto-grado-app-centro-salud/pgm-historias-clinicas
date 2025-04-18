package com.example.microserviciohistoriasclinicas.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;
import com.example.microserviciohistoriasclinicas.model.dtos.NotaEvolucionDto;
import com.example.microserviciohistoriasclinicas.repository.NotaEvolucionRepository;
import com.example.microserviciohistoriasclinicas.service.ContainerMetadataService;
import com.example.microserviciohistoriasclinicas.service.NotasEvolucionService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping(path = "/notas-evolucion")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
public class NotaEvolucionController {
    @Autowired
	private ContainerMetadataService containerMetadataService;
    @Autowired
    NotasEvolucionService notasEvolucionService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MEDICO')")
    public ResponseEntity<NotaEvolucionDto> registrarNotaEvolucion(@RequestBody NotaEvolucionDto notaEvolucionDto) {
        try {
            NotaEvolucionDto nuevaNota = notasEvolucionService.registrarNotaEvolucion(notaEvolucionDto);
            return new ResponseEntity<>(nuevaNota, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR','SUPERUSUARIO')")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try{
            notasEvolucionService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    @PermitAll
    public ResponseEntity<Page<NotaEvolucionDto>> obtenerTodasNotasEvolucion(@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,@RequestParam(required = false) String ciPaciente,@RequestParam(required = false) String nombrePaciente,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            Page<NotaEvolucionDto> notas = notasEvolucionService.obtenerTodasNotasEvolucion(fechaInicio,fechaFin,ciPaciente,nombrePaciente,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size);
            return new ResponseEntity<>(notas, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<NotaEvolucionDto> obtenerNotaEvolucionPorId(@PathVariable Integer id) {
        try {
            NotaEvolucionDto nota = notasEvolucionService.obtenerNotaEvolucionPorId(id);
            return new ResponseEntity<>(nota, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/pdf")
    @PermitAll
    public ResponseEntity<byte[]> obtenerNotaEvolucionPDFPorId(NotaEvolucionDto notaEvolucionDto) {
        try {
            byte[] pdfBytes = notasEvolucionService.obtenerPDFNotaEvolucion(notaEvolucionDto);
            // Path path = Paths.get("NotaEvolucion.pdf");
            // Files.write(path, pdfBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=NotaEvolucion.pdf");
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Length", "" + pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MEDICO')")
    public ResponseEntity<NotaEvolucionDto> actualizarNotaEvolucion(@PathVariable Integer id, @RequestBody NotaEvolucionDto actualizada) {
        try {
            NotaEvolucionDto notaActualizada = notasEvolucionService.actualizarNotaEvolucion(id,actualizada);
            return new ResponseEntity<>(notaActualizada, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/paciente/{idPaciente}")
    @PermitAll
    public ResponseEntity<Page<NotaEvolucionDto>> obtenerNotasEvolucionPaciente(@PathVariable String idPaciente,@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            Page<NotaEvolucionDto> notas = notasEvolucionService.obtenerTodasNotasEvolucionDePaciente(idPaciente,fechaInicio,fechaFin,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size);
            return new ResponseEntity<>(notas, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/info-container")
    @PermitAll
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
}

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;
import com.example.microserviciohistoriasclinicas.model.dtos.NotaEvolucionDto;
import com.example.microserviciohistoriasclinicas.repository.NotaEvolucionRepository;
import com.example.microserviciohistoriasclinicas.service.ContainerMetadataService;
import com.example.microserviciohistoriasclinicas.service.NotasEvolucionService;

@RestController
@RequestMapping(path = "/notas-evolucion")
public class NotaEvolucionController {
    @Autowired
	private ContainerMetadataService containerMetadataService;
    @Autowired
    NotasEvolucionService notasEvolucionService;

    @PostMapping
    public ResponseEntity<NotaEvolucionDto> registrarNotaEvolucion(@RequestBody NotaEvolucionDto notaEvolucionDto) {
        try {
            NotaEvolucionDto nuevaNota = notasEvolucionService.registrarNotaEvolucion(notaEvolucionDto);
            return new ResponseEntity<>(nuevaNota, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<NotaEvolucionDto>> obtenerTodasNotasEvolucion(@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,@RequestParam(required = false) String ciPaciente,@RequestParam(required = false) String nombrePaciente,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            List<NotaEvolucionDto> notas = notasEvolucionService.obtenerTodasNotasEvolucion(fechaInicio,fechaFin,ciPaciente,nombrePaciente,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size);
            return new ResponseEntity<>(notas, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<List<NotaEvolucionDto>> obtenerNotasEvolucionPaciente(@PathVariable int idPaciente,@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,@RequestParam(required = false) String nombreMedico,@RequestParam(required = false) String nombreEspecialidad,@RequestParam(required = false) String diagnosticoPresuntivo,@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {
        try {
            List<NotaEvolucionDto> notas = notasEvolucionService.obtenerTodasNotasEvolucionDePaciente(idPaciente,fechaInicio,fechaFin,nombreMedico,nombreEspecialidad,diagnosticoPresuntivo,page,size);
            return new ResponseEntity<>(notas, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
}

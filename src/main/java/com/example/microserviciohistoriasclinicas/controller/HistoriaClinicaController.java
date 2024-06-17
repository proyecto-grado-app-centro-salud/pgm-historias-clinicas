package com.example.microserviciohistoriasclinicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.repository.HistoriaClinicaRepositoryJPA;
import com.example.microserviciohistoriasclinicas.service.ContainerMetadataService;
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/historias-clinicas")
public class HistoriaClinicaController {
    @Autowired
    HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;

    @Autowired
	private ContainerMetadataService containerMetadataService;
    @PostMapping()
    public @ResponseBody HistoriaClinicaEntity registrarHistoriaClinica(@RequestBody HistoriaClinicaEntity nuevo){
        historiaClinicaRepositoryJPA.save(nuevo);
        return nuevo;
    }
    @PutMapping("/{id}")
    public @ResponseBody HistoriaClinicaEntity actualizarHistoriaClinica(@PathVariable Integer id, @RequestBody HistoriaClinicaEntity actualizada) {
        return historiaClinicaRepositoryJPA.findById(id)
                .map(historiaClinica -> {
                    historiaClinica.setAmnesis(actualizada.getAmnesis());
                    historiaClinica.setAntecedentesFamiliares(actualizada.getAntecedentesFamiliares());
                    historiaClinica.setAntecedentesGinecoobstetricos(actualizada.getAntecedentesGinecoobstetricos());
                    historiaClinica.setAntecedentesNoPatologicos(actualizada.getAntecedentesNoPatologicos());
                    historiaClinica.setAntecedentesPersonales(actualizada.getAntecedentesPersonales());
                    historiaClinica.setDiagnosticoPresuntivo(actualizada.getDiagnosticoPresuntivo());
                    historiaClinica.setDiagnosticosDiferenciales(actualizada.getDiagnosticosDiferenciales());
                    historiaClinica.setExamenFisico(actualizada.getExamenFisico());
                    historiaClinica.setExamenFisicoEspecial(actualizada.getExamenFisicoEspecial());
                    historiaClinica.setPropuestaBasicaDeConducta(actualizada.getPropuestaBasicaDeConducta());
                    historiaClinica.setTratamiento(actualizada.getTratamiento());
                    historiaClinica.setIdPaciente(actualizada.getIdPaciente());
                    historiaClinica.setIdEspecialidad(actualizada.getIdEspecialidad());
                    historiaClinica.setIdMedico(actualizada.getIdMedico());
                    historiaClinicaRepositoryJPA.save(historiaClinica);
                    return actualizada;
                })
                .orElseGet(() -> {
                    return actualizada;
                });
    }
    @GetMapping("/paciente/{idPaciente}")
    public @ResponseBody List<HistoriaClinicaEntity> controllerMethod(@PathVariable int idPaciente) {
        return historiaClinicaRepositoryJPA.findByIdPaciente(idPaciente);
    }
    @GetMapping("/{idHistoriaClinica}")
    public @ResponseBody HistoriaClinicaEntity obtenerDetalleHistoriaClinica(@PathVariable int idHistoriaClinica) {
        return historiaClinicaRepositoryJPA.findById(idHistoriaClinica)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Historia clínica con ID " + idHistoriaClinica + " no encontrada"));
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
    public @ResponseBody List<HistoriaClinicaEntity> obtenerTodasHistoriasClinicas() {
        return historiaClinicaRepositoryJPA.findAll();
    }
    @GetMapping("/info-container")
    public @ResponseBody String obtenerInformacionContenedor() {
        return "microservicio historias clinicas:" + containerMetadataService.retrieveContainerMetadataInfo();
    }
}

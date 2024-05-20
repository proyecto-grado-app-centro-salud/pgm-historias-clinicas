package com.example.microserviciohistoriasclinicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
import com.example.microserviciohistoriasclinicas.repository.HistoriaClinicaRepositoryJPA;
@Controller
@RequestMapping(path = "/historias-clinicas")
public class HistoriaClinicaController {
    @Autowired
    HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;

    @PostMapping()
    public @ResponseBody String registrarReserva(@RequestBody HistoriaClinicaEntity nuevo){
        historiaClinicaRepositoryJPA.save(nuevo);
        return "Ok";
    }
    @GetMapping("/{idPaciente}")
    public @ResponseBody List<HistoriaClinicaEntity> controllerMethod(@PathVariable int idPaciente) {
        return historiaClinicaRepositoryJPA.findByIdPaciente(idPaciente);
    }
}

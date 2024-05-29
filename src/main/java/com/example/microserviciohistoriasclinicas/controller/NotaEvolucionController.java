package com.example.microserviciohistoriasclinicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;
import com.example.microserviciohistoriasclinicas.repository.NotaEvolucionRepository;
import com.example.microserviciohistoriasclinicas.service.ContainerMetadataService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/notas-evolucion")
public class NotaEvolucionController {
    @Autowired
	private ContainerMetadataService containerMetadataService;
    @Autowired
    NotaEvolucionRepository recetasRepository;

    @PostMapping()
    public @ResponseBody String registrarNotaEvolucion(@RequestBody NotaEvolucionEntity notaEvolucionEntity){
        recetasRepository.save(notaEvolucionEntity);
        return "Ok";
    }
}

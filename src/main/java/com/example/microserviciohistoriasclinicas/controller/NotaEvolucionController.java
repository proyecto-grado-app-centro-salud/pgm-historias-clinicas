package com.example.microserviciohistoriasclinicas.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;
import com.example.microserviciohistoriasclinicas.model.dtos.NotaEvolucionDto;
import com.example.microserviciohistoriasclinicas.repository.NotaEvolucionRepository;
import com.example.microserviciohistoriasclinicas.service.ContainerMetadataService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/notas-evolucion")
public class NotaEvolucionController {
    @Autowired
	private ContainerMetadataService containerMetadataService;
    @Autowired
    NotaEvolucionRepository notaEvolucionRepository;

    @PostMapping()
    public @ResponseBody String registrarNotaEvolucion(@RequestBody NotaEvolucionEntity notaEvolucionEntity){
        notaEvolucionRepository.save(notaEvolucionEntity);
        return "Ok";
    }
    @PutMapping("/{id}")
    public @ResponseBody String actualizarNotaEvolucion(@PathVariable Integer id, @RequestBody NotaEvolucionEntity actualizada) {
        return notaEvolucionRepository.findById(id)
                .map(notaEvolucion -> {
                    notaEvolucion.setCambiosPacienteResultadosTratamiento(actualizada.getCambiosPacienteResultadosTratamiento());
                    notaEvolucion.setUpdatedAt(new Date());
                    notaEvolucion.setIdMedico(actualizada.getIdMedico());
                    notaEvolucionRepository.save(notaEvolucion);
                    return "Nota evolucion actualizada con éxito";
                })
                .orElseGet(() -> {
                    return "Error en la actualizacion";
                });
    }


    @GetMapping("/{idNotaEvolucionSolicitada}")
    public @ResponseBody NotaEvolucionDto obtenerDetalleNotaEvolucion(@PathVariable int idNotaEvolucionSolicitada) {
        Object[] elemento=notaEvolucionRepository.obtenerNotaEvolucionPorId(idNotaEvolucionSolicitada).get(0);
        Integer idNotaEvolucion=(Integer)elemento[0];
        Integer idHistoriaClinica=(Integer)elemento[1];
        String cambiosPacienteResultadosTratamiento=(String)elemento[2];
        Integer idMedico=(Integer)elemento[3];
        Date createdAt=(Date)elemento[4];
        Date updatedAt=(Date)elemento[5];
        Date deletedAt=(Date)elemento[6];
        String pacientePropietario=(String)elemento[7];
        String ciPropietario=(String)elemento[8];
        return new NotaEvolucionDto(idNotaEvolucion,idHistoriaClinica,cambiosPacienteResultadosTratamiento,idMedico,createdAt,updatedAt,deletedAt,pacientePropietario,ciPropietario);
        // return recetasRepository.findById(idReceta)
        // .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la peticion"));
    }
    @GetMapping()
    public @ResponseBody List<NotaEvolucionDto> obtenerTodasRecetas() {
        List<Object[]>  infoNotasEvolucion=notaEvolucionRepository.obtenerNotasEvolucion();
        return infoNotasEvolucion.stream().map((Object[] elemento) -> {
            Integer idNotaEvolucion=(Integer)elemento[0];
            Integer idHistoriaClinica=(Integer)elemento[1];
            String cambiosPacienteResultadosTratamiento=(String)elemento[2];
            Integer idMedico=(Integer)elemento[3];
            Date createdAt=(Date)elemento[4];
            Date updatedAt=(Date)elemento[5];
            Date deletedAt=(Date)elemento[6];
            String pacientePropietario=(String)elemento[7];
            String ciPropietario=(String)elemento[8];
            return new NotaEvolucionDto(idNotaEvolucion,idHistoriaClinica,cambiosPacienteResultadosTratamiento,idMedico,createdAt,updatedAt,deletedAt,pacientePropietario,ciPropietario);
        }).collect(Collectors.toList());
        // return recetasRepository.obtenerRecetas();
    }
    @GetMapping("/paciente/{idPaciente}")
    public @ResponseBody List<NotaEvolucionDto> obtenerRecetasPaciente(@PathVariable int idPaciente) {
        List<Object[]>  infoNotasEvolucion=notaEvolucionRepository.obtenerNotasEvolucionPaciente(idPaciente);
        return infoNotasEvolucion.stream().map((Object[] elemento) -> {
            Integer idNotaEvolucion=(Integer)elemento[0];
            Integer idHistoriaClinica=(Integer)elemento[1];
            String cambiosPacienteResultadosTratamiento=(String)elemento[2];
            Integer idMedico=(Integer)elemento[3];
            Date createdAt=(Date)elemento[4];
            Date updatedAt=(Date)elemento[5];
            Date deletedAt=(Date)elemento[6];
            String pacientePropietario=(String)elemento[7];
            String ciPropietario=(String)elemento[8];
            return new NotaEvolucionDto(idNotaEvolucion,idHistoriaClinica,cambiosPacienteResultadosTratamiento,idMedico,createdAt,updatedAt,deletedAt,pacientePropietario,ciPropietario);
        }).collect(Collectors.toList());
        //return recetasRepository.findByIdPaciente(idReceta);
        
    }
}

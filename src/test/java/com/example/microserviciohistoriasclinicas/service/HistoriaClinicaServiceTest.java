// package com.example.microserviciohistoriasclinicas.service;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.Arrays;
// import java.util.Date;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import com.example.microserviciohistoriasclinicas.model.EspecialidadesEntity;
// import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
// import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;
// import com.example.microserviciohistoriasclinicas.model.dtos.HistoriaClinicaDto;
// import com.example.microserviciohistoriasclinicas.repository.EspecialidadesRepositoryJPA;
// import com.example.microserviciohistoriasclinicas.repository.HistoriaClinicaRepositoryJPA;
// import com.example.microserviciohistoriasclinicas.repository.UsuariosRepositoryJPA;


// public class HistoriaClinicaServiceTest {
    
//     @InjectMocks
//     private HistoriaClinicaService historiaClinicaService;

//     @Mock
//     private HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;

//     @Mock
//     private UsuariosRepositoryJPA usuariosRepositoryJPA;

//     @Mock
//     private EspecialidadesRepositoryJPA especialidadesRepositoryJPA;

//     private HistoriaClinicaEntity historiaClinicaEntity;
//     private HistoriaClinicaDto historiaClinicaDto;
//     private UsuarioEntity pacienteEntity;
//     private UsuarioEntity medicoEntity;
//     private EspecialidadesEntity especialidadEntity;

//     @BeforeEach
//     public void beforeAll() {
//         MockitoAnnotations.openMocks(this);
        
//         pacienteEntity = new UsuarioEntity();
//         pacienteEntity.setIdUsuario(1);
//         pacienteEntity.setNombres("Juan");
//         pacienteEntity.setApellidoPaterno("Pérez");
//         pacienteEntity.setApellidoMaterno("González");

//         medicoEntity = new UsuarioEntity();
//         medicoEntity.setIdUsuario(2);
//         medicoEntity.setNombres("Ana");
//         medicoEntity.setApellidoPaterno("López");
//         medicoEntity.setApellidoMaterno("Martínez");

//         especialidadEntity = new EspecialidadesEntity();
//         especialidadEntity.setIdEspecialidad(3);
//         especialidadEntity.setNombre("Pediatría");

//         historiaClinicaEntity = new HistoriaClinicaEntity();
//         historiaClinicaEntity.setIdHistoriaClinica(1);
//         historiaClinicaEntity.setAmnesis("Amnesia temporal");
//         historiaClinicaEntity.setPaciente(pacienteEntity);
//         historiaClinicaEntity.setMedico(medicoEntity);
//         historiaClinicaEntity.setEspecialidad(especialidadEntity);
//         historiaClinicaEntity.setCreatedAt(new Date());
//         historiaClinicaEntity.setUpdatedAt(new Date());

//         historiaClinicaDto = new HistoriaClinicaDto();
//         historiaClinicaDto.setId(1);
//         historiaClinicaDto.setIdPaciente(1);
//         historiaClinicaDto.setIdMedico(2);
//         historiaClinicaDto.setIdEspecialidad(3);
//         historiaClinicaDto.setAmnesis("Amnesia temporal");
//     }

//     @Test
//     public void obtenerHistoriasClinicas_HistoriasClinicasExisten_Verdadero() {
//         when(historiaClinicaRepositoryJPA.findAll()).thenReturn(Arrays.asList(historiaClinicaEntity));

//         List<HistoriaClinicaDto> result = historiaClinicaService.obtenerHistoriasClinicas();

//         assertNotNull(result);
//         assertEquals(1, result.size());
//     }

//     @Test
//     public void actualizarHistoriaClinica_HistoriaClinicaCorrecta_Verdadero() {
//         when(usuariosRepositoryJPA.findById(1)).thenReturn(Optional.of(pacienteEntity));
//         when(usuariosRepositoryJPA.findById(2)).thenReturn(Optional.of(medicoEntity));
//         when(especialidadesRepositoryJPA.findById(3)).thenReturn(Optional.of(especialidadEntity));
//         when(historiaClinicaRepositoryJPA.findById(1)).thenReturn(Optional.of(historiaClinicaEntity));

//         HistoriaClinicaDto resultado = historiaClinicaService.actualizarHistoriaClinica(1, historiaClinicaDto);

//         assertNotNull(resultado);
//     }

//     @Test
//     public void obtenerHistoriasClinicasDePaciente_ExisteHistoriaClinica_Verdadero() {
//         when(usuariosRepositoryJPA.findById(1)).thenReturn(Optional.of(pacienteEntity));
//         when(historiaClinicaRepositoryJPA.findByPaciente(pacienteEntity)).thenReturn(Arrays.asList(historiaClinicaEntity));

//         List<HistoriaClinicaDto> result = historiaClinicaService.obtenerHistoriasClinicasDePaciente(1);

//         assertNotNull(result);
//         assertEquals(1, result.size());
//     }
// }
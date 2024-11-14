// package com.example.microserviciohistoriasclinicas.model;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import java.util.Date;

// import org.junit.Test;
// import org.junit.jupiter.api.BeforeAll;

// import com.example.microserviciohistoriasclinicas.model.dtos.HistoriaClinicaDto;


// public class HistoriaClinicaDtoTest {
//     private static UsuarioEntity pacienteEntity=new UsuarioEntity();
//     private static UsuarioEntity medicoEntity=new UsuarioEntity();
//     private static HistoriaClinicaEntity historiaClinicaEntity=new HistoriaClinicaEntity();
//     private static EspecialidadesEntity especialidadesEntity=new EspecialidadesEntity();
//     @BeforeAll
//     static void beforeAll() {
//         especialidadesEntity.setIdEspecialidad(1);
//         especialidadesEntity.setNombre("Pediatría");

//         medicoEntity.setIdUsuario(1);
//         medicoEntity.setNombres("Juan");
//         medicoEntity.setApellidoPaterno("Pérez");
//         medicoEntity.setApellidoMaterno("González");

//         pacienteEntity.setIdUsuario(1);
//         pacienteEntity.setNombres("Pedro");
//         pacienteEntity.setApellidoPaterno("Pérez");
//         pacienteEntity.setApellidoMaterno("González");

//     }

//     @Test
//     public void convertirHistoriaClinicaEntityAHistoriaClinicaDto_HistoriaClinicaDtoCorrecta_Verdadero() {
//         historiaClinicaEntity.setIdHistoriaClinica(1);
//         historiaClinicaEntity.setMedico(medicoEntity);
//         historiaClinicaEntity.setEspecialidad(especialidadesEntity);
//         historiaClinicaEntity.setAmnesis("Paciente con dolor de cabeza");
//         historiaClinicaEntity.setAntecedentesFamiliares("Sin antecedentes familiares relevantes");
//         historiaClinicaEntity.setPaciente(pacienteEntity);
//         historiaClinicaEntity.setCreatedAt(new Date());
//         HistoriaClinicaDto resultado = new HistoriaClinicaDto().convertirHistoriaClinicaEntityAHistoriaClinicaDto(historiaClinicaEntity);

//         assertNotNull(resultado);
//         assertEquals(historiaClinicaEntity.getIdHistoriaClinica(), resultado.getId());
//         assertEquals(historiaClinicaEntity.getAmnesis(), resultado.getAmnesis());
//         assertEquals(historiaClinicaEntity.getAntecedentesFamiliares(), resultado.getAntecedentesFamiliares());
//         assertEquals(historiaClinicaEntity.getAntecedentesPatologicos(), resultado.getAntecedentesPatologicos());
//         assertEquals(historiaClinicaEntity.getAntecedentesPersonales(), resultado.getAntecedentesPersonales());
//         assertEquals(historiaClinicaEntity.getDiagnosticoPresuntivo(), resultado.getDiagnosticoPresuntivo());
//         assertEquals(historiaClinicaEntity.getDiagnosticosDiferenciales(), resultado.getDiagnosticosDiferenciales());
//         assertEquals(historiaClinicaEntity.getPropuestaBasicaDeConducta(), resultado.getPropuestaBasicaDeConducta());
//         assertEquals(historiaClinicaEntity.getTratamiento(), resultado.getTratamiento());
//         assertEquals(historiaClinicaEntity.getPaciente().getIdUsuario(), resultado.getIdPaciente());
//         assertEquals(historiaClinicaEntity.getMedico().getIdUsuario(), resultado.getIdMedico());
//         assertEquals(historiaClinicaEntity.getEspecialidad().getIdEspecialidad(), resultado.getIdEspecialidad());
//     }
// }

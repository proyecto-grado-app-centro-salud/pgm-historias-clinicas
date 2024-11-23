// package com.example.microserviciohistoriasclinicas.repository;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import java.util.Date;
// import java.util.List;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.annotation.Rollback;

// import com.example.microserviciohistoriasclinicas.model.EspecialidadesEntity;
// import com.example.microserviciohistoriasclinicas.model.HistoriaClinicaEntity;
// import com.example.microserviciohistoriasclinicas.model.UsuarioEntity;

// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
// @DataJpaTest
// public class HistoriaClinicaRepositoryJPATest {
//     @Autowired
//     private HistoriaClinicaRepositoryJPA historiaClinicaRepositoryJPA;
//     @Autowired
//     private UsuariosRepositoryJPA usuariosRepositoryJPA;
//     @Autowired
//     private EspecialidadesRepositoryJPA especialidadesRepositoryJPA;

//     private static UsuarioEntity pacienteEntity=new UsuarioEntity();
//     private static UsuarioEntity medicoEntity=new UsuarioEntity();
//     private static HistoriaClinicaEntity historiaClinicaEntity=new HistoriaClinicaEntity();
//     private static EspecialidadesEntity especialidadesEntity=new EspecialidadesEntity();

//     @BeforeAll
//     static void beforeAll() {
//         especialidadesEntity.setNombre("Pediatría");

//         medicoEntity.setNombres("Juan");
//         medicoEntity.setApellidoPaterno("Pérez");
//         medicoEntity.setApellidoMaterno("González");

//         pacienteEntity.setNombres("Pedro");
//         pacienteEntity.setApellidoPaterno("Pérez");
//         pacienteEntity.setApellidoMaterno("González");

//         historiaClinicaEntity.setMedico(medicoEntity);
//         historiaClinicaEntity.setEspecialidad(especialidadesEntity);
//         historiaClinicaEntity.setAmnesis("Paciente con dolor de cabeza");
//         historiaClinicaEntity.setAntecedentesFamiliares("Sin antecedentes familiares relevantes");
//         historiaClinicaEntity.setPaciente(pacienteEntity);
//         historiaClinicaEntity.setCreatedAt(new Date());
//     }
//     @Test
//     @Rollback(value = true)
//     public void findByPaciente_retornaHistoriasClinicas_Encontradas() {
//         usuariosRepositoryJPA.save(pacienteEntity);
//         usuariosRepositoryJPA.save(medicoEntity);
//         especialidadesRepositoryJPA.save(especialidadesEntity);
//         historiaClinicaRepositoryJPA.save(historiaClinicaEntity);
//         List<HistoriaClinicaEntity> historias = historiaClinicaRepositoryJPA.findByPaciente(pacienteEntity);
//         assertNotNull(historias);
//         assertThat(historias.get(0).getPaciente()).isEqualTo(pacienteEntity);
//     }
//     @Test
//     public void save_historiaClinicaCorrecta_Verdadero() {
//         usuariosRepositoryJPA.save(pacienteEntity);
//         usuariosRepositoryJPA.save(medicoEntity);
//         especialidadesRepositoryJPA.save(especialidadesEntity);
//         HistoriaClinicaEntity savedEntity = historiaClinicaRepositoryJPA.save(historiaClinicaEntity);
//         assertThat(savedEntity).isNotNull();
//         assertThat(savedEntity.getIdHistoriaClinica()).isGreaterThan(0);
//     }
//     @Test
//     public void findAll_historiasClinicasExistentes_Verdadero() {
//         usuariosRepositoryJPA.save(pacienteEntity);
//         usuariosRepositoryJPA.save(medicoEntity);
//         especialidadesRepositoryJPA.save(especialidadesEntity);
//         historiaClinicaRepositoryJPA.save(historiaClinicaEntity);
//         List<HistoriaClinicaEntity> historiasClinicas = historiaClinicaRepositoryJPA.findAll();
//         assertThat(historiasClinicas).isNotEmpty();
//         assertThat(historiasClinicas.size()).isGreaterThan(0);
//     }

// }

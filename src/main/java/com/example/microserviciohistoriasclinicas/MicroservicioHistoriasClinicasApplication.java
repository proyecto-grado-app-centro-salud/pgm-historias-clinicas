package com.example.microserviciohistoriasclinicas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MicroservicioHistoriasClinicasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioHistoriasClinicasApplication.class, args);
	}

}

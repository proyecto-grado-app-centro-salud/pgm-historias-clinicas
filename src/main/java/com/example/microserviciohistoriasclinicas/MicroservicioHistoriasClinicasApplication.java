package com.example.microserviciohistoriasclinicas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
@SpringBootApplication
public class MicroservicioHistoriasClinicasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioHistoriasClinicasApplication.class, args);
	}

}

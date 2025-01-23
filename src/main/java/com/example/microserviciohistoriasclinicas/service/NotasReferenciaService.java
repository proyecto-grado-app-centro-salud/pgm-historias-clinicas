package com.example.microserviciohistoriasclinicas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import reactor.core.publisher.Mono;

@Service
public class NotasReferenciaService {
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    private WebClient webClient;
    Logger logger = LoggerFactory.getLogger(NotasReferenciaService.class);

    @Value("${url.lb}")
    private String lb;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(lb + "/api/microservicio-notas-referencia").build();
    }

    public void eliminarNotasReferenciaDeHistoriaClinica(int idHistoriaClinica) {
        logger.info("eliminarNRDeHistoriaClinica");
        logger.info(lb);

        this.webClient.delete()
                .uri("/notas-referencia/historia-clinica/"+idHistoriaClinica)
                .retrieve()
                .onStatus(
                    status -> !status.is2xxSuccessful(), 
                    clientResponse -> manejarError(clientResponse) 
                )
                .bodyToMono(Void.class)
                .block();
    }

    private Mono<? extends Throwable> manejarError(ClientResponse clientResponse) {
        int statusCode = clientResponse.statusCode().value();
       
        if (statusCode == 403) {
            return Mono.error(new NotFoundException("Datos enviados incorrectos"));
        } else if (statusCode == 500) {
            return Mono.error(new InternalServerErrorException("Error interno del servidor"));
        }
       
        return Mono.error(new Exception(""));
    }
}

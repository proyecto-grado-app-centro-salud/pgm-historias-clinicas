package com.example.microserviciohistoriasclinicas.service;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;

@Service
public class ExamenesComplementariosService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    
    private WebClient webClient;
    Logger logger = LoggerFactory.getLogger(ExamenesComplementariosService.class);

    @Value("${url.lb}")
    private String lb;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(lb + "/api/microservicio-examenes-complementarios").build();
    }

    public void eliminarExamenesComplemetariosDeHistoriaClinica(int idHistoriaClinica) {
        logger.info("eliminarExamenesComplemetariosDeHistoriaClinica");
        logger.info(lb);

        this.webClient.delete()
                .uri("/examenes-complementarios/historia-clinica/"+idHistoriaClinica)
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

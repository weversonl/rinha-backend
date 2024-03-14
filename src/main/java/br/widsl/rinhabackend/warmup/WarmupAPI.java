package br.widsl.rinhabackend.warmup;

import java.time.LocalDate;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import br.widsl.rinhabackend.domain.dto.PersonDTO;
import br.widsl.rinhabackend.service.PersonService;
import reactor.core.publisher.Mono;

@Component
public class WarmupAPI implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarmupAPI.class);

    private final PersonService personService;

    private final WebClient webClient;

    private final String serverPort;

    private Random rand = new Random();

    public WarmupAPI(PersonService personService, WebClient webClient,
            @Value("${server.port}") String serverPort) {
        this.personService = personService;
        this.webClient = webClient;
        this.serverPort = serverPort;
    }

    @Override
    @SuppressWarnings("null")
    public void run(ApplicationArguments args) {

        PersonDTO person = new PersonDTO("warmup" + rand.nextInt(500),
                "warmup", LocalDate.parse("2024-01-01"), new String[] {});

        String postURI = "http://localhost:%s/pessoas".formatted(serverPort);
        String getURI = "http://localhost:%s/pessoas?t=warmup".formatted(serverPort);
        String idURI = "http://localhost:%s/pessoas/9fba4ea3-5601-49c5-88dd-b6eec5e9b161".formatted(serverPort);

        this.webClient.post()
                .uri(postURI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(person))
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        this.webClient.get()
                .uri(getURI)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        this.webClient.get()
                .uri(idURI)
                .retrieve()
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), response -> Mono.empty())
                .bodyToMono(Object.class)
                .block();

        this.personService
                .cleanUpPersons()
                .blockLast();

        LOGGER.info("-> carga inicial completa <-");

    }

}

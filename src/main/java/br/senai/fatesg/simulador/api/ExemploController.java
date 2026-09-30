package br.senai.fatesg.simulador.api;

import br.senai.fatesg.simulador.api.dto.CenarioRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RF-07: devolve o caso de referência de {@code CenariosExemplo} para o
 * botão "Carregar exemplo". Implementação completa no Passo 5.
 */
@RestController
public class ExemploController {

    @GetMapping("/api/exemplo")
    public CenarioRequest exemplo() {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
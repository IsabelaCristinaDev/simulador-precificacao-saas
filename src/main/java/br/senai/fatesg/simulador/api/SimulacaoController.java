package br.senai.fatesg.simulador.api;

import br.senai.fatesg.simulador.api.dto.CenarioRequest;
import br.senai.fatesg.simulador.api.dto.SimulacaoResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * RF-01 a RF-04: calcula um cenário. Implementação completa (conversão
 * para {@code CenarioEntrada}, chamada a {@code CalculadoraPrecificacao}
 * e {@code AnaliseSensibilidade}) no Passo 5.
 */
@RestController
public class SimulacaoController {

    @PostMapping("/api/simulacoes")
    public SimulacaoResponse calcular(@Valid @RequestBody CenarioRequest request) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
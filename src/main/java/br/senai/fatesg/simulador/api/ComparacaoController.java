package br.senai.fatesg.simulador.api;

import br.senai.fatesg.simulador.api.dto.CenarioRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RF-05: compara os três cenários informados, delegando a
 * {@code ComparacaoCenarios} e {@code InterpretacaoTextual}.
 * Implementação completa (incluindo o DTO de resposta) no Passo 5.
 */
@RestController
public class ComparacaoController {

    @PostMapping("/api/comparacoes")
    public Object comparar(@Valid @RequestBody List<CenarioRequest> cenarios) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
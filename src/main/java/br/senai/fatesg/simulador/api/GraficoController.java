package br.senai.fatesg.simulador.api;

import br.senai.fatesg.simulador.api.dto.CenarioRequest;
import br.senai.fatesg.simulador.calculo.PontoGrafico;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RF-06: gera a série de pontos do gráfico para o cenário informado,
 * delegando a {@code GeradorSerieGrafico}. Implementação completa no
 * Passo 5.
 */
@RestController
public class GraficoController {

    @PostMapping("/api/graficos")
    public List<PontoGrafico> gerar(@Valid @RequestBody CenarioRequest request) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
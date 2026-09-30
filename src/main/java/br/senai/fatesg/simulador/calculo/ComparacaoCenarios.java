package br.senai.fatesg.simulador.calculo;

import java.util.List;

/**
 * Comparação de cenários (RF-05): identifica quais premissas mudam entre
 * os cenários informados e quais deles sustentam (resultado ≥ 0) o
 * volume de clientes informado. Java puro (RNF-04) — implementação
 * completa no Passo 5.
 */
public class ComparacaoCenarios {

    private ComparacaoCenarios() {
    }

    /**
     * @param premissasAlteradas nomes dos campos que diferem entre os cenários comparados
     * @param sustentavel        um booleano por cenário, na mesma ordem de entrada, indicando resultado >= 0
     */
    public record Resultado(List<String> premissasAlteradas, List<Boolean> sustentavel) {
    }

    public static Resultado comparar(List<CenarioEntrada> cenarios, List<ResultadoSimulacao> resultados) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
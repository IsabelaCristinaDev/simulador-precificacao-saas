package br.senai.fatesg.simulador.calculo;

import java.util.List;

/**
 * Texto explicativo do resultado (RF-10): indica se o cenário é
 * sustentável no volume informado citando o indicador que sustenta a
 * afirmação, menciona explicitamente as premissas e o horizonte mensal,
 * trata o resultado como equilíbrio operacional do mês (nunca afirma
 * recuperação de investimento) e evita frases de certeza absoluta sobre
 * o futuro. Java puro (RNF-04) — implementação completa no Passo 5.
 */
public class InterpretacaoTextual {

    private InterpretacaoTextual() {
    }

    /** Interpretação de um único cenário calculado. */
    public static String interpretar(CenarioEntrada cenario, ResultadoSimulacao resultado) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }

    /**
     * Interpretação de uma comparação de cenários (RF-05/RF-10): se
     * nenhum cenário tiver resultado ≥ 0, não recomenda um preço.
     */
    public static String interpretarComparacao(List<CenarioEntrada> cenarios, List<ResultadoSimulacao> resultados) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
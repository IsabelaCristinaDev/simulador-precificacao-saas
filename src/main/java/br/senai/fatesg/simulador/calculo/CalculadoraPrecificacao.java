package br.senai.fatesg.simulador.calculo;

/**
 * Núcleo de cálculo (RNF-04): implementa as fórmulas fixadas em
 * {@code requisitos.md} §2 e {@code modelo_calculos.md} §3 (receita,
 * custo variável total, custo total, tributos, resultado, contribuição
 * unitária, margem e clientes de equilíbrio), em {@link java.math.BigDecimal}
 * (RNF-01). Implementação completa no Passo 5 — a assinatura é fixada
 * agora para orientar a API e os testes.
 */
public class CalculadoraPrecificacao {

    private CalculadoraPrecificacao() {
    }

    /**
     * Calcula todas as saídas de {@link ResultadoSimulacao} para um
     * cenário já validado.
     */
    public static ResultadoSimulacao calcular(CenarioEntrada cenario) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
package br.senai.fatesg.simulador.calculo;

import java.math.BigDecimal;

/**
 * Um cenário de simulação já validado (RF-01, RF-08). Java puro, sem
 * dependência do Spring — testável isoladamente por JUnit 5 (RNF-04).
 * A validação aqui existe para que os cálculos nunca operem sobre um
 * cenário inválido, mesmo quando criado fora da API (ex.: em
 * {@code CenariosExemplo} ou diretamente em testes) — é a mesma regra
 * que as anotações Bean Validation do DTO da API já impõem na entrada
 * HTTP.
 */
public record CenarioEntrada(
        BigDecimal custoFixo,
        BigDecimal custoVariavel,
        BigDecimal preco,
        int clientes,
        BigDecimal taxaPct,
        String beneficioPercebido) {

    public CenarioEntrada {
        exigirNaoNegativo(custoFixo, "custoFixo");
        exigirNaoNegativo(custoVariavel, "custoVariavel");
        exigirNaoNegativo(preco, "preco");
        if (clientes < 0) {
            throw new IllegalArgumentException("clientes deve ser >= 0");
        }
        if (taxaPct == null
                || taxaPct.compareTo(BigDecimal.ZERO) < 0
                || taxaPct.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("taxaPct deve estar entre 0 e 100");
        }
    }

    private static void exigirNaoNegativo(BigDecimal valor, String nomeCampo) {
        if (valor == null || valor.signum() < 0) {
            throw new IllegalArgumentException(nomeCampo + " deve ser >= 0");
        }
    }
}
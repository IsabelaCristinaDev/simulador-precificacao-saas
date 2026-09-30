package br.senai.fatesg.simulador.calculo;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Saída de uma simulação (RF-01 a RF-04). Estrutura de dados pura; os
 * valores são produzidos por {@link CalculadoraPrecificacao} (Passo 5).
 */
public record ResultadoSimulacao(
        BigDecimal receita,
        BigDecimal custoVariavelTotal,
        BigDecimal custoTotal,
        BigDecimal tributos,
        BigDecimal resultado,
        BigDecimal contribuicaoUnitaria,
        Optional<BigDecimal> margemPercentual,
        Optional<Integer> clientesEquilibrio,
        Optional<String> mensagemSemEquilibrio) {
}
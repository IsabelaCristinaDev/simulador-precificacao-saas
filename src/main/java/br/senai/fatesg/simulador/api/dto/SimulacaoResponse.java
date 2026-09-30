package br.senai.fatesg.simulador.api.dto;

import java.math.BigDecimal;

/**
 * Saída JSON de uma simulação, mapeada a partir de
 * {@link br.senai.fatesg.simulador.calculo.ResultadoSimulacao} pelos
 * controllers (Passo 5). Campos opcionais ({@code margemPercentual},
 * {@code clientesEquilibrio}, {@code mensagemSemEquilibrio}) usam tipos
 * anuláveis, não {@code Optional}, por serem um DTO serializado em JSON.
 */
public record SimulacaoResponse(
        BigDecimal receita,
        BigDecimal custoVariavelTotal,
        BigDecimal custoTotal,
        BigDecimal tributos,
        BigDecimal resultado,
        BigDecimal contribuicaoUnitaria,
        BigDecimal margemPercentual,
        Integer clientesEquilibrio,
        String mensagemSemEquilibrio) {
}
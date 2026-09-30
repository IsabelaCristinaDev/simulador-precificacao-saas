package br.senai.fatesg.simulador.api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Entrada de um cenário vinda da tela (RF-01, RF-08). {@code clientes} é
 * {@link BigDecimal}, não {@code int}/{@code Integer}: o Jackson trunca
 * silenciosamente um valor como {@code 10.5} para {@code 10} ao
 * desserializar para um tipo inteiro (DeserializationFeature
 * .ACCEPT_FLOAT_AS_INT = true por padrão); recebendo como
 * {@code BigDecimal}, o valor decimal chega intacto e
 * {@link Digits @Digits(fraction = 0)} rejeita qualquer casa decimal na
 * validação.
 */
public record CenarioRequest(
        @NotNull @DecimalMin(value = "0", message = "custoFixo deve ser >= 0")
        BigDecimal custoFixo,

        @NotNull @DecimalMin(value = "0", message = "custoVariavel deve ser >= 0")
        BigDecimal custoVariavel,

        @NotNull @DecimalMin(value = "0", message = "preco deve ser >= 0")
        BigDecimal preco,

        @NotNull
        @DecimalMin(value = "0", message = "clientes deve ser >= 0")
        @Digits(integer = 10, fraction = 0, message = "clientes deve ser um número inteiro")
        BigDecimal clientes,

        @NotNull
        @DecimalMin(value = "0", message = "taxaPct deve estar entre 0 e 100")
        @DecimalMax(value = "100", message = "taxaPct deve estar entre 0 e 100")
        BigDecimal taxaPct,

        @NotBlank(message = "beneficioPercebido é obrigatório")
        String beneficioPercebido) {
}
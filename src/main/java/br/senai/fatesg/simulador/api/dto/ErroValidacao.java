package br.senai.fatesg.simulador.api.dto;

/**
 * Erro de validação de um campo (RF-08), retornado pelo
 * {@link br.senai.fatesg.simulador.api.ApiExceptionHandler}.
 */
public record ErroValidacao(String campo, String mensagem) {
}
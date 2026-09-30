package br.senai.fatesg.simulador.calculo;

import java.math.BigDecimal;

/** Um ponto (clientes, resultado) da série do gráfico de RF-06. */
public record PontoGrafico(int clientes, BigDecimal resultado) {
}
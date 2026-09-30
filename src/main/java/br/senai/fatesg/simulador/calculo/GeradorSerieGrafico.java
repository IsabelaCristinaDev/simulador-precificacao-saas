package br.senai.fatesg.simulador.calculo;

import java.util.List;

/**
 * Gera a série de pontos do gráfico de resultado por quantidade de
 * clientes (RF-06), aplicando o intervalo, o passo e os marcadores
 * obrigatórios decididos em {@code modelo_calculos.md} §9. Implementação
 * completa no Passo 5.
 */
public class GeradorSerieGrafico {

    private GeradorSerieGrafico() {
    }

    /** Gera a série completa (incluindo os marcadores de {@code clientes} e {@code clientes_equilibrio}). */
    public static List<PontoGrafico> gerar(CenarioEntrada cenario) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
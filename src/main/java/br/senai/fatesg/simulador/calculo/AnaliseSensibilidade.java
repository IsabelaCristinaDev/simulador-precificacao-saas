package br.senai.fatesg.simulador.calculo;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Análise de sensibilidade (RF-11): preço que zera o resultado e
 * quantidade mínima de clientes, incluindo os casos sem solução de
 * {@code modelo_calculos.md} §10.1 (clientes = 0; taxa = 100%).
 * Implementação completa no Passo 5.
 */
public class AnaliseSensibilidade {

    private AnaliseSensibilidade() {
    }

    /**
     * Preço que zera o resultado para a quantidade de clientes do
     * cenário, já arredondado para cima ao centavo para exibição
     * ({@code modelo_calculos.md} §7); vazio quando não calculável
     * (clientes = 0 ou taxa = 100%).
     */
    public static Optional<BigDecimal> precoQueZeraResultado(CenarioEntrada cenario) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }

    /**
     * Quantidade mínima de clientes para o preço do cenário
     * (equivalente a {@code clientes_equilibrio}).
     */
    public static Optional<Integer> clientesMinimos(CenarioEntrada cenario) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
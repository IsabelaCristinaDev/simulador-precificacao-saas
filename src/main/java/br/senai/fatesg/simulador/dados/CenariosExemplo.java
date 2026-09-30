package br.senai.fatesg.simulador.dados;

import br.senai.fatesg.simulador.calculo.CenarioEntrada;

import java.math.BigDecimal;
import java.util.List;

/**
 * Dados de exemplo fictícios (RNF-03): o caso de referência de
 * {@code requisitos.md} §6 (caso 1) e os três cenários de preço de
 * RF-05/RF-07 (R$ 40 / R$ 50 / R$ 70). Apenas dados — nenhuma lógica de
 * cálculo.
 */
public final class CenariosExemplo {

    private static final String BENEFICIO_EXEMPLO =
            "Simulação: economia de tempo na gestão financeira do cliente";

    public static final CenarioEntrada CASO_REFERENCIA = new CenarioEntrada(
            new BigDecimal("3000"),
            new BigDecimal("10"),
            new BigDecimal("50"),
            100,
            new BigDecimal("10"),
            BENEFICIO_EXEMPLO);

    /** RF-05: mesmos custo fixo, custo variável, clientes e taxa; só o preço muda. */
    public static final List<CenarioEntrada> CENARIOS_COMPARACAO = List.of(
            new CenarioEntrada(new BigDecimal("3000"), new BigDecimal("10"), new BigDecimal("40"), 100, new BigDecimal("10"), BENEFICIO_EXEMPLO),
            CASO_REFERENCIA,
            new CenarioEntrada(new BigDecimal("3000"), new BigDecimal("10"), new BigDecimal("70"), 100, new BigDecimal("10"), BENEFICIO_EXEMPLO));

    private CenariosExemplo() {
    }
}
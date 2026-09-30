package br.senai.fatesg.simulador.api.dto;

/** Um cenário completo para exportação (RF-09): entrada e saída já calculadas. */
public record CenarioExportavel(CenarioRequest entrada, SimulacaoResponse saida) {
}
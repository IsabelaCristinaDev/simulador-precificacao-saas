package br.senai.fatesg.simulador.api.dto;

import java.util.List;

/**
 * Corpo de {@code POST /api/exportacoes} (RF-09). A aplicação é
 * stateless (sem persistência — {@code requisitos.md} §1), então não há
 * de onde buscar cenários por um GET: o cliente reenvia no corpo os
 * cenários que já calculou.
 */
public record ExportacaoRequest(List<CenarioExportavel> cenarios) {
}
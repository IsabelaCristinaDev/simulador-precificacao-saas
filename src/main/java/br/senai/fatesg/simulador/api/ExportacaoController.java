package br.senai.fatesg.simulador.api;

import br.senai.fatesg.simulador.api.dto.ExportacaoRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RF-09: exporta em CSV ou JSON os cenários já calculados. Como a
 * aplicação é stateless (sem persistência — {@code requisitos.md} §1),
 * não há de onde buscar os cenários por um GET: o cliente reenvia no
 * corpo ({@link ExportacaoRequest}) os cenários e o benefício percebido
 * que já calculou. Implementação completa no Passo 5.
 */
@RestController
public class ExportacaoController {

    @PostMapping("/api/exportacoes")
    public ResponseEntity<String> exportar(@RequestParam String formato,
                                            @Valid @RequestBody ExportacaoRequest request) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
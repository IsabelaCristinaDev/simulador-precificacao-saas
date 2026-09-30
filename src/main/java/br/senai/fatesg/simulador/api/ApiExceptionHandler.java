package br.senai.fatesg.simulador.api;

import br.senai.fatesg.simulador.api.dto.ErroValidacao;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * RF-08: traduz em mensagens por campo, para a tela, tanto as violações
 * de Bean Validation dos DTOs quanto o {@link IllegalArgumentException}
 * lançado pelo construtor de {@code CenarioEntrada} (rede de segurança
 * caso um cenário inválido seja montado sem passar pelos DTOs).
 * Implementação completa no Passo 5.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroValidacao> tratarValidacao(MethodArgumentNotValidException ex) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErroValidacao tratarIllegalArgument(IllegalArgumentException ex) {
        throw new UnsupportedOperationException("Implementado no Passo 5");
    }
}
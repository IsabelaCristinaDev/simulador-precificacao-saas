package br.senai.fatesg.simulador.api.dto;

import tools.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * RF-08: o Jackson desserializa {@code clientes} sem truncar valores
 * decimais porque o campo é {@link java.math.BigDecimal}, não
 * {@code int}; {@code @Digits(fraction = 0)} então rejeita o valor na
 * validação. O teste passa pelo caminho real da requisição
 * (JSON -> ObjectMapper -> Validator), não só pela construção direta do
 * record, para confirmar que o Jackson não trunca antes.
 */
class CenarioRequestValidationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void rejeitaClientesComCasaDecimalNoCaminhoRealDaRequisicao() throws Exception {
        String json = """
                {
                  "custoFixo": 3000,
                  "custoVariavel": 10,
                  "preco": 50,
                  "clientes": 10.5,
                  "taxaPct": 10,
                  "beneficioPercebido": "Simulação: caso de teste"
                }
                """;

        CenarioRequest request = objectMapper.readValue(json, CenarioRequest.class);

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<CenarioRequest>> violacoes = validator.validate(request);

            assertTrue(violacoes.stream()
                    .anyMatch(v -> v.getPropertyPath().toString().equals("clientes")));
        }
    }
}
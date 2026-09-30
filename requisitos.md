# Requisitos — Simulador de Precificação de um SaaS (Opção 2)

Projeto acadêmico de Engenharia Econômica (SENAI FATESG). Este documento é o
Passo 2 do roteiro da atividade: transforma a análise revisada (Passo 1) em
requisitos numerados com critérios de aceitação. Ainda não há código —
implementação começa no Passo 4/5.

## 0. Decisão apoiada

Um profissional de engenharia de software precisa decidir **qual preço
mensal cobrar por um SaaS e com quantos clientes o serviço deixa de dar
prejuízo**, dado um custo fixo mensal, um custo variável por cliente e uma
taxa hipotética de tributos sobre a receita.

## 1. Convenções e premissas (aprovadas no Passo 1)

- Moeda: reais (R$). Unidade de tempo: mensal (sem conversão anual).
- Taxa de tributos é **hipotética, editável, meramente ilustrativa** para
  fins didáticos — não representa nenhuma alíquota legal real. Cada
  simulação registra o valor de taxa usado.
- Taxa é digitada em percentual na tela (0 a 100) e convertida em fração
  (÷100) para o cálculo.
- Quantidade de clientes: número inteiro ≥ 0.
- Sem persistência em banco de dados: cada simulação é calculada sob
  demanda (stateless); comparação de cenários acontece na mesma sessão.
- O "benefício percebido pelo cliente" é um campo de texto livre, salvo
  junto do cenário e incluído na exportação, **sem** entrar em nenhuma
  fórmula.
- Dados de exemplo são fictícios e identificados como simulação.
- O resultado calculado é o saldo operacional de um modelo didático, não
  lucro contábil.
- O modelo mede apenas o **equilíbrio operacional do mês**. Ele não mede
  recuperação de investimento.
- Cálculos ficam no backend (Java/Spring Boot), em funções puras testáveis
  por JUnit 5, independentes da tela; o frontend (HTML/CSS/JS puro) consome
  os resultados via API REST.

## 2. Fórmulas (fixadas — não alterar)

```
receita               = preco * clientes
tributos              = receita * taxa
resultado             = receita - custo_fixo - custo_variavel * clientes - tributos
contribuicao_unitaria = preco * (1 - taxa) - custo_variavel
margem_percentual     = 100 * resultado / receita         (somente se receita > 0)
clientes_equilibrio   = teto(custo_fixo / contribuicao_unitaria)   (somente se contribuicao_unitaria > 0)

custo_variavel_total  = custo_variavel * clientes
custo_total           = custo_fixo + custo_variavel_total
```

`custo_variavel_total` e `custo_total` são fórmulas auxiliares de saída,
não alteram as fórmulas do PDF.

Se `contribuicao_unitaria <= 0` e `custo_fixo > 0`: não existe
`clientes_equilibrio` numérico — a aplicação deve informar que **não há
equilíbrio por aumento de volume nesse modelo**.

## 3. Requisitos funcionais obrigatórios

### RF-01 — Cadastro de um cenário
- **Entrada:** custo fixo mensal, custo variável por cliente, preço
  mensal, quantidade de clientes, taxa de tributos (%), benefício
  percebido (texto).
- **Ação:** usuário preenche o formulário e aciona "Calcular".
- **Resultado observável:** a aplicação exibe receita, custo variável
  total, custo total, tributos, resultado, contribuição unitária, margem
  (quando aplicável) e clientes de equilíbrio (ou a mensagem de não
  equilíbrio) para aquele cenário.

### RF-02 — Cálculo de receita, custos, tributos e resultado
- **Entrada:** Fixos R$ 3.000; variável R$ 10; preço R$ 50; clientes 100;
  taxa 10%.
- **Ação:** sistema aplica as fórmulas da seção 2.
- **Resultado observável:** receita R$ 5.000,00; custo variável total
  R$ 1.000,00; custo total R$ 4.000,00; tributos R$ 500,00; resultado
  R$ 500,00 (tolerância R$ 0,01).

### RF-03 — Contribuição unitária e margem
- **Entrada:** mesmos dados do RF-02.
- **Ação:** sistema calcula contribuição unitária e, apenas se receita > 0,
  a margem percentual.
- **Resultado observável:** contribuição unitária R$ 35,00; margem 10,00%.
  Se preço = 0 ou clientes = 0 (receita = 0), a tela mostra "margem não
  aplicável (receita zero)" em vez de erro ou divisão por zero.

### RF-04 — Clientes de equilíbrio e caso sem equilíbrio
- **Entrada:** contribuição unitária e custo fixo do cenário.
- **Ação:** se contribuição unitária > 0, calcula
  `teto(custo_fixo / contribuicao_unitaria)`; caso contrário (≤ 0) e custo
  fixo > 0, não calcula número algum.
- **Resultado observável:** caso de referência (RF-02) → 86 clientes; caso
  com preço R$ 8 e custo variável R$ 10 (contribuição negativa) → mensagem
  "não há equilíbrio por aumento de volume nesse modelo", sem número de
  clientes.

### RF-05 — Comparação de três cenários
- **Entrada:** três conjuntos de premissas. Cenários aprovados: preços
  **R$ 40 / R$ 50 / R$ 70**, com custo fixo R$ 3.000, variável R$ 10,
  100 clientes e taxa 10%.
- **Ação:** usuário aciona "Comparar cenários".
- **Resultado observável:** tabela lado a lado com receita, tributos,
  resultado, margem e clientes de equilíbrio dos três cenários,
  **destacando quais premissas mudam entre eles**, permitindo identificar
  quais sustentam o volume informado.
- **Simplificação assumida:** a quantidade de clientes é mantida em 100
  nos três preços. Na prática, alterar o preço tende a alterar a demanda;
  o modelo não considera esse efeito.

### RF-06 — Gráfico de resultado por quantidade de clientes
- **Entrada:** um cenário válido (custo fixo, variável, preço, taxa).
- **Ação:** sistema recalcula o resultado variando a quantidade de
  clientes em um intervalo.
- **Resultado observável:** gráfico mostrando resultado (eixo Y) por
  quantidade de clientes (eixo X), com o ponto de equilíbrio identificável
  quando existir. Intervalo e passo conforme `modelo_calculos.md` §9
  (decidido).

### RF-07 — Exemplo pronto para carregar
- **Entrada:** usuário aciona "Carregar exemplo".
- **Ação:** sistema preenche o formulário com o caso de referência.
- **Resultado observável:** campos preenchidos com Fixos 3.000, variável
  10, preço 50, clientes 100, taxa 10% e um benefício percebido de
  exemplo; ao calcular, resultado bate com RF-02/RF-03/RF-04.

### RF-08 — Validação de entradas inválidas
- **Entrada:** campo obrigatório vazio, valor negativo onde não faz
  sentido (custo fixo, custo variável, preço ou clientes negativos), taxa
  fora de 0–100%, valor não numérico, ou **quantidade de clientes não
  inteira (ex.: 10,5)**.
- **Ação:** usuário tenta calcular.
- **Resultado observável:** aplicação não calcula; mostra mensagem de erro
  junto ao campo inválido; nenhum resultado anterior permanece exibido
  como se fosse válido para os novos dados.

### RF-09 — Exportação de dados (obrigatório)
- **Entrada:** um ou mais cenários já calculados.
- **Ação:** usuário aciona "Exportar" (CSV ou JSON).
- **Resultado observável:** arquivo exportado contém, por cenário: as
  entradas (incluindo benefício percebido), as premissas (moeda,
  periodicidade, taxa hipotética usada, origem dos dados) e as saídas
  (receita, custo variável total, custo total, tributos, resultado,
  contribuição unitária, margem, clientes de equilíbrio ou mensagem de
  não equilíbrio).

### RF-10 — Interpretação textual dos resultados
- **Entrada:** resultado calculado de um ou mais cenários.
- **Ação:** sistema gera um texto explicativo.
- **Resultado observável:** a mensagem:
    - indica se o cenário é sustentável no volume informado e cita o
      indicador que sustenta a afirmação (ex. "resultado positivo de
      R$ 500,00, margem de 10%");
    - **menciona explicitamente as premissas e o horizonte (mensal)**
      utilizados;
    - trata o resultado como **equilíbrio operacional do mês** e nunca
      afirma recuperação de investimento;
    - evita frases de certeza absoluta sobre o futuro;
    - **se nenhum cenário comparado tiver resultado ≥ 0, não recomenda um
      preço** e informa que nenhum cenário sustenta o volume nas premissas
      consideradas.

### RF-11 — Análise de sensibilidade (obrigatório)
- **Entrada:** um cenário válido.
- **Ação:** sistema varia uma entrada por vez para encontrar o limite que
  altera a decisão.
- **Resultado observável:**
    - **preço que zera o resultado** para a quantidade de clientes
      informada (caso de referência: R$ 4.000 / 90 = R$ 44,444..., exibido
      R$ 44,45);
    - **quantidade mínima de clientes** para o preço informado (caso de
      referência: 86 clientes);
    - com clientes = 0, o preço que zera o resultado não é calculado
      ("não se aplica com zero clientes"); com taxa = 100%, não existe
      ("com tributo de 100% sobre a receita, nenhum preço cobre os custos").
      Ver `modelo_calculos.md` §10.1.

## 4. Requisitos não funcionais / técnicos obrigatórios

- **RNF-01 — Precisão monetária:** cálculos com `BigDecimal`; comparações
  com tolerância de R$ 0,01; arredondamento só na apresentação (2 casas);
  clientes de equilíbrio = menor inteiro ≥ valor calculado.
- **RNF-02 — Unidades e premissas visíveis:** tela e exportação declaram
  moeda (R$), periodicidade (mensal) e deixam claro que a taxa de
  tributos é hipotética/editável, não uma alíquota legal.
- **RNF-03 — Dados fictícios identificados:** qualquer dado de exemplo é
  rotulado como simulação.
- **RNF-04 — Separação cálculo/interface:** funções de cálculo no backend
  Java, cobertas por testes JUnit 5, sem depender da tela.
- **RNF-05 — Execução local:** backend Spring Boot + frontend estático
  executáveis localmente; gráfico implementado sem dependência de CDN/rede
  (a confirmar a técnica no Passo 6).
- **RNF-06 — Documentação:** README identifica o grupo, explica os
  indicadores calculados e traz instruções de execução e teste.
- **RNF-07 — Significado dos campos e origem dos dados:** a tela e a
  exportação explicam o significado de cada campo e declaram a origem dos
  dados (dados fictícios de simulação).

## 5. Extensões opcionais (fora do MVP)

- Persistência de cenários em banco de dados.
- Autenticação/múltiplos usuários.
- Internacionalização / múltiplas moedas.
- Histórico de simulações entre sessões.

## 6. Casos de teste mínimos (a resolver à mão no Passo 3, depois automatizar em Passo 5)

| # | Categoria | Entrada | Resultado esperado |
|---|---|---|---|
| 1 | Caso normal (referência) | Fixos 3.000; var 10; preço 50; clientes 100; taxa 10% | Receita 5.000; custo variável total 1.000; custo total 4.000; tributos 500; resultado 500; margem 10%; equilíbrio 86 |
| 2 | Caso de limite (contribuição unitária = 0) | Fixos 3.000; var 9; preço 10; clientes 100; taxa 10% | Contribuição unitária = 0; mensagem de não equilíbrio (custo fixo > 0) |
| 3 | Entrada inválida | Taxa = 150%, ou campo de clientes vazio | Sistema rejeita, mensagem de erro, nenhum cálculo realizado |
| 4 | Cenário desfavorável (contribuição negativa) | Fixos 3.000; var 10; preço 8; clientes 100; taxa 10% | Contribuição unitária negativa; mensagem de não equilíbrio; resultado negativo |
| 5 | Alteração de premissa (taxa 0%) | Fixos 3.000; var 10; preço 50; clientes 100; taxa 0% | Tributos 0; resultado 1.000; margem 20%; equilíbrio 75 |
| 6 | Receita zero | Fixos 3.000; var 10; preço 50; clientes 0; taxa 10% | Receita 0; resultado −3.000; margem não calculada (não exibida); sem erro/divisão por zero |
| 7 | Limite do equilíbrio (abaixo) | Fixos 3.000; var 10; preço 50; clientes 85; taxa 10% | Resultado −25,00 |
| 8 | Limite do equilíbrio (exato) | Fixos 3.000; var 10; preço 50; clientes 86; taxa 10% | Resultado +10,00 |
| 9 | Preço que zera com zero clientes | Fixos 3.000; var 10; preço 50; clientes 0; taxa 10% | Preço que zera não calculado; mensagem "não se aplica com zero clientes"; sem erro/divisão por zero |
| 10 | Preço que zera com taxa 100% | Fixos 3.000; var 10; preço 50; clientes 100; taxa 100% | Preço que zera inexistente; mensagem "com tributo de 100% sobre a receita, nenhum preço cobre os custos"; contribuição −10; mensagem de não equilíbrio; resultado −4.000 |

**Todos os 10 casos serão automatizados como testes JUnit 5** no Passo 5
(núcleo de cálculo).

## 7. Rastreabilidade

Este documento cobre: funcionalidades mínimas da Opção 2 (PDF, "2 Simulador
de precificação de um SaaS"), incluindo o cálculo de custos, os "Requisitos
comuns da aplicação" (PDF, páginas 8–9), incluindo exportação CSV/JSON e
comparação de três cenários, a análise de sensibilidade do Passo 7 e as
"Convenções obrigatórias para os cinco projetos" (PDF, página 5), conforme
revisões da equipe em 28 e 29/09/2026.

## 8. Pendências

1. ~~Intervalo/passo de clientes do gráfico (RF-06)~~ — **Decidida em
   29/09/2026:** proposta de `modelo_calculos.md` §9 aprovada pela equipe.
2. ~~Comportamento com `custo_fixo = 0`~~ — **Decidida:** ver
   `modelo_calculos.md` §8.
3. ~~Arredondamento de exibição do preço que zera o resultado~~ —
   **Decidida:** teto ao centavo (R$ 44,45); ver `modelo_calculos.md` §7.
4. Técnica de gráfico sem dependência externa (RNF-05) — proposta virá no
   Passo 6.
5. ~~Verificação do pom.xml~~ — **Resolvida em 28/09/2026:** build
   executado com BUILD SUCCESS; dependências válidas; nenhuma alteração
   necessária.
# Simulador de Precificação de um SaaS

Aplicação de apoio à decisão desenvolvida para a atividade **"Desenvolvimento de Aplicações de Apoio à Decisão com Agentes de IA"** — Opção 2.

| | |
|---|---|
| **Instituição** | Faculdade SENAI FATESG |
| **Curso** | Engenharia de Software |
| **Componente curricular** | Engenharia Econômica |
| **Docente** |  Ujeverson Tavares Sampaio |
| **Integrantes** | Isabela Cristina · Wanessa Castro


## Objetivo

Ajudar um profissional de engenharia de software a decidir **qual preço mensal cobrar por um serviço por assinatura (SaaS) e com quantos clientes ele deixa de dar prejuízo**, a partir do custo fixo mensal, do custo variável por cliente e de uma taxa hipotética de tributos sobre a receita.

A aplicação recebe dados, realiza cálculos verificáveis, compara três cenários de preço e apresenta uma interpretação dos resultados.

## Tecnologias

- **Backend:** Java 21, Spring Boot, Maven (API REST)
- **Frontend:** HTML, CSS e JavaScript puro, servidos pelo próprio Spring Boot
- **Testes:** JUnit 5
- **Desenvolvimento:** IntelliJ IDEA com Claude Code (agente de IA)

## Indicadores calculados

| Indicador | Fórmula | Significado |
|---|---|---|
| Receita | `preço × clientes` | Valor faturado no mês |
| Tributos | `receita × taxa` | Tributo hipotético sobre a receita |
| Resultado | `receita − custo fixo − custo variável × clientes − tributos` | Saldo operacional mensal do modelo |
| Contribuição unitária | `preço × (1 − taxa) − custo variável` | Quanto cada cliente contribui para cobrir o custo fixo |
| Margem (%) | `100 × resultado / receita` | Parcela da receita que sobra como resultado (só com receita > 0) |
| Clientes de equilíbrio | `teto(custo fixo / contribuição unitária)` | Menor quantidade de clientes com resultado ≥ 0 |

Se a contribuição unitária for zero ou negativa, com custo fixo positivo, **não há equilíbrio por aumento de volume** nesse modelo: cada cliente a mais não reduz o prejuízo.

## Convenções e premissas

- **Moeda:** reais (R$). **Unidade de tempo:** mensal.
- A **taxa de tributos é hipotética e editável**, usada apenas para fins didáticos. Não representa alíquota legal.
- A taxa é digitada em percentual na tela e convertida em fração no cálculo.
- A quantidade de clientes é um número inteiro maior ou igual a zero.
- O **resultado é o saldo operacional de um modelo didático**, não o lucro contábil.
- Todos os dados de exemplo são **fictícios** e identificados como simulação.
- Valores monetários são comparados com tolerância de R$ 0,01 e arredondados apenas na apresentação.
- Nos três cenários de comparação, a quantidade de clientes é mantida constante (100). Isso é uma **simplificação**: na prática, alterar o preço tende a alterar a demanda.

## Dados de exemplo

**Caso de referência** (fornecido na atividade):

| Entrada | Valor |
|---|---|
| Custo fixo mensal | R$ 3.000,00 |
| Custo variável por cliente | R$ 10,00 |
| Preço mensal | R$ 50,00 |
| Clientes | 100 |
| Taxa hipotética de tributos | 10% |

Resultado esperado: receita R$ 5.000,00 · tributos R$ 500,00 · resultado R$ 500,00 · margem 10% · equilíbrio a partir de 86 clientes.

**Cenários de comparação** (custo fixo R$ 3.000, variável R$ 10, 100 clientes, taxa 10%):

| Cenário | Preço | Resultado |
|---|---|---|
| Pessimista | R$ 40,00 | −R$ 400,00 |
| Base | R$ 50,00 | R$ 500,00 |
| Otimista | R$ 70,00 | R$ 2.300,00 |

## Como executar

> _A confirmar no Passo 4._

Pré-requisito: JDK 21 instalado.

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

Depois, acesse `http://localhost:8080` no navegador.

## Como rodar os testes

> _A confirmar no Passo 5._

```bash
# Windows
mvnw.cmd test

# Linux / macOS
./mvnw test
```

## Estrutura do repositório

```
simulador-precificacao-saas/
├── docs/               # PDF da atividade, caso resolvido à mão, evidências
├── src/                # Código-fonte (backend, frontend e testes)
├── README.md           # Este arquivo
├── requisitos.md       # Requisitos e critérios de aceitação
├── modelo_calculos.md  # Modelo de cálculos (Passo 3)
├── registro_ia.md      # Registro das interações com a IA
└── pom.xml             # Dependências Maven
```

## Documentação do projeto

- [`requisitos.md`](requisitos.md): requisitos funcionais, não funcionais e casos de teste.
- [`modelo_calculos.md`](modelo_calculos.md): variáveis, unidades, fórmulas e caso resolvido passo a passo _(a criar no Passo 3)_.
- [`registro_ia.md`](registro_ia.md): interações com o agente de IA, com a verificação humana e a decisão da equipe.

## Limitações

- Modelo de um único período (mensal), sem projeção ao longo do tempo.
- Não considera variação da demanda em função do preço.
- A taxa de tributos é ilustrativa e não substitui análise tributária real.
- O resultado não equivale a lucro contábil completo.

## Referências

- Aula 4 – Custo, Preço e Valor: Diferenças e Impactos em Projetos de Software, páginas 3 a 20.
- Atividade "Desenvolvimento de Aplicações de Apoio à Decisão com Agentes de IA" (`docs/`).

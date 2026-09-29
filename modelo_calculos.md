# Modelo de Cálculos — Simulador de Precificação de um SaaS (Opção 2)

Passo 3 do roteiro da atividade. Este documento detalha, antes de qualquer
código, as variáveis, unidades, fórmulas, convenções de sinal, o tratamento
do "mês zero" e os limites de validade do modelo fixado em
[`requisitos.md`](requisitos.md) (seção 2). Resolve também o caso de
referência passo a passo, compara o resultado com o cálculo manual da
equipe (`docs/evidencias/calculo1.jpeg` a `calculo4.jpeg`) e traz a dedução
algébrica das duas grandezas de sensibilidade (RF-11). Fecha as pendências
2 e 3 da seção 8 de `requisitos.md` e registra uma proposta para a
pendência 1, que aguarda revisão da equipe.

## 1. Escopo

Modelo de **período único** (um mês típico, recorrente, stateless — ver
`requisitos.md` §1). Cada simulação é independente; não há acumulação,
projeção ao longo de vários meses, nem decisão de investimento inicial.

## 2. Variáveis e unidades

### 2.1 Fornecidas (entrada do usuário)

| Variável | Símbolo | Unidade | Domínio válido |
|---|---|---|---|
| Custo fixo mensal | `custo_fixo` | R$ / mês | número real ≥ 0 |
| Custo variável por cliente | `custo_variavel` | R$ / cliente / mês | número real ≥ 0 |
| Preço mensal | `preco` | R$ / cliente / mês | número real ≥ 0 |
| Quantidade de clientes | `clientes` | clientes (unidade adimensional) | inteiro ≥ 0 |
| Taxa de tributos (tela) | `taxa_pct` | % | número real, 0 a 100 |
| Benefício percebido | — | texto livre | qualquer texto; não entra em fórmula |

`taxa` (fração usada nas fórmulas) é **convertida**, não fornecida
diretamente: `taxa = taxa_pct / 100`, domínio `[0, 1]`.

### 2.2 Calculadas (fórmula fechada, valor exato dentro da precisão do `BigDecimal`)

| Variável | Símbolo | Unidade | Depende de |
|---|---|---|---|
| Receita | `receita` | R$ / mês | `preco`, `clientes` |
| Tributos | `tributos` | R$ / mês | `receita`, `taxa` |
| Custo variável total | `custo_variavel_total` | R$ / mês | `custo_variavel`, `clientes` (intermediário, não citado à parte em `requisitos.md` mas usado no `resultado`) |
| Resultado | `resultado` | R$ / mês | `receita`, `custo_fixo`, `custo_variavel_total`, `tributos` |
| Contribuição unitária | `contribuicao_unitaria` | R$ / cliente / mês | `preco`, `taxa`, `custo_variavel` |
| Margem percentual | `margem_percentual` | % | `resultado`, `receita` (só se `receita > 0`) |

### 2.3 Estimadas (fronteira de um domínio contínuo transposta para um domínio discreto/monetário por uma regra de arredondamento explícita — o valor "exato" não é, em si, uma resposta válida do domínio)

| Variável | Símbolo | Unidade | Regra de fronteira |
|---|---|---|---|
| Clientes de equilíbrio | `clientes_equilibrio` | clientes (inteiro) | teto do valor real `custo_fixo / contribuicao_unitaria` |
| Preço que zera o resultado (exibido) | `preco_zera_exibido` | R$ / cliente / mês | teto ao centavo do valor real `preco_zera` |

A diferença entre "calculada" e "estimada" aqui não é sobre precisão do
`BigDecimal` (que é exata nas duas), mas sobre o fato de a variável
estimada resolver uma **fronteira de decisão** (menor `clientes` inteiro
que não dá prejuízo; menor `preco` em centavos que não dá prejuízo), cujo
valor real intermediário (`85,714...`; `44,444...`) não é, sozinho, uma
resposta utilizável — precisa de uma convenção de arredondamento definida
por decisão de equipe (seções 7 e 8 deste documento), não apenas pela
fórmula algébrica.

## 3. Fórmulas fixadas (reprodução de `requisitos.md` §2)

```
receita               = preco * clientes
tributos              = receita * taxa
custo_variavel_total  = custo_variavel * clientes
resultado             = receita - custo_fixo - custo_variavel_total - tributos
contribuicao_unitaria = preco * (1 - taxa) - custo_variavel
margem_percentual     = 100 * resultado / receita         (somente se receita > 0)
clientes_equilibrio   = teto(custo_fixo / contribuicao_unitaria)   (somente se contribuicao_unitaria > 0)
```

`teto(x)` = menor inteiro `n` tal que `n ≥ x` (função ceiling).

## 4. Convenções de sinal

- Entradas (`custo_fixo`, `custo_variavel`, `preco`, `clientes`, `taxa`) são
  sempre **magnitudes não negativas**; nenhuma é pré-negada antes de entrar
  na fórmula — os sinais de subtração já estão explícitos em `resultado`.
- `receita`, `tributos` e `custo_variavel_total` são sempre ≥ 0.
- `resultado` pode ser negativo (prejuízo do mês), zero (equilíbrio exato)
  ou positivo (superávit do mês). Exibição de valor negativo usa sinal de
  menos (ex.: `−R$ 25,00`), nunca parênteses contábeis, para consistência
  com a exportação (RF-09).
- `contribuicao_unitaria` pode ser negativa (cada cliente adicional
  aumenta o prejuízo), zero (cada cliente é neutro) ou positiva (cada
  cliente ajuda a cobrir o custo fixo).
- `margem_percentual` segue o sinal de `resultado` (pode ser negativa).

## 5. Tratamento do mês zero

**Não se aplica.** A Opção 2 é um modelo de **período único mensal**: cada
simulação avalia o equilíbrio operacional de um mês recorrente e
autônomo, sem investimento inicial, sem fluxo de caixa multiperíodo, sem
payback e sem VPL/TIR. Não existe, portanto, um "mês zero" (momento do
investimento inicial) a tratar — conceito pertencente a modelos de
recuperação de investimento, explicitamente fora do escopo deste projeto
(`requisitos.md` §1: "o modelo mede apenas o equilíbrio operacional do
mês... não mede recuperação de investimento"). Inventar uma regra para o
mês zero introduziria uma premissa não pedida pela atividade; por isso
este documento apenas declara a não aplicabilidade.

## 6. Limites de validade do modelo

- Válido só para **um mês por simulação**; não projeta crescimento,
  sazonalidade, churn ou variação de custos ao longo do tempo.
- Não modela **elasticidade de demanda**: mudar `preco` não altera
  `clientes` no mesmo cálculo (simplificação assumida também no RF-05,
  ao comparar três preços com os mesmos 100 clientes).
- `taxa` é **hipotética e única**, incidente só sobre a receita; não
  representa a legislação tributária real (ISS, PIS/COFINS, Simples
  etc.), nem outros tributos sobre lucro ou folha.
- `custo_fixo` e `custo_variavel` são constantes por cliente/mês; o
  modelo não representa economia ou deseconomia de escala.
- Domínio de entrada aceito: `custo_fixo`, `custo_variavel`, `preco` ≥ 0;
  `clientes` inteiro ≥ 0; `taxa_pct` em `[0, 100]`. Fora desse domínio a
  aplicação rejeita a entrada (RF-08), e as fórmulas acima não têm
  significado garantido.
- `resultado` é **saldo operacional do mês**, não lucro contábil (não
  inclui depreciação, impostos sobre lucro, variação de capital de giro
  etc.) nem fluxo de caixa livre.

## 7. Decisão — preço que zera o resultado (pendência 3 de `requisitos.md` §8)

O valor algébrico exato (seção 10) costuma ser uma dízima (ex.:
`44,444...`). Preço é um valor monetário com **2 casas decimais**; a
dízima não é um preço cobrável. Decisão da equipe:

> Manter precisão total do `BigDecimal` internamente (para comparações e
> para os testes com tolerância de R$ 0,01, RNF-01); na **exibição**,
> arredondar **para cima** até o centavo:
> `preco_zera_exibido = teto(preco_zera * 100) / 100`.

Motivo: arredondamento comum (mais próximo) para `44,44` ainda produz
resultado negativo naquele preço (`−R$ 0,40`, seção 12.9); o objetivo do
indicador é "o menor preço que **não dá prejuízo**", então a exibição
precisa ser o menor centavo que garante `resultado ≥ 0`, mesma lógica de
teto já usada em `clientes_equilibrio`. Verificado em `docs/evidencias/calculo4.jpeg`.

## 8. Decisão — custo fixo igual a zero (pendência 2 de `requisitos.md` §8)

O PDF da atividade não especifica esse caso; `custo_fixo = 0` é entrada
**válida** (não é rejeitada por RF-08, que só proíbe valores negativos).
Com `custo_fixo = 0`, `resultado = contribuicao_unitaria * clientes`
(seção 11), logo:

| Condição | `clientes_equilibrio` | Mensagem adicional |
|---|---|---|
| `contribuicao_unitaria > 0` | `teto(0 / contribuicao_unitaria) = 0` — já coberto pela fórmula fixada da seção 3 | nenhuma; resultado só melhora com mais clientes |
| `contribuicao_unitaria = 0` | `0` (caso à parte: `0 / 0` é indefinido; por raciocínio direto, `resultado ≡ 0` para qualquer `clientes`) | "resultado é zero para qualquer quantidade de clientes" |
| `contribuicao_unitaria < 0` | `0` (caso à parte, fora da condição "`> 0`" da fórmula fixada) | **"cada cliente adicional gera resultado negativo"** (decisão da equipe) |

Ou seja: `clientes_equilibrio = 0` em todos os três casos quando
`custo_fixo = 0` (não há custo fixo a cobrir, o equilíbrio já começa em
zero clientes); a diferença entre eles é apenas a mensagem qualitativa
que acompanha o número, já que nos dois primeiros casos aumentar
`clientes` mantém ou melhora o resultado, e no terceiro caso piora. Isso
não contradiz nem altera a fórmula fixada de `requisitos.md` §2 — apenas
preenche as duas fronteiras (`contribuicao_unitaria = 0` e `< 0`) que a
condição "somente se `contribuicao_unitaria > 0`" deixa em aberto quando
`custo_fixo = 0`. Quando `custo_fixo > 0` e `contribuicao_unitaria ≤ 0`,
vale a regra já fixada: "não há equilíbrio por aumento de volume".

## 9. Proposta — intervalo do gráfico (RF-06, pendência 1 de `requisitos.md` §8)

**Em aberto para revisão da equipe** (ainda não é decisão fechada).

- **Limite inferior:** sempre `0` clientes, mesmo que o resultado ali seja
  negativo — mostra o efeito pleno do custo fixo.
- **Limite superior:**
  `limite = teto(max(2 * clientes, 2 * clientes_equilibrio, 10))`
  quando existir `clientes_equilibrio`; caso não exista (sem equilíbrio
  por volume), `limite = teto(max(2 * clientes, 10))`. O fator 2 garante
  ver o comportamento bem depois do ponto de equilíbrio e depois da
  quantidade informada; o piso `10` evita gráficos degenerados quando
  `clientes` é pequeno (ex.: `clientes = 0` ou `1`).
- **Passo:** `passo = 1` se `limite ≤ 100` (plota cada cliente inteiro,
  compatível com o domínio inteiro de `clientes`); senão
  `passo = teto(limite / 100)`, limitando a série a ~100 pontos — mantém
  o gráfico leve para a técnica sem dependência externa do RNF-05.
- **Marcadores obrigatórios:** o ponto `clientes` (quantidade informada) e
  o ponto `clientes_equilibrio` (quando existir) são sempre desenhados,
  **mesmo que caiam fora da malha do passo** — inseridos como pontos
  extras destacados (marcador/cor diferente), nunca omitidos.
- **Sem equilíbrio:** se `contribuicao_unitaria ≤ 0` e `custo_fixo > 0`,
  o gráfico é desenhado normalmente (mostrando a reta decrescente ou
  constante), sem marcador de equilíbrio, acompanhado da mensagem textual
  de "não há equilíbrio por aumento de volume".

Aplicando ao caso de referência (seção 12): `clientes = 100`,
`clientes_equilibrio = 86` → `limite = teto(max(200, 172, 10)) = 200`;
como `200 > 100`, `passo = teto(200/100) = 2`; série `0, 2, 4, ..., 200`
(101 pontos). `86` é par, então cai naturalmente na malha (não precisa de
marcador extra); `100` também cai na malha.

## 10. Dedução algébrica — preço que zera o resultado

Partindo da fórmula de `resultado` e isolando `preco`, para `clientes`
fixo e `clientes > 0`:

```
resultado = receita - custo_fixo - custo_variavel_total - tributos
          = preco*clientes - custo_fixo - custo_variavel*clientes - (preco*clientes)*taxa
          = clientes * [ preco*(1 - taxa) - custo_variavel ] - custo_fixo
          = clientes * contribuicao_unitaria(preco) - custo_fixo
```

Impondo `resultado = 0`:

```
clientes * [ preco*(1 - taxa) - custo_variavel ] = custo_fixo
preco*(1 - taxa) - custo_variavel = custo_fixo / clientes
preco*(1 - taxa) = custo_fixo/clientes + custo_variavel
preco_zera = (custo_fixo + custo_variavel*clientes) / (clientes * (1 - taxa))
```

Com `custo_fixo = 3.000`, `custo_variavel = 10`, `clientes = 100`,
`taxa = 0,10`:

```
preco_zera = (3.000 + 10*100) / (100 * 0,90) = 4.000 / 90 = 44,444...
```

Confere com o caso de referência de `requisitos.md` RF-11
("R$ 4.000 / 90 = R$ 44,444...") e com `docs/evidencias/calculo3.jpeg`.
Exibição (seção 7): `teto(44,444... * 100)/100 = teto(4.444,44...)/100 =
4.445/100 = R$ 44,45`.

## 11. Dedução algébrica — clientes de equilíbrio

Da mesma fatoração acima, para `preco` (logo `contribuicao_unitaria`)
fixo:

```
resultado(clientes) = contribuicao_unitaria * clientes - custo_fixo
```

Impondo `resultado = 0` e isolando `clientes` (válido quando
`contribuicao_unitaria > 0`, para preservar o sentido da desigualdade ao
dividir):

```
clientes_real = custo_fixo / contribuicao_unitaria
```

Como `clientes` é inteiro e o objetivo é o **menor** `clientes` com
`resultado ≥ 0` (não apenas `= 0`), e `resultado` é crescente em
`clientes` quando `contribuicao_unitaria > 0`:

```
resultado(clientes) ≥ 0
⟺ contribuicao_unitaria * clientes ≥ custo_fixo
⟺ clientes ≥ custo_fixo / contribuicao_unitaria     (contribuicao_unitaria > 0)
⟺ clientes_equilibrio = teto(custo_fixo / contribuicao_unitaria)
```

Com `custo_fixo = 3.000` e `contribuicao_unitaria = 35`:

```
clientes_equilibrio = teto(3.000 / 35) = teto(85,714...) = 86
```

Confere com `docs/evidencias/calculo3.jpeg` (`85,71` → arredondado para
86, com a conferência `85 × 35 = 2.975 → −R$25`; `86 × 35 = 3.010 →
+R$10`).

## 12. Caso de referência resolvido passo a passo

Entradas (fornecidas): `custo_fixo = R$ 3.000,00`; `custo_variavel =
R$ 10,00`; `preco = R$ 50,00`; `clientes = 100`; `taxa_pct = 10%`.

| # | Passo | Cálculo | Resultado | Tipo |
|---|---|---|---|---|
| 12.1 | Converter taxa | `taxa = 10 / 100` | `0,10` | calculado (conversão de unidade) |
| 12.2 | Receita | `50 * 100` | `R$ 5.000,00` | calculado |
| 12.3 | Tributos | `5.000 * 0,10` | `R$ 500,00` | calculado |
| 12.4 | Custo variável total | `10 * 100` | `R$ 1.000,00` | calculado (intermediário) |
| 12.5 | Resultado | `5.000 − 3.000 − 1.000 − 500` | `R$ 500,00` | calculado |
| 12.6 | Contribuição unitária | `50*(1 − 0,10) − 10 = 45 − 10` | `R$ 35,00` | calculado |
| 12.7 | Margem | `100 * 500 / 5.000` | `10,00%` | calculado (só por `receita > 0`) |
| 12.8 | Clientes de equilíbrio | `teto(3.000 / 35) = teto(85,714...)` | `86 clientes` | estimado (fronteira inteira) |
| 12.9 | Preço que zera o resultado (100 clientes) | `(3.000 + 1.000) / (100 * 0,90) = 4.000/90 = 44,444...` → exibido `teto(4.444,44.../100)` | valor exato `R$ 44,444...`; exibido `R$ 44,45` | calculado (exato) + estimado (exibição) |

Verificações de fronteira (seção 11): `85 clientes → 85*35 − 3.000 =
−R$ 25,00` (prejuízo); `86 clientes → 86*35 − 3.000 = +R$ 10,00`
(primeiro valor não negativo). Verificação de preço (seção 10): a
`R$ 44,44 → 44,44*0,90 − 10 = 29,996`; `29,996*100 − 3.000 = −R$ 0,40`
(ainda prejuízo); a `R$ 44,45 → 44,45*0,90 − 10 = 30,005`;
`30,005*100 − 3.000 = +R$ 0,50` (primeiro preço, ao centavo, sem
prejuízo).

## 13. Comparação com o cálculo manual da equipe

| Indicador | Modelo (este documento) | Cálculo manual (`docs/evidencias/`) | Time (mensagem) | Divergência |
|---|---|---|---|---|
| Receita | R$ 5.000,00 | R$ 5.000 (calculo2.jpeg) | R$ 5.000 | nenhuma |
| Tributos | R$ 500,00 | R$ 500 (calculo2.jpeg) | R$ 500 | nenhuma |
| Resultado | R$ 500,00 | R$ 500 (calculo2.jpeg) | R$ 500 | nenhuma |
| Contribuição unitária | R$ 35,00 | R$ 35 (calculo2.jpeg) | R$ 35 | nenhuma |
| Margem | 10,00% | 10% (calculo2.jpeg) | 10% | nenhuma |
| Clientes de equilíbrio | 86 | 86 (calculo3.jpeg, com 85→−25 e 86→+10) | 86 | nenhuma |
| Preço que zera o resultado (exato) | R$ 44,444... | R$ 44,444... (calculo3.jpeg) | — | nenhuma |
| Preço que zera o resultado (exibido) | R$ 44,45 | R$ 44,45 (calculo4.jpeg, com 44,44→−0,40 e 44,45→+0,50) | R$ 44,45 (decisão da equipe) | nenhuma |

**Nenhuma divergência encontrada.** Todos os oito valores batem entre a
dedução algébrica deste documento, o cálculo manual em papel
(`docs/evidencias/calculo1.jpeg`–`calculo4.jpeg`, já revisado e corrigido
conforme `registro_ia.md` — "Observação – Caso de referência resolvido à
mão") e os valores citados pela equipe. Não foi necessário investigar
causa de divergência porque não houve nenhuma.

## 14. Rastreabilidade

Fecha as pendências 2 e 3 da seção 8 de `requisitos.md` (decididas) e
registra a proposta da pendência 1 (intervalo do gráfico, ainda em
revisão). Base: `requisitos.md` §§1–4, RF-11; `registro_ia.md`
("Observação – Caso de referência resolvido à mão"); PDF da atividade,
Passo 3.
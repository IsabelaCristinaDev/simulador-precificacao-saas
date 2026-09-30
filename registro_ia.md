# Registro de interações com a IA     

## Interação 1 – Análise inicial do problema (Mensagem inicial + Passo 1)
- **Data:** 28/09/2026
- **Integrante responsável:** Isabela e Wanessa 
- **Objetivo:** Analisar a opção 2, definir usuário, decisão, entradas, saídas e MVP, sem código.
- **Prompt utilizado:** Mensagem inicial do PDF, preenchida para a opção 2.
- **Arquivos alterados:** nenhum.
- **Sugestão da IA:** Descrição do problema, caso fictício com Preço A (R$ 50) e Preço B (R$ 70), MVP proposto e hipóteses. Deixou exportação e cenários fora do MVP. Apontou possível erro nos nomes das dependências do pom.xml.
- **Verificação humana:** Recalculamos o Preço B à mão (contribuição 53; equilíbrio 57; resultado R$ 2.300; margem 32,86%), valores corretos. Comparamos o MVP com os "Requisitos comuns" do PDF e vimos que exportação e três cenários são obrigatórios.
- **Decisão:** ALTERADA. Exportação e três cenários incluídos no MVP. Hipóteses e Preço B aceitos. Alteração no pom.xml suspensa até testar o build.
- **Evidência:** Seção "Requisitos comuns da aplicação" do PDF (p. 8-9).

## Interação 2 – Requisitos e critérios de aceitação (Passo 2)
- **Data:** 28/09/2026
- **Integrante responsável:** Isabela e Wanessa
- **Objetivo:** Transformar a análise em requisitos numerados com critérios de aceitação.
- **Prompt utilizado:** Prompt do Passo 2 do PDF, com as correções da Interação 1; depois, mensagem de correção com 8 itens.
- **Arquivos alterados:** requisitos.md (criado e depois corrigido).
- **Sugestão da IA:** Versão inicial com 10 RFs, 6 RNFs e 6 casos de teste; propôs R$ 40 como terceiro preço; colocou a análise de sensibilidade como extensão opcional.
- **Verificação humana:** Recalculamos os casos de teste à mão (todos corretos). Pelo PDF (Passo 7 e critério "Apoio à decisão"), identificamos que a sensibilidade é obrigatória. Notamos a falta do teste de limite exato do equilíbrio e da validação de clientes não inteiros.
- **Decisão:** ALTERADA. Criado o RF-11 (sensibilidade, obrigatório). Incluídos os casos 7 e 8 (85 clientes → −R$ 25,00; 86 → +R$ 10,00), todos os 8 casos marcados para JUnit, validação de clientes não inteiros, menção a premissas e horizonte no RF-10 e destaque das premissas alteradas no RF-05. R$ 40 aprovado, com a simplificação de 100 clientes registrada. Custo fixo zero e arredondamento do preço de equilíbrio ficaram para o Passo 3.
- **Evidência:** commit "docs: requisitos e critérios de aceitação"; PDF p. 8 e p. 10-11.

- **Obs.:** o requisitos.md apareceu vazio no projeto antes do commit. Foi reconstruído com apoio do Claude (chat), a partir da primeira versão gerada pelo Claude Code e da lista de alterações aprovadas. Nessa reconstrução, a equipe incluiu 3 requisitos novos: equilíbrio mensal × recuperação de investimento, significado dos campos e origem dos dados (RNF-07), e não recomendar quando nenhum cenário é viável.

## Observação – README inicial
- **Data:** 28/09/2026
- **Ferramenta:** Claude (chat), fora da IDE
- **Objetivo:** Rascunho inicial do README.md.
- **Verificação humana:** Revisamos integrantes, indicadores, premissas e dados de exemplo. Instruções de execução e testes ficaram marcadas "a confirmar" até os Passos 4 e 5.
- **Decisão:** ACEITA com ajustes (Isabela e Wanessa).

## Interação 3 – Verificação das dependências do pom.xml
- **Data:** 28/09/2026
- **Integrante responsável:** Isabela e Wanessa
- **Objetivo:** Verificar a afirmação da IA (Interação 1) de que os starters `spring-boot-starter-webmvc-test` e `spring-boot-starter-validation-test` não seriam nomes oficiais e fariam o build falhar.
- **Prompt utilizado:** Nenhum novo. Verificação feita pela equipe após a sugestão da Interação 1.
- **Arquivos alterados:** nenhum (pom.xml mantido como gerado pelo IntelliJ/Spring Initializr).
- **Sugestão da IA:** Corrigir os nomes dos starters no pom.xml no Passo 4.
- **Verificação humana:** Executamos "Reload All Maven Projects" e `mvnw.cmd clean test`. Todas as dependências foram resolvidas; resultado BUILD SUCCESS, 1 teste executado, 0 falhas. Os nomes são válidos na versão do Spring Boot usada no projeto (starters modulares).
- **Decisão:** REJEITADA. O pom.xml não será alterado. A IA provavelmente se baseou em convenções de uma versão anterior do Spring Boot.
- **Evidência:** docs/evidencias/build_pom.png (saída do build com BUILD SUCCESS).


## Observação – Caso de referência resolvido à mão
- **Data:** 28/09/2026
- **Integrantes:** Isabela e Wanessa
- **Objetivo:** Resolver o caso de referência (preço R$ 50) independentemente do agente, conforme exigido no Passo 3 do PDF.
- **Como foi feito:** Cálculo manual no papel (receita, tributos, resultado, contribuição unitária, margem, equilíbrio, teste 85/86 clientes e preço que zera o resultado). Fotos em docs/evidencias/calculo1.jpeg a calculo4.jpeg.
- **Revisão:** Na revisão do cálculo manual, identificamos e corrigimos dois erros de escrita: faltava "− 1.000" (custo variável total) na linha do resultado, e o custo fixo estava escrito como 300 em vez de 3.000 na dedução do preço que zera o resultado.
- **Planilha complementar:** docs/evidencias/caso_manual.xlsx, com estrutura gerada com apoio do Claude (chat), cobrindo os três cenários (R$ 40/50/70) e a comparação entre teto e arredondamento comum. Valores conferidos com o cálculo manual.
- **Resultado:** Todos os valores conferem com o teste de referência do PDF: receita R$ 5.000, tributos R$ 500, resultado R$ 500, margem 10%, contribuição R$ 35, equilíbrio 86 clientes.

## Interação 4 – Modelo de cálculos (Passo 3)
- **Data:** 29/09/2026
- **Integrante responsável:** Isabela e Wanessa
- **Objetivo:** Documentar variáveis, unidades, fórmulas, convenções de sinal e limites do modelo; resolver o caso de referência passo a passo e comparar com o cálculo manual da equipe.
- **Prompt utilizado:** Prompt do Passo 3 do PDF, com as decisões da equipe sobre custo fixo zero, arredondamento do preço que zera o resultado e pedido de proposta para o intervalo do gráfico.
- **Arquivos alterados:** modelo_calculos.md (criado); requisitos.md (seção 8 atualizada).
- **Sugestão da IA:** Documento com 14 seções: mês zero declarado como não aplicável, deduções algébricas do preço que zera o resultado e dos clientes de equilíbrio, caso de referência passo a passo e proposta de intervalo do gráfico. Cabeçalho afirmava que o documento fechava as pendências 1, 2 e 3.
- **Verificação humana:** Conferimos os valores contra o cálculo manual (docs/evidencias/calculo1 a calculo4.jpeg): receita R$ 5.000, tributos R$ 500, resultado R$ 500, contribuição R$ 35, margem 10%, equilíbrio 86 (85 → −R$ 25; 86 → +R$ 10), preço que zera R$ 44,45 (44,44 → −R$ 0,40; 44,45 → +R$ 0,50). Todos bateram. Identificamos contradição no cabeçalho: a pendência 1 (intervalo do gráfico) continuava aberta.
- **Decisão:** ALTERADA. Pedimos correção apenas da frase do cabeçalho (fecha as pendências 2 e 3; pendência 1 com proposta aguardando revisão). Demais conteúdos aceitos.
- **Evidência:** commit "docs: modelo de cálculos e decisões das pendências 2 e 3".
- **Pendente:** aprovar a proposta de intervalo/passo do gráfico (seção 9 do modelo_calculos.md).

## Interação 5 – Revisão do modelo de cálculos contra o PDF
- **Data:** 29/09/2026
- **Integrante responsável:** Isabela e Wanessa
- **Objetivo:** Revisar o modelo_calculos.md completo comparando linha a linha com a opção 2 do PDF e decidir o intervalo do gráfico (pendência 1).
- **Prompt utilizado:** Duas mensagens de revisão: a primeira com aprovação da seção 9 e três correções; a segunda acrescentando duas correções encontradas na releitura do PDF. Depois, pedido de alinhamento das fórmulas entre os dois documentos.
- **Arquivos alterados:** modelo_calculos.md; requisitos.md (RF-01, RF-02, RF-09, RF-11, §2, §6 com casos 9 e 10, §8 com pendência 1 fechada).
- **Sugestão da IA:** Proposta de intervalo do gráfico (0 até 2× o maior entre clientes e equilíbrio, até ~100 pontos, marcadores sempre visíveis). Fórmula do preço que zera o resultado sem tratamento para clientes = 0 e taxa = 100%. Valores arredondados classificados como "estimados". Custos calculados apenas como intermediários, sem aparecer como saída.
- **Verificação humana:** Comparamos o modelo linha a linha com a opção 2 do PDF e conferimos todas as deduções e o caso de referência (corretos). Identificamos: (1) divisão por zero no preço que zera o resultado com clientes = 0 ou taxa = 100%; (2) erro de notação na linha do preço exibido, que resultaria em 45; (3) classificação de "estimados" diferente do sentido do PDF; (4) ausência dos "custos" como saída, exigidos em "Funcionalidades mínimas"; (5) falta da base declarada da taxa e da regra de desembolso único, das "Convenções obrigatórias"; (6) fórmulas de custo adicionadas no modelo, mas não no requisitos.md.
- **Decisão:** ALTERADA. Seção 9 aprovada. Incluídas regras para clientes = 0 e taxa = 100% (casos 9 e 10), notação corrigida, classificação refeita (fornecidos = dados do PDF; estimados = taxa 10%, 100 clientes, preços R$ 40 e R$ 70; calculados = demais), custo variável total (R$ 1.000) e custo total (R$ 4.000) como saídas obrigatórias, convenções documentadas e fórmulas alinhadas entre os dois documentos.
- **Evidência:** commit "docs: revisão do modelo de cálculos contra o PDF".
- **Obs.:** após a revisão, os arquivos requisitos.md e modelo_calculos.md voltaram à versão do último commit (git restore) antes de as alterações serem commitadas. As versões finais foram reconstruídas com apoio do Claude (chat), a partir do conteúdo já revisado e da lista de alterações aprovadas, e conferidas pela equipe. Lição registrada: fazer commit imediatamente após aprovar cada etapa.
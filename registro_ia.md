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

Obs.: o requisitos.md apareceu vazio no projeto antes do commit. Foi reconstruído com apoio do Claude (chat), a partir da primeira versão gerada pelo Claude Code e da lista de alterações aprovadas. Nessa reconstrução, a equipe incluiu 3 requisitos novos: equilíbrio mensal × recuperação de investimento, significado dos campos e origem dos dados (RNF-07), e não recomendar quando nenhum cenário é viável.
## Observação – README inicial
- **Data:** 28/09/2026
- **Ferramenta:** Claude (chat), fora da IDE
- **Objetivo:** Rascunho inicial do README.md.
- **Verificação humana:** Revisamos integrantes, indicadores, premissas e dados de exemplo. Instruções de execução e testes ficaram marcadas "a confirmar" até os Passos 4 e 5.
- **Decisão:** ACEITA com ajustes (Isabela e Wanessa).

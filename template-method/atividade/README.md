# Atividade 016 — Padrão Template Method

Sistema de geração de relatórios refatorado com o padrão Template Method:
a classe abstrata `ReportGenerator` define em `generate()` a sequência fixa
de etapas, e cada formato implementa apenas o que varia — a formatação e o
salvamento.

## Estrutura

- `ReportGenerator` — classe abstrata com o Template Method
  `public final void generate()`, que executa, nesta ordem:
  1. `readData()` — etapa comum, `protected final`: exibe a mensagem de
     leitura e retorna `{"Notebook", "Mouse"}`.
  2. `formatData(String[] data)` — etapa abstrata: retorna o conteúdo
     formatado a partir dos dados lidos.
  3. `saveReport(String content)` — etapa abstrata: simula o salvamento
     exibindo o nome do arquivo e o conteúdo.
  4. `afterGeneration()` — hook com comportamento padrão: exibe
     `Report generated successfully`.
- `CsvReport` — cabeçalho `Product` e um produto por linha; salva em `report.csv`.
- `HtmlReport` — lista `<ul><li>...</li></ul>`; salva em `report.html`.
- `TextReport` — um produto por linha; salva em `report.txt` e sobrescreve o
  hook para chamar `super.afterGeneration()` e depois exibir uma
  notificação (desafio adicional).
- `Main` — gera os relatórios por meio de referências `ReportGenerator`.

## Saída esperada

```
=== CsvReport ===
Reading sales data
Saving report.csv
Product
Notebook
Mouse
Report generated successfully

=== HtmlReport ===
Reading sales data
Saving report.html
<ul><li>Notebook</li><li>Mouse</li></ul>
Report generated successfully

=== TextReport ===
Reading sales data
Saving report.txt
Notebook
Mouse
Report generated successfully
Notification: report.txt is now available

```

As quatro etapas aparecem sempre na mesma ordem; só o conteúdo, o nome do
arquivo e, no `TextReport`, a mensagem final mudam.

## Relatório em texto simples (desafio adicional)

Bastou criar `TextReport` estendendo `ReportGenerator`. Ele implementa as
duas etapas abstratas e especializa o hook `afterGeneration()`, mantendo o
comportamento padrão via `super.afterGeneration()` e acrescentando a
notificação. A sequência em `generate()` e as demais classes não foram
alteradas.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Template Method?**
Cada relatório repetia o fluxo completo (ler, formatar, salvar e registrar
a conclusão). Mudar a leitura ou a ordem exigia alterar todas as classes, e
um novo formato poderia esquecer uma etapa ou executá-las em outra ordem.
Agora o fluxo está em um único lugar, `generate()`, e as subclasses só
fornecem o que é específico do formato.

**b) Qual método representa o Template Method e quais etapas são comuns, abstratas ou hooks na sua implementação?**
O Template Method é `ReportGenerator.generate()`. Etapa comum:
`readData()`. Etapas abstratas: `formatData(String[])` e
`saveReport(String)`. Hook: `afterGeneration()`, sobrescrito apenas em
`TextReport`.

**c) Por que o método `generate()` foi declarado como `final`?**
Para que nenhuma subclasse possa sobrescrevê-lo e alterar a ordem das
etapas ou pular alguma delas. A classe base garante a estrutura do
algoritmo; as subclasses só podem personalizar os pontos previstos. Pelo
mesmo motivo `readData()` também é `final`: a leitura é comum e não deve
variar.

**d) Qual é a diferença entre uma etapa abstrata obrigatória e um hook com implementação padrão?**
A etapa abstrata não tem implementação na classe base, então toda
subclasse concreta é obrigada a implementá-la — o compilador não aceita a
classe sem ela. O hook já tem um comportamento padrão que funciona sozinho;
a subclasse pode sobrescrevê-lo para estender ou substituir esse
comportamento, mas não é obrigada (como `CsvReport` e `HtmlReport`, que o
herdam sem mudanças).

**e) Como Template Method e Strategy diferem quanto ao uso de herança ou composição e à possibilidade de variar o comportamento em tempo de execução?**
O Template Method usa herança: a variação é definida pela subclasse
escolhida e fica fixa no objeto — um `CsvReport` sempre formata em CSV.
Ele varia *partes* de um algoritmo cuja estrutura é controlada pela classe
base. O Strategy usa composição e delegação: o contexto guarda uma
referência para um algoritmo inteiro, que pode ser trocado em tempo de
execução (como o `setStrategy` da calculadora de frete), sem criar um novo
objeto contexto.

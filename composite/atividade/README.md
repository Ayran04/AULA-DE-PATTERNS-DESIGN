# Atividade 010 — Padrão Composite

Sistema de arquivos e pastas refatorado para que arquivos e pastas
compartilhem uma abstração comum, permitindo montar uma árvore com
qualquer profundidade e tratar folhas e composições de forma uniforme.

## Estrutura

- `FileSystemComponent` — abstração comum (component), com `getName()`,
  `getSize()` e `display(String indent)`.
- `DocumentFile` — folha (leaf). Representa um arquivo individual; não
  possui filhos e retorna o próprio tamanho.
- `Folder` — composto (composite). Mantém uma lista de
  `FileSystemComponent` (arquivos e/ou outras pastas), com `add` e
  `remove`. `getSize()` soma recursivamente o tamanho de todos os
  elementos; uma pasta vazia retorna `0`.
- `Main` — monta uma árvore com um arquivo isolado, uma pasta vazia e uma
  hierarquia com pelo menos dois níveis de subpastas (`root` →
  `documents` → `invoices`), consultando tudo apenas através de
  `FileSystemComponent`/`Folder`, sem `instanceof`.

## Exibição da hierarquia (desafio adicional)

`display(String indent)` foi adicionado à abstração comum:

- Em `DocumentFile`, imprime o nome e o tamanho do arquivo com a
  indentação recebida.
- Em `Folder`, imprime seu próprio nome e tamanho total e então chama
  `display` em cada filho com indentação incrementada, propagando a
  chamada recursivamente até as folhas.

Chamar `root.display("")` percorre a árvore inteira a partir da raiz sem
que o código cliente precise saber a profundidade da estrutura.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Composite?**
A estrutura original só permitia que uma pasta contivesse arquivos, e não
outras pastas. Uma solução ingênua para suportar subpastas exigiria
listas separadas (uma para arquivos, outra para subpastas) e lógica
diferente para cada tipo ao calcular tamanhos, o que dificulta tratar a
hierarquia de forma uniforme.

**b) Quais elementos representam o componente, a folha e o composto na sua implementação?**
Componente: `FileSystemComponent`. Folha: `DocumentFile`. Composto:
`Folder`, que contém outros `FileSystemComponent` (arquivos ou pastas).

**c) Como o polimorfismo permite tratar arquivos e pastas de maneira uniforme?**
Tanto `DocumentFile` quanto `Folder` implementam `FileSystemComponent`.
Qualquer código que opere sobre essa interface (como `getSize()` ou
`display()` chamados dentro de `Folder`) não precisa saber se está lidando
com um arquivo ou uma pasta — apenas invoca o método da interface, e a
implementação concreta correta é executada automaticamente, sem
condicionais nem `instanceof`.

**d) Qual é o papel da recursão no cálculo do tamanho das pastas?**
`Folder.getSize()` soma o tamanho de cada filho chamando `child.getSize()`.
Quando o filho é outra `Folder`, essa chamada dispara novamente
`getSize()` daquela subpasta, que por sua vez soma os seus próprios
filhos — e assim sucessivamente até alcançar as folhas (`DocumentFile`),
que apenas retornam seu tamanho armazenado. É essa recursão que permite
calcular o total de uma árvore inteira a partir de uma única chamada na
raiz.

**e) Quais são as vantagens e limitações de manter as operações de adicionar e remover filhos apenas na classe Folder?**
Vantagem: a interface `FileSystemComponent` permanece simples e válida
tanto para folhas quanto para composições — um `DocumentFile` nunca
precisa implementar (ou lançar exceção em) métodos de gerenciamento de
filhos que não fazem sentido para ele, respeitando melhor o princípio de
segregação de interface. Limitação: código que trabalha apenas com a
referência `FileSystemComponent` não consegue adicionar/remover filhos
sem antes verificar ou converter o objeto para `Folder`, o que reduz um
pouco a uniformidade de tratamento nesse ponto específico (é a
alternativa ao Composite "puro", em que essas operações ficam na própria
interface e a folha lança exceção ao ser chamada).

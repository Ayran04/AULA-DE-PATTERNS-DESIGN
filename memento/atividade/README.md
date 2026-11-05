# Atividade 023 — Padrão Memento

Editor de texto refatorado com o padrão Memento: o próprio editor gera uma
captura imutável do seu estado (texto e posição do cursor) e sabe
restaurá-la. O histórico apenas empilha essas capturas e as devolve ao
editor, sem conhecer nem alterar o que há dentro delas.

## Estrutura

- `TextEditor` — originador. Mantém o texto (inicia vazio) e o cursor
  (inicia em zero), os métodos de consulta, as validações e o
  comportamento de `setText()`, que posiciona o cursor no fim do novo
  texto. Ganhou:
  - `EditorMemento save()`: cria uma nova captura do estado atual, sem
    modificar o editor;
  - `void restore(EditorMemento)`: recupera exatamente o texto e o cursor
    da captura (rejeita `null`).
- `TextEditor.EditorMemento` — memento. Classe aninhada
  `public static final`, com construtor e campos `private final` e sem
  getters. Fora de `TextEditor` ela só pode ser guardada e repassada.
- `EditorHistory` — cuidador. Recebe um `TextEditor` não nulo e mantém
  duas pilhas (`Deque`) de `EditorMemento`:
  - `backup()`: empilha `save()` na pilha de desfazer e limpa a pilha de
    refazer;
  - `undo()`: se houver captura, salva o estado atual na pilha de refazer
    e restaura a mais recente da pilha de desfazer;
  - `redo()` (desafio adicional): se houver captura, salva o estado atual
    na pilha de desfazer e restaura a mais recente da pilha de refazer.

  Com a pilha correspondente vazia, `undo()` e `redo()` só exibem um aviso,
  sem alterar o editor nem a outra pilha. As movimentações internas usam
  `save()` diretamente, sem chamar `backup()`.
- `Main` — cliente. Chama `backup()` imediatamente antes de cada edição que
  quer poder desfazer e exibe texto e cursor após cada operação.

## Saída esperada

```
=== Editing ===
Text: Hello | Cursor: 2
Text: Hello, world! | Cursor: 5
Text: Goodbye | Cursor: 7

=== Undo ===
Text: Hello, world! | Cursor: 5
Text: Hello | Cursor: 2
Nothing to undo: history is empty
Text: Hello | Cursor: 2

=== Undo and redo (additional challenge) ===
Text: Draft | Cursor: 1
Text: Draft v2 | Cursor: 5
Text: Draft | Cursor: 1
Text: Draft v2 | Cursor: 5

=== New edit after undo discards redo ===
Text: Draft | Cursor: 1
Text: Alternative | Cursor: 11
Nothing to redo: redo history is empty
Text: Alternative | Cursor: 11
Text: Draft | Cursor: 1
```

Cenários pedidos e onde aparecem:

- `Hello` com cursor em 2, backup, `Hello, world!` com cursor em 5,
  backup e `Goodbye`: primeiro bloco.
- Dois `undo()`, restaurando `Hello, world!` (cursor 5) e depois `Hello`
  (cursor 2), e um terceiro que só exibe o aviso e mantém o estado:
  segundo bloco.
- Desafio adicional: desfazer e refazer uma edição, conferindo texto e
  cursor: terceiro bloco.
- Desafio adicional: depois de desfazer, um `backup()` seguido de uma
  edição diferente (`Alternative`) limpa a pilha de refazer, então
  `redo()` não recupera mais `Draft v2`; o `undo()` final mostra que a
  pilha de desfazer continua íntegra: quarto bloco.

## Refazer (desafio adicional)

A extensão ficou toda em `EditorHistory`: uma segunda pilha, a operação
`redo()` e a limpeza dessa pilha em `backup()`. `TextEditor` e
`EditorMemento` não mudaram, porque a política de navegação entre versões
é responsabilidade do cuidador, não do originador.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Memento?**
O cliente lia cada parte do estado do editor, guardava em variáveis soltas
e reconstruía o estado chamando os setters na ordem certa (o texto antes
do cursor, já que `setText()` move o cursor). Se o estado ganhasse um novo
campo, todo ponto de salvar e restaurar teria de mudar, e esquecer o
cursor gerava uma restauração incompleta. Agora o editor produz uma
captura única e consistente com `save()` e a aplica inteira com
`restore()`; quem guarda o histórico não precisa saber do que o estado é
feito.

**b) Quais classes representam o originador, o memento e o cuidador do histórico na sua implementação?**
Originador: `TextEditor`. Memento: `TextEditor.EditorMemento`. Cuidador:
`EditorHistory`.

**c) Por que o histórico não deve consultar ou modificar os dados armazenados em uma captura? Como essa restrição foi representada em Java?**
Se o histórico lesse os dados, voltaria a depender da estrutura interna do
editor, que é justamente o acoplamento que o padrão remove; se pudesse
modificá-los, uma captura deixaria de representar fielmente uma versão
anterior e o `restore()` poderia aplicar um estado que nunca existiu ou
que viola as validações do editor. Em Java, `EditorMemento` é uma classe
aninhada `public static final` de `TextEditor`, com construtor e campos
`private final` e sem getters. Como os membros privados de uma classe
aninhada são acessíveis à classe externa, só `TextEditor` cria a captura e
lê seus campos; para `EditorHistory` ela é um objeto opaco, que só pode
ser empilhado e devolvido.

**d) Se o editor passasse a armazenar objetos mutáveis, por que apenas copiar suas referências poderia comprometer o histórico? Qual seria o impacto de manter muitas capturas completas em memória?**
Com uma cópia rasa, a captura e o editor apontariam para o mesmo objeto;
uma alteração posterior no editor modificaria também a captura, e o
`restore()` traria de volta o estado atual em vez do antigo. Seria
preciso fazer cópia defensiva (profunda) em `save()` e também em
`restore()`, para que o editor restaurado não passe a compartilhar o
objeto com a captura que continua no histórico. Hoje isso não é necessário
porque `String` e `int` são imutáveis. Quanto à memória, cada captura
completa guarda o documento inteiro: com textos grandes e muitas edições,
o consumo cresce proporcionalmente ao tamanho do documento vezes o número
de versões. As saídas seriam limitar o tamanho do histórico, descartar as
capturas mais antigas ou guardar apenas as diferenças entre versões
(capturas incrementais), ao custo de uma restauração mais complexa.

**e) Como Memento e Command diferem na implementação de desfazer? Como um comando poderia utilizar uma captura de estado para desfazer sua execução?**
No Command, cada comando sabe executar e reverter a própria operação
(`execute()`/`undo()`), em geral aplicando a operação inversa, e o
histórico é uma pilha de comandos. No Memento, não se reverte uma
operação: guarda-se uma foto do estado antes da mudança e volta-se a ela,
o que funciona mesmo quando a operação não tem inversa simples, mas custa
mais memória. Os dois se combinam: um comando pode chamar `editor.save()`
no início de `execute()`, guardar o `EditorMemento` resultante e, em seu
`undo()`, chamar `editor.restore(memento)`. Assim o comando não precisa
implementar a lógica inversa, e o histórico continua sendo uma pilha de
comandos.

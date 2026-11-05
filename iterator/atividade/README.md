# Atividade 022 — Padrão Iterator

Sistema de playlist musical refatorado com o padrão Iterator: a playlist
deixa de entregar sua lista interna e passa a fornecer iteradores, objetos
que controlam a posição do percurso e devolvem as músicas uma por vez. O
cliente percorre a coleção apenas com `hasNext()` e `next()`, sem conhecer
a estrutura de armazenamento nem manipular índices.

## Estrutura

- `SongIterator` — iterador (interface) com `boolean hasNext()` e
  `Song next()`. Não há operação de remoção no contrato.
- `SongCollection` — agregado (interface) com
  `SongIterator createIterator()`.
- `Playlist` — agregado concreto. Implementa `SongCollection`, mantém
  `addSong(Song)` (rejeita `null` com `NullPointerException`) e oferece
  `createReverseIterator()` (desafio adicional). `getSongs()` foi removido;
  a lista é `private final` e só os iteradores internos têm acesso a ela.
- `PlaylistIterator` — iterador concreto (classe interna privada de
  `Playlist`). Guarda o índice da próxima música, começando em `0`, ou
  seja, antes da primeira música. Percorre na ordem de inclusão.
- `ReversePlaylistIterator` — iterador concreto do desafio adicional
  (classe interna privada de `Playlist`). Começa no índice da última música
  e anda para trás, sem inverter nem copiar a lista.
- `Song` — elemento da coleção; o campo passou a ser `final`.
- `Main` — cliente. Obtém iteradores pela abstração `SongCollection` (e
  `createReverseIterator()` pela `Playlist`) e exibe tudo pelo mesmo método
  `printSongs(SongIterator)`.

Nos dois iteradores, `hasNext()` só consulta a posição, `next()` devolve a
música e avança exatamente uma posição, e `next()` sem próximo elemento
lança `java.util.NoSuchElementException`. Cada chamada a `createIterator()`
ou `createReverseIterator()` cria uma instância nova, com posição própria.

## Saída esperada

```
=== Full traversal ===
First song
Second song
Third song

=== Independent iterators ===
First iterator, 1st next(): First song
First iterator, 2nd next(): Second song
Second iterator, 1st next(): First song

=== Empty playlist ===
hasNext(): false
(no songs)

=== next() after the end ===
First song
Second song
Third song
hasNext(): false
Error: No more songs in the playlist

=== Reverse traversal ===
Third song
Second song
First song

=== New normal traversal after reverse ===
First song
Second song
Third song

=== Reverse traversal: next() after the end ===
Third song
Second song
First song
Error: No more songs in the playlist

=== Reverse traversal: empty playlist ===
hasNext(): false
(no songs)
```

Cenários pedidos e onde aparecem:

- Percurso completo na ordem de inclusão: primeiro bloco.
- Dois novos iteradores, o primeiro avançado duas vezes e o segundo uma
  vez, que ainda devolve a primeira música: segundo bloco.
- Playlist vazia com `hasNext()` igual a `false` desde o início: terceiro
  bloco.
- `next()` após o fim, com a exceção tratada e a mensagem no console:
  quarto bloco.
- Desafio adicional: percurso inverso, novo percurso normal ainda na ordem
  original, exceção após o fim no inverso e inverso em playlist vazia:
  últimos quatro blocos.

## Percurso inverso (desafio adicional)

A extensão exigiu criar `ReversePlaylistIterator` e o método
`createReverseIterator()`, que devolve a mesma abstração `SongIterator`.
O cliente não mudou: `printSongs` recebe qualquer `SongIterator` e
funciona para os dois percursos. A lista armazenada não é invertida nem
copiada; só a regra de posição do iterador é diferente.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Iterator?**
O cliente recebia a `List<Song>` interna e controlava o índice do laço,
dependendo de acesso por posição e do tamanho da lista. Além disso,
`getSongs()` permitia alterar a coleção por fora, sem passar por
`addSong`. Agora a playlist não expõe a lista: o cliente recebe um
`SongIterator` e só pergunta se há próxima música e pede a próxima. A
estrutura de armazenamento e a forma de percorrer podem mudar sem afetar o
cliente.

**b) Quais abstrações e classes representam o agregado, o agregado concreto, o iterador e o iterador concreto na sua implementação?**
Agregado: `SongCollection`. Agregado concreto: `Playlist`. Iterador:
`SongIterator`. Iteradores concretos: `PlaylistIterator` e
`ReversePlaylistIterator`.

**c) Por que a posição deve pertencer a cada iterador, em vez de ser armazenada na própria playlist?**
Se a posição ficasse na playlist, só existiria um percurso por vez: dois
trechos de código percorrendo a mesma playlist avançariam o mesmo cursor e
um "roubaria" músicas do outro, e um percurso interrompido deixaria a
playlist em um estado intermediário. Com a posição no iterador, cada
percurso é independente, como mostra o bloco em que o segundo iterador
ainda devolve a primeira música, e a playlist fica responsável apenas por
guardar as músicas.

**d) Por que hasNext() não deve avançar o percurso? Qual é a importância de definir o comportamento de next() após o último elemento?**
`hasNext()` é uma consulta: o cliente pode chamá-la quantas vezes quiser
(por exemplo, para decidir se exibe "(no songs)" antes do laço) sem pular
elementos. Se ela avançasse, cada verificação consumiria uma música. Já o
comportamento de `next()` após o fim precisa ser definido para que o
erro seja explícito e igual em todos os iteradores: lançar
`NoSuchElementException` evita devolver `null` silenciosamente ou estourar
um `IndexOutOfBoundsException` que revelaria a lista interna, e permite ao
cliente tratar o caso de forma previsível.

**e) Que decisões seriam necessárias para permitir alterações na playlist durante um percurso? Como percorrer uma cópia dos elementos difere de detectar modificações e interromper a iteração?**
Seria preciso decidir se um percurso em andamento deve ou não ver as
músicas incluídas ou removidas depois que começou, o que acontece com a
posição quando um elemento antes dela é removido, e se o próprio iterador
pode remover elementos. Percorrer uma cópia (snapshot) faz o iterador
ignorar as alterações: o percurso nunca falha, mas pode mostrar dados
desatualizados e custa memória para copiar a coleção. Detectar modificações
(fail-fast, como um contador de modificações comparado a cada `next()`)
não copia nada e mantém o percurso fiel à coleção atual, mas interrompe a
iteração com uma exceção, deixando ao cliente a decisão de recomeçar.

# Atividade 013 — Padrão Flyweight

Sistema de renderização de florestas refatorado para que árvores do mesmo
tipo compartilhem nome, cor e textura em uma única instância de
`TreeType`, enquanto cada `Tree` guarda apenas o que é seu: posição e
escala.

## Estrutura

- `TreeType` — Flyweight. Guarda o estado intrínseco (nome, cor e
  textura) em campos `private final`, sem setters e sem expor o array.
  A textura recebida é copiada uma única vez, na criação do tipo.
  `draw(int x, int y, double scale)` recebe o estado extrínseco no momento
  da renderização.
- `Tree` — contexto. Armazena apenas `x`, `y`, `scale` e uma referência
  para `TreeType`; `draw()` delega ao tipo passando sua posição e escala.
- `TreeTypeFactory` — fábrica de flyweights com cache. `getTreeType(name,
  color, textureId)` devolve o tipo já existente ou cria um novo,
  "carregando" a textura (array de 100.000 bytes) somente nesse caso.
  A chave do cache é a classe interna `Key`, que compara os três campos
  por valor em `equals`/`hashCode` — evitando colisões que uma
  concatenação como `name + color + textureId` poderia causar
  (ex.: `"Oak" + "Green"` e `"OakG" + "reen"`).
- `Main` — cria 200 árvores (100 carvalhos e 100 pinheiros) com posições e
  escalas variadas usando a mesma fábrica, desenha todas, exibe a
  quantidade de árvores e de tipos e compara referências com `==`.

## Resultado esperado da demonstração

- `Loading texture: ...` aparece apenas duas vezes (uma por tipo).
- `Trees created: 200` e `Tree types created: 2`.
- `oak1 == oak2: true` — solicitações equivalentes reutilizam o mesmo tipo.
- `oak1 == pine: false` — combinações diferentes produzem tipos distintos.
- Após as solicitações extras, a fábrica continua com 2 tipos.

Memória de textura: no código inicial, 200 cópias de 100.000 bytes
(≈ 20 MB); agora, apenas 2 arrays (≈ 200 KB), independentemente da
quantidade de árvores.

## Escala das árvores (desafio adicional)

A escala é estado extrínseco: varia de árvore para árvore, então foi
adicionada a `Tree` e passada como parâmetro em `TreeType.draw(x, y,
scale)`. Ela **não** faz parte da chave do cache — carvalhos pequenos
(0.5) e grandes (2.0) usam a mesma instância de `TreeType`, e o número de
tipos continua sendo 2.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Flyweight?**
Cada árvore guardava sua própria cópia de nome, cor e, principalmente, da
textura de 100.000 bytes, mesmo sendo idênticas entre árvores do mesmo
tipo. O consumo de memória crescia linearmente com o número de árvores.
Agora os dados visuais existem uma vez por tipo e cada árvore guarda
apenas alguns campos pequenos.

**b) Quais dados representam o estado intrínseco e o estado extrínseco na sua implementação?**
Intrínseco (compartilhado, em `TreeType`): nome, cor e textura.
Extrínseco (por árvore, em `Tree`): coordenadas `x` e `y` e a escala.

**c) Qual é o papel da fábrica e da chave do cache no compartilhamento dos objetos?**
A fábrica é o único ponto de criação de `TreeType`: ela consulta o cache e
só cria (e carrega a textura) quando a combinação ainda não existe,
garantindo que pedidos equivalentes recebam a mesma instância. A chave
define o que é "equivalente": nome, cor e identificador da textura,
comparados por valor. Uma chave mal definida causaria duplicação (tipos
iguais tratados como diferentes) ou, pior, compartilhamento indevido
(tipos diferentes colidindo na mesma chave).

**d) Por que o estado compartilhado deve ser imutável? O que poderia acontecer se a textura de um tipo fosse alterada?**
Porque a mesma instância é usada por centenas ou milhares de árvores.
Se a textura de um `TreeType` fosse alterada, *todas* as árvores daquele
tipo mudariam de aparência ao mesmo tempo, um efeito colateral difícil de
rastrear. Além disso, o cache passaria a devolver um tipo que não
corresponde mais ao `textureId` da chave. Por isso o array é copiado na
criação e nunca é exposto.

**e) Em quais situações o Flyweight pode reduzir significativamente o consumo de memória e quais custos de complexidade ele acrescenta?**
Quando há um número muito grande de objetos, poucos "tipos" distintos e o
estado compartilhável é pesado em relação ao estado individual — árvores e
partículas em jogos, caracteres e estilos em editores de texto, ícones e
marcadores em mapas. Os custos: o estado do objeto fica dividido em duas
classes, o cliente precisa guardar e repassar o estado extrínseco a cada
operação, é necessária uma fábrica com cache (e uma chave bem definida),
o estado compartilhado precisa ser imutável e, em ambientes concorrentes,
o cache exige sincronização. Com poucos objetos, essa complexidade não
compensa.

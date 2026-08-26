# Atividade — Padrão Prototype

Sistema de personagens de jogo, refatorado para criar novos personagens a
partir de protótipos (`Warrior`, `Mage`, `Archer`) em vez de informar todos
os atributos manualmente a cada `new Character(...)`.

## Estrutura

- `CharacterPrototype` — contrato de clonagem (`Character clone()`).
- `Character` — personagem, com atributos básicos e uma lista de `Skill`
  (habilidades). Implementa `clone()` com **cópia profunda**.
- `Skill` — habilidade do personagem, com método `copy()`.
- `Main` — cria os três protótipos base e gera novos personagens a partir
  deles, alterando apenas nome, arma ou habilidades de cada clone.

## Cópia rasa x cópia profunda nesta implementação

Se `clone()` apenas copiasse a referência da lista de habilidades:

```java
Character clone = new Character(name, type, health, attack, defense, weapon);
clone.skills = this.skills; // cópia rasa: mesma lista, mesmos objetos Skill
```

o clone e o protótipo passariam a compartilhar a mesma `List<Skill>` e os
mesmos objetos `Skill`. Adicionar ou alterar uma habilidade no clone (como
`sylas.getSkills().add(...)` em `Main`) também alteraria o protótipo original.

Por isso `Character.clone()` faz **cópia profunda**: cria uma nova
`ArrayList` e, para cada `Skill`, chama `skill.copy()`, gerando um novo
objeto `Skill` independente. Assim o clone pode ganhar, perder ou alterar
habilidades sem nunca afetar o protótipo.

## Questões para reflexão

**a) Qual problema existente na criação dos personagens foi resolvido com o Prototype?**
A repetição de atributos ao criar personagens semelhantes. Antes, todo
personagem novo exigia digitar de novo todos os valores (vida, ataque,
defesa etc.) no construtor, o que era repetitivo e propenso a erro — se um
atributo padrão de um tipo mudasse, seria preciso corrigir em todos os
lugares onde ele era criado. Com o Prototype, os valores padrão ficam
concentrados no protótipo, e cada novo personagem só precisa informar o que
o diferencia dos demais (nome, arma, habilidades extras).

**b) Qual é a principal vantagem de criar objetos a partir de protótipos?**
Criar uma cópia de um objeto já configurado é mais simples, rápido e seguro
do que reconstruir o objeto do zero via construtor, principalmente quando
ele tem muitos atributos ou uma configuração complexa (como a lista de
habilidades). Também desacopla quem cria o objeto da classe concreta e de
como ela é construída — quem clona não precisa conhecer a lista de
parâmetros do construtor, só chamar `clone()`.

**c) Onde está o Prototype na sua implementação?**
A interface `CharacterPrototype` é o contrato do padrão (papel de
`Prototype`), e `Character` é o `ConcretePrototype`, implementando `clone()`
com cópia profunda. Os objetos `warriorPrototype`, `magePrototype` e
`archerPrototype`, criados em `Main`, são os protótipos concretos usados
para gerar `arthos`, `brakus`, `elowen` e `sylas`.

**d) Qual a diferença entre cópia rasa e cópia profunda?**
Na cópia rasa (*shallow copy*), apenas os campos do objeto são copiados;
quando um campo é uma referência a outro objeto (como uma `List`), o clone
passa a apontar para o **mesmo** objeto do original — alterações nesse
objeto compartilhado afetam ambos. Na cópia profunda (*deep copy*), os
objetos referenciados também são clonados recursivamente, então clone e
original ficam totalmente independentes. Neste projeto, `skill.copy()` e a
recriação da `List<Skill>` dentro de `Character.clone()` garantem a cópia
profunda das habilidades.

**e) Em quais situações o padrão Prototype é mais indicado do que criar objetos diretamente com `new`?**
Quando a criação de um objeto é custosa ou complexa (muitos parâmetros,
configuração inicial elaborada, dependência de outros recursos), quando
existem "modelos" ou variações padronizadas de um objeto que se repetem no
sistema (como tipos de personagem, templates de documento, configurações
pré-definidas), ou quando o código que cria o objeto não deve depender da
sua classe concreta nem conhecer os detalhes do seu construtor.

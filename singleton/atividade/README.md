# Atividade — Padrão Singleton

Sistema de configuração da aplicação, refatorado para que `AppConfig`
tenha uma única instância compartilhada por todo o sistema, acessada por
`UserService` e `ReportService` através de um ponto global de acesso.

## Estrutura

- `AppConfig` — Singleton. Construtor privado, instância estática mantida
  em `instance` e acessada via `getInstance()`.
- `UserService` / `ReportService` — não criam mais `new AppConfig()`;
  ambos obtêm a mesma instância através de `AppConfig.getInstance()`.
- `Main` — demonstra que `UserService`, `ReportService` e o próprio `Main`
  enxergam a mesma instância (`==`), e simula 10 threads chamando
  `getInstance()` concorrentemente para provar que todas recebem a mesma
  referência.

## Thread-safety

`getInstance()` usa **double-checked locking** com o campo `instance`
marcado como `volatile`:

```java
if (instance == null) {
    synchronized (AppConfig.class) {
        if (instance == null) {
            instance = new AppConfig();
        }
    }
}
```

- O `synchronized` só é usado na primeira criação, evitando o custo de
  lock em toda chamada.
- O segundo `if (instance == null)` dentro do bloco sincronizado evita que
  duas threads, tendo passado juntas pela primeira checagem, criem duas
  instâncias.
- `volatile` é necessário porque, sem ele, o compilador/JIT pode reordenar
  as instruções de `new AppConfig()` (alocar memória, atribuir à variável,
  rodar o construtor) de forma que outra thread veja `instance` != null
  antes do construtor terminar, obtendo um objeto parcialmente
  inicializado.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Singleton?**
Cada classe (`UserService`, `ReportService`) criava sua própria instância
de `AppConfig` com `new`, o que espalhava pelo código a responsabilidade de
criar as configurações e podia gerar estados divergentes — por exemplo, se
uma instância tivesse seu `environment` alterado, as outras não refletiriam
essa mudança. O Singleton garante uma única instância compartilhada, com
estado consistente para todo o sistema.

**b) Por que o construtor da classe Singleton deve ser privado?**
Para impedir que outras classes criem novas instâncias com `new AppConfig()`
fora da própria classe. Isso é o que garante que a única forma de obter um
`AppConfig` seja pelo método estático `getInstance()`, preservando a
unicidade da instância.

**c) Onde está o ponto global de acesso na sua implementação?**
No método estático `AppConfig.getInstance()`. É por ele que qualquer classe
do sistema (`UserService`, `ReportService`, `Main`) obtém a referência
compartilhada, sem precisar saber como ou quando a instância foi criada.

**d) Quais cuidados devem ser considerados ao utilizar Singleton em sistemas com múltiplas threads?**
Sem sincronização, duas threads podem passar pela checagem
`if (instance == null)` ao mesmo tempo e cada uma criar sua própria
instância, quebrando a garantia de unicidade (condição de corrida na
criação). Também é preciso cuidado com reordenação de instruções pelo
compilador/JIT, que pode expor uma instância parcialmente construída para
outra thread — por isso o campo é `volatile`. Além disso, se a instância
guardar estado mutável (como `environment`), acessos concorrentes de
leitura/escrita a esse estado também precisam de sincronização própria, o
que o Singleton por si só não resolve.

**e) Quais são as desvantagens ou riscos de utilizar Singleton em excesso?**
Introduz estado global no sistema, o que dificulta testes (é difícil isolar
ou trocar a instância em testes unitários) e cria acoplamento oculto entre
classes que dependem do mesmo Singleton sem que isso apareça explicitamente
em suas assinaturas. Também pode virar um gargalo de concorrência se muitas
threads disputarem acesso sincronizado ao mesmo recurso, e tende a acumular
responsabilidades demais ao longo do tempo, funcionando como um "objeto
global" disfarçado, o que fere princípios como responsabilidade única e
inversão de dependência.

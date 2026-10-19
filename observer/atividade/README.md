# Atividade 018 — Padrão Observer

Sistema de monitoramento meteorológico refatorado com o padrão Observer: a
estação mantém uma lista de interessados e, a cada nova temperatura,
notifica todos por meio da abstração `TemperatureObserver`, sem conhecer
seus tipos concretos nem suas regras de exibição.

## Estrutura

- `TemperatureObserver` — abstração do observador com `update(double)`.
- `TemperatureSubject` — abstração do sujeito com `addObserver`,
  `removeObserver` e `notifyObservers`.
- `WeatherStation` — sujeito concreto. Guarda a temperatura atual e uma
  lista de `TemperatureObserver`; não recebe painéis pelo construtor.
  - `addObserver` rejeita `null` (`NullPointerException`) e ignora uma
    instância já cadastrada; não gera notificação imediata.
  - `removeObserver` de uma instância não cadastrada não tem efeito.
  - `setTemperature` atualiza o estado e chama `notifyObservers()`, que
    chama `update(temperature)` uma vez em cada observador, mesmo que a
    temperatura seja igual à anterior.
- `CurrentConditionsDisplay` — exibe toda temperatura recebida.
- `TemperatureAlert` — exibe alerta apenas quando a temperatura é maior ou
  igual a 35 °C; a regra fica no próprio alerta.
- `AverageTemperatureDisplay` (desafio adicional) — acumula soma e
  quantidade das atualizações recebidas e exibe a média.
- `Main` — cliente que monta os cenários pedidos.

## Saída esperada

```
=== Station 1: display and alert ===
Setting temperature to 28.0 C
Current temperature: 28.0 C
Setting temperature to 36.0 C
Current temperature: 36.0 C
High temperature alert: 36.0 C
-- Removing alert --
Setting temperature to 38.0 C
Current temperature: 38.0 C
-- Removing alert again and display --
Setting temperature to 40.0 C

=== Station 2: average display ===
Setting temperature to 20.0 C
Average temperature: 20.0 C
Setting temperature to 30.0 C
Average temperature: 25.0 C
Setting temperature to 40.0 C
Average temperature: 30.0 C
```

Verificações:

- O painel foi cadastrado duas vezes, mas exibe cada temperatura uma única
  vez.
- O alerta aparece apenas para 36 °C (28 °C está abaixo do limite e em
  38 °C ele já foi removido).
- Remover o alerta de novo não tem efeito; após remover todos os
  observadores, `setTemperature(40.0)` funciona sem exibir nada.
- Desafio adicional: em uma nova estação, o painel de média exibe 20, 25 e
  30 °C, considerando só as atualizações recebidas após a inscrição.

## Painel de média (desafio adicional)

`AverageTemperatureDisplay` foi criado apenas implementando
`TemperatureObserver`. `WeatherStation`, as abstrações e os observadores
existentes não foram alterados: basta cadastrar o novo painel com
`addObserver`.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Observer?**
A `WeatherStation` dependia de cada destinatário concreto
(`CurrentConditionsDisplay` e `TemperatureAlert`), os recebia pelo
construtor e chamava métodos específicos de cada um. Incluir um novo painel
exigia modificar a estação, e não havia como cadastrar ou remover
interessados em tempo de execução. Agora a estação conhece apenas a
abstração `TemperatureObserver` e uma lista dinâmica de inscritos.

**b) Quais abstrações e classes representam o sujeito, o sujeito concreto, o observador e os observadores concretos na sua implementação?**
Sujeito: `TemperatureSubject`. Sujeito concreto: `WeatherStation`.
Observador: `TemperatureObserver`. Observadores concretos:
`CurrentConditionsDisplay`, `TemperatureAlert` e
`AverageTemperatureDisplay`.

**c) Como o cadastro e a remoção de observadores alteram os destinatários das notificações durante a execução?**
`notifyObservers()` percorre a lista no momento da notificação, então os
destinatários são exatamente os inscritos naquele instante. Um observador
cadastrado passa a receber as próximas atualizações (não as anteriores, nem
uma notificação imediata); um removido deixa de recebê-las. No `Main`, o
alerta recebe 28 e 36 °C, mas não 38 °C, porque foi removido antes.

**d) A solução envia a temperatura no método update(), utilizando a abordagem push. Como seria uma alternativa pull, em que o observador consulta o estado do sujeito?**
O `update` não receberia o dado, apenas uma referência ao sujeito (por
exemplo `update(WeatherStation station)`, ou o observador guardaria a
estação), e a estação ofereceria um getter como `getTemperature()`. Cada
observador consultaria só o que precisa no momento da notificação. Isso
permite acrescentar novos dados (umidade, pressão) sem mudar a assinatura
de `update`, mas acopla o observador à interface de consulta do sujeito.

**e) Como um observador lento ou que lança uma exceção pode afetar os demais em uma notificação síncrona? Que cuidados seriam necessários em um sistema real?**
Como a notificação percorre a lista em sequência na mesma thread, um
observador lento atrasa todos os seguintes e o próprio `setTemperature`; e
uma exceção não tratada interrompe o laço, de modo que os observadores
seguintes não recebem a atualização e o erro chega a quem chamou a estação.
Em um sistema real seria preciso isolar cada chamada com `try/catch` e
registrar falhas, usar notificação assíncrona (filas, executores) com
timeouts para observadores lentos, iterar sobre uma cópia ou coleção
thread-safe (como `CopyOnWriteArrayList`) para permitir alterações de
inscrição durante a notificação, e cuidar de remover observadores que não
são mais usados para evitar vazamentos de memória.

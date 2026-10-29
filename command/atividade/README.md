# Atividade 020 — Padrão Command

Sistema de controle de iluminação refatorado com o padrão Command: cada
solicitação é um objeto que conhece a lâmpada e a operação a executar. O
controle remoto apenas associa nomes de botões a objetos `Command` e chama
`execute()`, sem conhecer `Light` nem decidir qual método chamar.

## Estrutura

- `Command` — abstração com `execute()` e `undo()` (desafio adicional).
- `TurnOnLightCommand` / `TurnOffLightCommand` — comandos concretos.
  Recebem uma `Light` não nula pelo construtor (`NullPointerException`
  caso contrário) e delegam a `turnOn()` / `turnOff()`. Antes de executar,
  guardam o estado anterior da lâmpada; `undo()` restaura esse estado.
- `Light` — receptor. Responsável pelo estado (`isOn()`) e pelas operações
  `turnOn()` e `turnOff()`. Inicia desligada; repetir a operação preserva o
  estado e exibe a mensagem novamente.
- `RemoteControl` — invocador. Mantém um `Map<String, Command>` e não
  recebe nenhum dispositivo no construtor.
  - `setCommand` recusa nome nulo ou vazio (`IllegalArgumentException`) e
    comando nulo (`NullPointerException`); um nome existente tem o comando
    substituído. Os nomes são comparados exatamente (`button1` ≠ `BUTTON1`).
    Configurar não executa o comando.
  - `pressButton` busca o comando e chama `execute()`; botão não
    configurado só exibe um aviso. Não há condicionais por nome de ação
    nem por tipo de comando.
  - `undoLast` desfaz o último comando executado por um botão válido e
    limpa a referência; sem ação disponível, exibe um aviso. Botões não
    configurados e reconfigurações não alteram essa referência.
- `Main` — cliente. Cria as lâmpadas, os comandos e o controle e monta as
  associações.

## Saída esperada

```
=== Initial states ===
State - Living room: off
State - Kitchen: off

=== Pressing button1, button2 and button3 ===
Living room: light on
Living room: light off
Kitchen: light on
State - Living room: off
State - Kitchen: on

=== Reconfiguring button1 to turn off the kitchen ===
Kitchen: light off
State - Living room: off
State - Kitchen: off

=== Pressing an unconfigured button ===
Warning: button button4 is not configured
Warning: button BUTTON1 is not configured

=== Turning on the kitchen (off) and undoing ===
Kitchen: light on
State - Living room: off
State - Kitchen: on
Warning: button button4 is not configured
Undoing last action
Kitchen: light off
State - Living room: off
State - Kitchen: off

=== Undoing again without a new action ===
Warning: there is no action to undo

=== Turning on the kitchen (already on) and undoing ===
Kitchen: light on
Kitchen: light on
State - Living room: off
State - Kitchen: on
Undoing last action
Kitchen: light on
State - Living room: off
State - Kitchen: on
```

Cenários pedidos e onde aparecem:

- `button1` liga a sala, `button2` desliga a sala e `button3` liga a
  cozinha; os três são acionados e os estados exibidos: segundo bloco.
- `button1` reconfigurado para desligar a cozinha: só a cozinha muda
  (terceiro bloco).
- Botão não configurado (`button4`) e nome com outra caixa (`BUTTON1`):
  quarto bloco.
- Desafio adicional: ligar a cozinha desligada e desfazer, com um botão não
  configurado e uma reconfiguração no meio, que não apagam a última ação
  (quinto bloco); desfazer sem ação disponível (sexto bloco); ligar a
  cozinha já ligada e desfazer, que a mantém ligada (último bloco).

## Desfazer (desafio adicional)

`undo()` entrou no contrato `Command`. Cada comando de iluminação guarda
`previousState = light.isOn()` antes de executar e, ao desfazer, liga ou
desliga a lâmpada de acordo com esse valor, em vez de executar a operação
oposta. O `RemoteControl` guarda só o último comando executado (um nível de
desfazer) e limpa a referência depois de usá-la.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Command?**
O `RemoteControl` dependia diretamente de `Light` e decidia, por
condicionais sobre um texto, qual método chamar. Novas ações ou
dispositivos exigiam mudar essas condicionais, e a solicitação não existia
como objeto. Agora cada solicitação é um `Command` que reúne o receptor e a
operação; o controle só associa botões a comandos e os executa, podendo ser
reconfigurado em tempo de execução.

**b) Quais classes representam o invocador, o receptor, os comandos concretos e o cliente na sua implementação?**
Invocador: `RemoteControl`. Receptor: `Light`. Comandos concretos:
`TurnOnLightCommand` e `TurnOffLightCommand` (abstração `Command`).
Cliente: `Main`.

**c) Como seria possível acrescentar um comando para outro dispositivo sem modificar RemoteControl?**
Criando o receptor (por exemplo, `Fan` com `start()` e `stop()`) e classes
que implementam `Command`, como `StartFanCommand`, que recebem o
dispositivo e delegam a operação. No cliente, basta
`remote.setCommand("button5", new StartFanCommand(fan))`. O controle
continua conhecendo só `Command`.

**d) Por que desfazer uma ação pode exigir armazenar o estado anterior, em vez de apenas executar a operação oposta?**
Porque a operação oposta supõe que a ação mudou o estado, o que nem sempre
acontece. Ligar uma lâmpada que já estava ligada não muda nada; desfazer
com "desligar" a deixaria desligada, um estado que nunca existiu antes da
ação. Guardar o estado anterior permite restaurar exatamente a situação de
antes. Em operações sem inversa simples (ajustar intensidade, mudar
várias propriedades), guardar o estado é a única forma confiável.

**e) Embora Command e Strategy encapsulem comportamentos, como representar uma solicitação difere de representar um algoritmo intercambiável?**
No Strategy, o objeto representa uma forma de fazer algo: o contexto
escolhe um algoritmo intercambiável (como calcular o frete) e o usa para
realizar sua própria tarefa, recebendo os dados e normalmente devolvendo um
resultado. No Command, o objeto representa um pedido completo — o que
fazer e em quem —, já ligado ao seu receptor; o invocador não sabe o que
acontece, só dispara. Por ser um objeto de solicitação, o Command pode ser
guardado, associado a botões, enfileirado, registrado e desfeito, guardando
dados sobre a execução, como o estado anterior.

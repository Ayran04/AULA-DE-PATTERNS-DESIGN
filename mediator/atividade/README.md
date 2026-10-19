# Atividade 019 — Padrão Mediator

Sistema de sala de chat refatorado com o padrão Mediator: a sala é o
mediador que registra os participantes e encaminha as mensagens. Cada
usuário conhece apenas a abstração `ChatMediator` e não guarda referências
para os demais usuários.

## Estrutura

- `ChatMediator` — abstração do mediador com `register(ChatUser)`,
  `send(String, ChatUser)` e `sendPrivate(String, ChatUser, String)`
  (desafio adicional).
- `ChatRoom` — mediador concreto. Mantém a lista de participantes
  registrados e centraliza todas as regras:
  - `register` rejeita `null` (`NullPointerException`), recusa usuários
    associados a outra sala, ignora a mesma instância já registrada e
    recusa outra instância com um nome já usado (comparação exata,
    diferenciando maiúsculas e minúsculas), preservando o cadastro original.
  - `send` recusa remetentes não registrados e encaminha a mensagem uma vez
    a cada outro participante; o remetente é identificado por comparação de
    instâncias (`!=`) e não recebe a própria mensagem.
  - `sendPrivate` recusa remetente não registrado, destinatário inexistente
    e destinatário igual ao remetente; caso contrário, entrega apenas ao
    destinatário.
- `ChatUser` — colega. Recebe nome (não vazio, senão
  `IllegalArgumentException`) e mediador (não nulo) pelo construtor, ambos
  fixos (`final`). `send` e `sendPrivate` apenas delegam ao mediador
  passando `this`; `receive` apenas exibe a mensagem, sem gerar novo envio.
  Não há lista de contatos nem `addContact()`.
- `Main` — cliente. Cria os usuários associados à sala e faz o registro
  explicitamente com `room.register(...)`.

## Saída esperada

```
=== Registering Ana, Bruno and Carla ===
Ana joined the room
Bruno joined the room
Carla joined the room
Registration ignored: Bruno is already registered

=== Ana sends to the room ===
Bruno received from Ana: Hello, everyone!
Carla received from Ana: Hello, everyone!

=== Diego sends before registering ===
Message refused: Diego is not registered

=== Diego joins and Bruno sends to the room ===
Diego joined the room
Ana received from Bruno: Hello, Ana!
Carla received from Bruno: Hello, Ana!
Diego received from Bruno: Hello, Ana!

=== Private messages ===
Carla received from Ana (private): Hi Carla, just for you.
Private message refused: Eduardo is not in the room
Private message refused: Ana cannot send to themselves
Private message refused: carla is not in the room

=== Another user named Ana ===
Registration refused: name Ana is already in use
Ana received from Bruno (private): Which Ana gets this?
Message refused: Ana is not registered
```

Cenários pedidos e onde aparecem:

- Registro duplicado da mesma instância de Bruno: primeiro bloco.
- Mensagem de Ana recebida só por Bruno e Carla, uma vez cada: segundo bloco.
- Recusa de envio por usuário associado à sala, mas não registrado
  (Diego): terceiro bloco.
- Diego registrado e mensagem de Bruno para Ana, Carla e Diego: quarto bloco.
- Desafio adicional: mensagem privada de Ana só para Carla, recusas para
  nome inexistente, para a própria Ana e para `carla` (nomes diferenciam
  maiúsculas e minúsculas); outra instância chamada "Ana" tem o cadastro
  recusado, a mensagem privada para "Ana" chega à Ana original e a nova
  instância não consegue enviar.

## Mensagens privadas (desafio adicional)

A nova operação exigiu atualizar o contrato `ChatMediator`
(`sendPrivate`), o mediador concreto e o ponto de delegação em `ChatUser`
(`sendPrivate(recipientName, message)`). A busca do destinatário pelo nome
e todas as validações ficaram no `ChatRoom`; o usuário só informa o nome do
destinatário. Para garantir nomes únicos, o `register` passou a recusar
outra instância com nome já registrado.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Mediator?**
Cada `ChatUser` mantinha sua própria lista de contatos e enviava as
mensagens diretamente a eles. Os usuários ficavam acoplados entre si
(muitos para muitos), a entrada de alguém exigia atualizar várias listas,
uma configuração incompleta deixava pessoas sem receber mensagens, e as
regras de encaminhamento ficavam espalhadas pelos participantes. Agora a
comunicação passa pela sala, que concentra quem participa e como as
mensagens são entregues.

**b) Quais abstrações e classes representam o mediador, o mediador concreto e os colegas participantes na sua implementação?**
Mediador: `ChatMediator`. Mediador concreto: `ChatRoom`. Colegas:
instâncias de `ChatUser` (Ana, Bruno, Carla e Diego).

**c) Como a entrada de um novo participante muda em relação ao código inicial e quais dependências deixam de existir entre os usuários?**
Antes, incluir Diego exigiria chamar `addContact` em todos os usuários
existentes e adicionar todos eles aos contatos de Diego. Agora basta uma
chamada a `room.register(diego)`, e ele passa a receber e enviar mensagens
para todos. Os usuários não guardam mais referências uns aos outros nem
chamam `receive` uns dos outros: cada um depende apenas de `ChatMediator`.

**d) Por que as regras de encaminhamento e de mensagens privadas devem ficar no mediador? Qual é o risco de concentrar responsabilidades demais nesse objeto?**
Porque são regras sobre a interação entre os participantes, e não sobre um
participante isolado. No mediador elas ficam em um único lugar: quem está
registrado, quem recebe, quem é excluído, como achar um destinatário. Mudar
a forma de comunicação não exige mexer nos usuários, e eles não precisam
conhecer uns aos outros. O risco é o mediador virar um "objeto deus":
acumulando cada nova operação (privadas, grupos, moderação, histórico,
silenciamento), ele cresce, fica difícil de entender e testar e passa a ser
o ponto de mudança de quase tudo. Para evitar isso, pode-se dividir
responsabilidades em colaboradores (por exemplo, um registro de
participantes ou políticas de entrega) ou em mediadores menores.

**e) Embora uma sala de chat e um sujeito observado possam distribuir mensagens a vários objetos, como Mediator e Observer diferem quanto à intenção e à organização das interações?**
No Observer, a intenção é avisar interessados sobre mudanças de estado de
um sujeito: a comunicação é de um para muitos e em um sentido só (a
estação notifica; os observadores não falam entre si nem com a estação),
e o sujeito não sabe o que cada observador faz com a notificação. No
Mediator, a intenção é coordenar a comunicação entre colegas que
interagem entre si: qualquer colega pode iniciar uma interação, o mediador
conhece os colegas e decide quem recebe o quê (todos menos o remetente,
apenas um destinatário, recusas). O Observer distribui eventos; o Mediator
encapsula as regras de colaboração entre vários objetos.

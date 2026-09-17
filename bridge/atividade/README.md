# Atividade 009 — Padrão Bridge

Sistema de notificações refatorado para separar o **tipo** de notificação
(`Notification`) do **canal de envio** (`NotificationSender`), permitindo
que ambas as dimensões evoluam de forma independente.

## Estrutura

- `NotificationSender` — implementação (canal de envio), com a operação
  `send(String message)`.
- `EmailSender`, `SmsSender`, `WhatsAppSender` — implementações concretas
  do canal.
- `Notification` — abstração. Classe abstrata que mantém uma referência a
  `NotificationSender` e delega o envio a ela.
- `AlertNotification`, `ReminderNotification`, `WarningNotification` —
  refinamentos da abstração; cada um complementa a mensagem com seu
  próprio prefixo e delega o envio ao `sender` recebido.
- `Main` — demonstra várias combinações entre tipos de notificação e
  canais (incluindo o canal e o tipo adicionados no desafio).

## Novo canal e novo tipo (desafio adicional)

`WhatsAppSender` foi adicionado apenas implementando `NotificationSender`,
e `WarningNotification` apenas estendendo `Notification` — nenhuma classe
existente precisou ser alterada. Isso é possível porque tipo e canal são
duas hierarquias independentes conectadas por composição, não por herança
combinada.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Bridge?**
A explosão de classes causada por combinar tipo de notificação e canal de
envio em uma única hierarquia (`EmailAlertNotification`,
`SmsReminderNotification` etc.). Cada novo tipo ou canal multiplicava o
número de classes necessárias.

**b) O que representa a abstração na sua implementação?**
A classe `Notification` (e suas subclasses `AlertNotification`,
`ReminderNotification`, `WarningNotification`), que representa o "o quê"
está sendo comunicado, sem se preocupar em como a mensagem é
efetivamente transmitida.

**c) O que representa a implementação na sua solução?**
A interface `NotificationSender` e suas implementações concretas
(`EmailSender`, `SmsSender`, `WhatsAppSender`), que representam "como" a
mensagem é enviada.

**d) Qual é a diferença entre utilizar Bridge e criar uma classe para cada combinação possível?**
Com Bridge, tipo e canal variam independentemente e se combinam por
composição em tempo de execução — N tipos e M canais exigem N + M
classes. Criando uma classe por combinação, o crescimento é
multiplicativo (N × M classes), com código duplicado entre combinações
que compartilham o mesmo tipo ou o mesmo canal.

**e) Em quais situações o padrão Bridge é mais indicado?**
Quando existem duas (ou mais) dimensões de variação que podem evoluir
separadamente, quando se quer evitar uma hierarquia de classes que cresce
de forma combinatória, e quando é desejável trocar a implementação
(canal) em tempo de execução sem alterar a abstração que a utiliza.

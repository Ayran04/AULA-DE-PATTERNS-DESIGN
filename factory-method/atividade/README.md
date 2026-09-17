# Atividade Prática — Padrão Factory Method

Sistema de notificações refatorado para que a criação de cada tipo de
notificação (Email, SMS, Push, WhatsApp) seja delegada a subclasses de um
criador (`NotificationService`), sem estruturas condicionais decidindo qual
classe concreta instanciar.

## Estrutura

- `Notification` — abstração das notificações, com a operação
  `send(String message)`.
- `EmailNotification`, `SmsNotification`, `PushNotification`,
  `WhatsAppNotification` — produtos concretos (implementações de
  `Notification`).
- `NotificationService` — criador (creator). Classe abstrata com o
  **Factory Method** `createNotification()` e o método `sendNotification`,
  que usa o produto criado sem conhecer sua classe concreta.
- `EmailNotificationService`, `SmsNotificationService`,
  `PushNotificationService`, `WhatsAppNotificationService` — criadores
  concretos, cada um sobrescrevendo `createNotification()` para
  instanciar o respectivo produto.
- `Main` — demonstra o envio de notificação pelos quatro canais.

## WhatsApp (desafio adicional)

Adicionar o WhatsApp exigiu apenas duas classes novas —
`WhatsAppNotification` e `WhatsAppNotificationService` — sem alterar
`Notification`, `NotificationService` ou qualquer um dos serviços já
existentes.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com a aplicação do Factory Method?**
`NotificationService` criava diretamente `new EmailNotification()`,
acoplando a lógica de envio a uma implementação concreta específica. Ao
introduzir novos tipos, a alternativa natural seria um conjunto de
`if/else` decidindo qual classe instanciar, o que tende a crescer e ficar
difícil de manter conforme novos tipos são adicionados.

**b) Qual é a principal vantagem da solução quando novos tipos de notificações precisam ser adicionados?**
Um novo tipo de notificação é adicionado criando uma nova implementação
de `Notification` e um novo criador concreto que sobrescreve
`createNotification()` — sem tocar no código já existente
(`NotificationService` e os demais serviços concretos permanecem
inalterados), respeitando o princípio Open/Closed.

**c) Onde está o Factory Method na sua implementação?**
No método abstrato `createNotification()`, declarado em
`NotificationService` e implementado por cada subclasse
(`EmailNotificationService`, `SmsNotificationService`,
`PushNotificationService`, `WhatsAppNotificationService`), cada uma
decidindo qual `Notification` concreta instanciar.

**d) Qual é o papel do polimorfismo nessa solução?**
`sendNotification()`, definido uma única vez em `NotificationService`,
chama `createNotification()` e `notification.send(...)` através das
referências abstratas `Notification`/`NotificationService`. Em tempo de
execução, o polimorfismo garante que a implementação correta de
`createNotification()` (da subclasse concreta usada) e de `send()` (do
produto concreto retornado) seja executada, sem que `sendNotification()`
precise de nenhuma verificação de tipo.

**e) Apenas criar uma interface Notification e suas diferentes implementações seria suficiente para afirmar que o sistema utiliza o padrão Factory Method? Justifique.**
Não. Isso por si só é apenas o uso de **polimorfismo/interface**
(equivalente ao "produto" do padrão). O Factory Method exige também a
parte do **criador**: um método (o factory method) cuja responsabilidade
é decidir/encapsular qual implementação concreta instanciar, geralmente
delegado a subclasses do criador. Sem o `NotificationService` abstrato e
seus criadores concretos delegando essa decisão, o código que precisa de
uma notificação ainda teria que escolher e instanciar diretamente a
classe concreta correta (com `new` ou condicionais), o que é exatamente o
acoplamento que o padrão busca eliminar.

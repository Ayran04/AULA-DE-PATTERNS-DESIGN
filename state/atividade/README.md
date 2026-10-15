# Atividade 017 — Padrão State

Sistema de acompanhamento de pedidos refatorado com o padrão State: cada
estado do pedido é um objeto que decide o que cada ação faz naquele momento
do ciclo de vida e para qual estado o pedido vai em seguida. O `Order`
apenas delega as ações ao estado atual.

## Estrutura

- `OrderState` — abstração de estado com `pay(Order)`, `ship(Order)`,
  `cancel(Order)`, `deliver(Order)` e `getName()`.
- `PendingState` (`PENDING`), `PaidState` (`PAID`), `ShippedState`
  (`SHIPPED`), `CancelledState` (`CANCELLED`) e `DeliveredState`
  (`DELIVERED`, desafio adicional) — estados concretos.
- `Order` — contexto. Começa em `PendingState`; `pay()`, `ship()`,
  `cancel()` e `deliver()` delegam ao estado atual passando `this`.
  `changeState(OrderState)` é usada pelos estados para fazer as transições
  e rejeita `null` (`NullPointerException`). `getStatus()` retorna
  `state.getName()`. Não há nenhuma condicional por estado.
- `Main` — cliente. Usa somente as ações do pedido (nunca `changeState`) e
  exibe o estado após cada ação.

## Transições

| Estado      | pay        | ship       | cancel      | deliver      |
|-------------|------------|------------|-------------|--------------|
| `PENDING`   | → `PAID`   | recusa     | → `CANCELLED` | recusa     |
| `PAID`      | recusa     | → `SHIPPED`| → `CANCELLED` | recusa     |
| `SHIPPED`   | recusa     | recusa     | recusa      | → `DELIVERED` |
| `DELIVERED` | recusa     | recusa     | recusa      | recusa       |
| `CANCELLED` | recusa     | recusa     | recusa      | recusa       |

Toda recusa exibe uma mensagem e mantém o estado atual.

## Saída esperada

```
=== Order 1: full flow (pay, ship, deliver) ===
Status: PENDING
Shipping not allowed: order has not been paid
Status: PENDING
Payment confirmed
Status: PAID
Order shipped
Status: SHIPPED
Cancellation not allowed: order is already shipped
Status: SHIPPED
Order delivered
Status: DELIVERED
Cancellation not allowed: order is already delivered
Status: DELIVERED

=== Order 2: cancel a pending order ===
Status: PENDING
Order cancelled
Status: CANCELLED
Payment not allowed: order is cancelled
Status: CANCELLED

=== Order 3: cancel a paid order ===
Status: PENDING
Payment confirmed
Status: PAID
Delivery not allowed: order has not been shipped
Status: PAID
Order cancelled
Status: CANCELLED
```

Cenários pedidos e onde aparecem:

- Envio antes do pagamento: pedido 1, primeira ação.
- Pagamento e envio: pedido 1.
- Recusa de cancelamento após o envio: pedido 1.
- Cancelamento de pedido pendente: pedido 2.
- Recusa de pagamento de pedido cancelado: pedido 2.
- Cancelamento de pedido pago: pedido 3.
- Desafio adicional: fluxo completo com entrega e cancelamento recusado
  após a entrega (pedido 1) e entrega recusada antes do envio (pedido 3).

## Registro de entrega (desafio adicional)

A nova operação exigiu mudar o contrato: `deliver(Order)` foi adicionado a
`OrderState` e, por consequência, implementado em todos os estados
concretos; `Order` ganhou `deliver()` delegando ao estado. A nova transição
ficou apenas em `ShippedState`, que é o único estado que permite entregar,
e o novo `DeliveredState` recusa todas as ações. Os demais estados apenas
recusam a entrega, sem mudar suas regras existentes.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o State?**
O `Order` guardava o estado como texto e repetia `if/else` sobre esse texto
em cada operação. As regras de todos os estados estavam misturadas em uma
só classe, e acrescentar um estado ou mudar uma transição exigia revisar
várias condicionais, com risco de liberar operações indevidas. Agora cada
estado concentra suas próprias regras e o `Order` não tem condicionais.

**b) Quais classes representam o contexto, a abstração de estado e os estados concretos na sua implementação?**
Contexto: `Order`. Abstração de estado: `OrderState`. Estados concretos:
`PendingState`, `PaidState`, `ShippedState`, `CancelledState` e
`DeliveredState`.

**c) Onde ficam as regras de transição e por que uma operação recusada deve preservar o estado atual?**
Nos próprios estados concretos: cada um decide, em cada operação, se ela é
permitida e chama `order.changeState(...)` com o próximo estado. Uma
operação recusada não aconteceu de fato — o pedido não foi pago, enviado
ou cancelado —, então mudar o estado faria o sistema registrar algo falso
e liberar ações seguintes que não deveriam ser possíveis.

**d) Embora State e Strategy utilizem composição e delegação, como suas intenções e a responsabilidade pela troca dos objetos se diferenciam neste sistema?**
No Strategy (cálculo de frete), as estratégias são algoritmos
intercambiáveis e independentes entre si; quem escolhe e troca a
estratégia é o cliente, e as estratégias não conhecem umas às outras nem
alteram a calculadora. No State, os objetos representam etapas do ciclo de
vida do pedido; quem troca o estado são os próprios estados, como
consequência das ações, e o cliente nem sabe quais classes existem — só
chama `pay()`, `ship()` etc. A intenção do State é modelar comportamento
que muda conforme a situação interna do objeto.

**e) Quais partes da solução precisam mudar ao acrescentar um estado e ao acrescentar uma operação comum a todos os estados?**
Novo estado: cria-se a classe concreta e alteram-se apenas os estados que
devem levar a ele (como `ShippedState` levando a `DeliveredState`); o
`Order` e o contrato não mudam. Nova operação: muda o contrato
`OrderState`, todas as implementações de estado (cada uma decide se
permite ou recusa) e o `Order`, que ganha o método público que delega.
O State facilita acrescentar estados, mas acrescentar operações afeta
todas as classes de estado.

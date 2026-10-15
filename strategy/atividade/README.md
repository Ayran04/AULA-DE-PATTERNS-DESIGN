# Atividade 015 — Padrão Strategy

Sistema de cálculo de frete refatorado com o padrão Strategy: cada
modalidade de entrega é um algoritmo encapsulado em uma classe própria, e a
`ShippingCalculator` apenas delega o cálculo à estratégia que recebeu,
podendo trocá-la em tempo de execução.

## Estrutura

- `ShippingStrategy` — abstração (estratégia) com a operação
  `calculate(double weight)`, que retorna o valor do frete.
- `StandardShipping` — R$ 10,00 mais R$ 2,00 por quilograma.
- `ExpressShipping` — R$ 20,00 mais R$ 5,00 por quilograma.
- `StorePickup` — retirada na loja, sem cobrança de frete.
- `EconomyShipping` — R$ 5,00 mais R$ 1,50 por quilograma (desafio adicional).
- `ShippingCalculator` — contexto. Recebe a estratégia pelo construtor,
  permite substituí-la com `setStrategy(...)` e delega `calculate(weight)`.
  Rejeita estratégia nula (`NullPointerException`) no construtor e no setter.
- `Main` — cliente. Cria as estratégias e as troca na mesma calculadora.

## Saída esperada

```
Standard: R$ 16,00
Express: R$ 35,00
Pickup: R$ 0,00
Economy: R$ 9,50
```

Todos os valores são para um pedido de 3 kg.

## Modalidade econômica (desafio adicional)

Bastou criar a classe `EconomyShipping` implementando `ShippingStrategy` e
selecioná-la no `Main` com `setStrategy(new EconomyShipping())`. Nenhuma
linha de `ShippingCalculator` ou das estratégias existentes foi alterada.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Strategy?**
A `ShippingCalculator` concentrava todas as fórmulas em uma cadeia de
`if/else` por texto. Cada nova modalidade exigia alterar essa classe, as
regras ficavam misturadas com a lógica de seleção e um texto inválido só
era detectado em tempo de execução. Agora cada fórmula vive em sua própria
classe, a calculadora não tem condicionais e o cliente escolhe uma
estratégia por tipo, sem textos que possam estar errados.

**b) Quais classes representam o contexto, a estratégia e as estratégias concretas na sua implementação?**
Contexto: `ShippingCalculator`. Estratégia: `ShippingStrategy`.
Estratégias concretas: `StandardShipping`, `ExpressShipping`,
`StorePickup` e `EconomyShipping`.

**c) Como a composição e a delegação permitem trocar o algoritmo durante a execução?**
A calculadora *tem uma* `ShippingStrategy` (composição) em vez de herdar ou
embutir as fórmulas. Ao calcular, ela apenas repassa a chamada para a
estratégia guardada (delegação). Como essa referência pode ser substituída
por `setStrategy`, o mesmo objeto calculadora passa a usar outro algoritmo
sem ser recriado.

**d) Qual é a responsabilidade do cliente na seleção da estratégia e por que essa escolha não deve ficar dentro da calculadora?**
O cliente conhece o contexto da compra (o que o usuário escolheu) e por
isso decide qual estratégia criar e entregar à calculadora. Se a escolha
ficasse na calculadora, ela voltaria a ter condicionais por modalidade e
dependeria de todas as classes concretas, e cada nova modalidade exigiria
alterá-la de novo, que é justamente o problema original.

**e) Como a inclusão de uma nova modalidade de entrega se relaciona com o princípio Open/Closed (OCP)?**
O sistema fica aberto para extensão e fechado para modificação: a
modalidade econômica foi adicionada criando uma nova classe que implementa
`ShippingStrategy`, sem modificar a `ShippingCalculator` nem as estratégias
já existentes e testadas. Só o cliente muda, para passar a selecioná-la.

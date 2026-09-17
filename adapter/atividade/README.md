# Atividade 008 — Padrão Adapter

Sistema de pagamentos refatorado para que `OrderService` dependa apenas da
abstração `PaymentProcessor`, permitindo integrar o serviço externo
`ExternalPaymentGateway` sem alterar sua interface original.

## Estrutura

- `PaymentProcessor` — abstração esperada pelo sistema, com a operação
  `pay(double amount)`.
- `CreditCardPayment` — implementação local que já falava a linguagem do
  sistema; passou a implementar `PaymentProcessor` diretamente.
- `ExternalPaymentGateway` — classe da equipe externa, **não alterada**;
  continua expondo `makePayment(String currency, double value)`.
- `PaymentGatewayAdapter` — Adapter. Implementa `PaymentProcessor` e
  encapsula uma instância de `ExternalPaymentGateway`, convertendo a
  chamada `pay(amount)` em `makePayment(currency, amount)`.
- `OrderService` — depende apenas de `PaymentProcessor`, recebido por
  injeção no construtor; não conhece `ExternalPaymentGateway`.
- `Main` — demonstra `OrderService` processando pagamento local (cartão de
  crédito) e pagamentos via gateway externo.

## Múltiplas moedas (desafio adicional)

A moeda é resolvida inteiramente dentro do Adapter: `PaymentGatewayAdapter`
recebe a moeda (`"BRL"`, `"USD"`, `"EUR"`) no construtor e a mantém como
detalhe interno de integração. `OrderService` continua chamando apenas
`pay(amount)`, sem saber que existe conceito de moeda — basta criar um
`PaymentGatewayAdapter` diferente para cada moeda desejada.

## Questões para reflexão

**a) Qual problema existente na integração com o serviço externo foi resolvido com o Adapter?**
A incompatibilidade entre a interface que o sistema espera (`pay(double amount)`)
e a interface que o serviço externo oferece (`makePayment(String currency, double value)`).
Sem o Adapter, `OrderService` (ou outras classes) precisariam conhecer e
chamar diretamente o método do gateway externo, espalhando detalhes de
integração pelo sistema.

**b) Qual classe representa o Adapter na sua implementação?**
`PaymentGatewayAdapter`. Ela implementa `PaymentProcessor` e traduz
internamente a chamada para `ExternalPaymentGateway.makePayment(...)`.

**c) Qual é a diferença entre a interface esperada pelo sistema e a interface fornecida pelo serviço externo?**
O sistema espera um método simples, `pay(double amount)`, focado apenas no
valor. O serviço externo exige `makePayment(String currency, double value)`,
que além do valor também exige explicitamente a moeda da transação — um
parâmetro extra e uma assinatura diferente.

**d) Por que não é recomendado alterar diretamente a classe ExternalPaymentGateway?**
Porque ela pertence a outra equipe/sistema externo: alterá-la poderia
quebrar outros consumidores dessa classe, não é garantido que a mudança
seria aceita ou mantida em futuras atualizações da API externa, e o
sistema perderia a fronteira clara entre "meu código" e "código de
terceiros".

**e) Em quais situações o padrão Adapter é mais indicado do que modificar todas as classes clientes?**
Quando a classe incompatível não pode ou não deve ser alterada (código de
terceiros, biblioteca externa, legado), quando existem ou podem existir
vários clientes que já dependem da interface atual, e quando é desejável
isolar os detalhes de uma integração específica em um único ponto do
sistema, em vez de espalhar esse conhecimento por todas as classes que
precisam daquele serviço.

# Atividade 011 — Padrão Decorator

Sistema de pedidos de bebidas refatorado para adicionar responsabilidades
(adicionais) a uma bebida em tempo de execução, através de composição, em
vez de criar uma subclasse para cada combinação de bebida e adicionais.

## Estrutura

- `Beverage` — abstração (component), com `getDescription()` e
  `getCost()`.
- `Coffee` (R$ 5,00) e `Tea` (R$ 4,00) — bebidas básicas (componentes
  concretos).
- `BeverageDecorator` — decorador base. Implementa `Beverage` e mantém uma
  referência para outro `Beverage`, recebida no construtor.
- `MilkDecorator` (+R$ 1,50), `ChocolateDecorator` (+R$ 2,00),
  `WhippedCreamDecorator` (+R$ 2,50) — decoradores concretos. Cada um
  delega a descrição e o custo à bebida envolvida e soma sua própria
  contribuição.
- `Main` — demonstra: uma bebida sem adicionais, café com leite e
  chocolate (R$ 8,50), chá com leite (R$ 5,50), café com o mesmo
  adicional aplicado duas vezes (double chocolate) e uma combinação com
  vários adicionais diferentes, incluindo caramelo.

## Caramelo (desafio adicional)

`CaramelDecorator` (+R$ 1,00) foi adicionado como mais uma implementação
de `BeverageDecorator`, sem alterar `Beverage`, `Coffee`, `Tea` ou
qualquer decorador já existente. Em `Main`, ele é aplicado sobre uma
bebida que já possui outros adicionais (`WhippedCreamDecorator` e
`MilkDecorator`), demonstrando que decoradores se empilham livremente.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Decorator?**
A explosão de classes causada por representar cada combinação de bebida e
adicionais como uma classe própria (`CoffeeWithMilk`,
`CoffeeWithMilkAndChocolate` etc.), com preço e descrição duplicados e
recalculados manualmente em cada uma.

**b) Quais classes representam o componente, os componentes concretos, o decorador base e os decoradores concretos na sua implementação?**
Componente: `Beverage`. Componentes concretos: `Coffee` e `Tea`.
Decorador base: `BeverageDecorator`. Decoradores concretos:
`MilkDecorator`, `ChocolateDecorator`, `WhippedCreamDecorator` e
`CaramelDecorator`.

**c) Por que o decorador deve implementar a mesma abstração do objeto que ele envolve?**
Porque isso é o que permite empilhar decoradores livremente: como
`BeverageDecorator` também é um `Beverage`, um decorador pode envolver
outro decorador (ou uma bebida básica) indistintamente, e o resultado
final continua podendo ser tratado como um `Beverage` comum pelo código
cliente — sem precisar saber quantos adicionais foram aplicados.

**d) Como a composição e a delegação permitem combinar adicionais sem criar uma subclasse para cada combinação?**
Cada decorador guarda uma referência a um `Beverage` (composição) e, ao
calcular sua descrição/custo, primeiro delega a chamada a esse objeto
envolvido e depois soma sua própria contribuição. Como qualquer `Beverage`
pode ser passado ao construtor de qualquer decorador, as combinações
surgem do encadeamento de objetos em tempo de execução, não de uma
hierarquia fixa de classes definida em tempo de compilação.

**e) Como a inclusão de um novo adicional se relaciona com o princípio Open/Closed (OCP)?**
`CaramelDecorator` foi adicionado criando uma classe nova, sem modificar
nenhuma linha de `Beverage`, `Coffee`, `Tea` ou dos decoradores já
existentes — o sistema ficou aberto para extensão (novo comportamento)
e fechado para modificação (código existente permanece intacto), que é
exatamente a essência do OCP.

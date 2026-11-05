# Atividade 024 — Padrão Visitor

Catálogo de produtos refatorado com o padrão Visitor: cada operação sobre o
catálogo (relatório, preço promocional, total) é um visitante com um método
`visit` para cada tipo de produto. Cada produto aceita o visitante e chama
o método correspondente ao seu próprio tipo, então ninguém precisa
verificar tipos com `instanceof` nem fazer conversões.

## Estrutura

- `Product` — elemento (interface) com `void accept(ProductVisitor)`.
- `Book` e `Electronics` — elementos concretos. Mantêm os dados e os
  métodos de consulta originais; os campos passaram a ser `final` e o
  construtor rejeita título/modelo vazio ou nulo e preço não positivo.
  Cada um implementa `accept` com `visitor.visit(this)`, e nada mais.
- `ProductVisitor` — visitante (interface) com `visit(Book)` e
  `visit(Electronics)`.
- `ProductReportVisitor` — visitante concreto com as regras de exibição
  que antes ficavam em `ProductReport`: tipo, título ou modelo e preço
  original em centavos.
- `PromotionalPriceVisitor` — visitante concreto que exibe o título ou
  modelo e o preço promocional: 10% de desconto em livros e 5% em
  eletrônicos. O cálculo usa `long` na multiplicação
  (`(long) preço * (100 - desconto) / 100`), e a fração de centavo só é
  descartada na divisão final.
- `TotalPriceVisitor` — visitante concreto do desafio adicional. Soma os
  preços originais em um acumulador `long`, exposto por
  `getTotalInCents()`, sem imprimir nada.
- `Main` — cliente. O método `visitAll(List<Product>, ProductVisitor)`
  percorre a coleção chamando apenas `product.accept(visitor)` e depende
  só de `Product` e `ProductVisitor`.

`ProductReport` foi removida: suas regras foram para
`ProductReportVisitor` e o percurso ficou em `visitAll`.

## Saída esperada

```
=== Report ===
Book: Design Patterns - cents: 10000
Electronics: Headphones - cents: 20000

=== Promotional prices ===
Design Patterns - promotional cents: 9000
Headphones - promotional cents: 19000

=== Report again (original prices preserved) ===
Book: Design Patterns - cents: 10000
Electronics: Headphones - cents: 20000

=== Empty catalog ===

=== Book with fractional discount ===
Book: Pocket Guide - cents: 999
Pocket Guide - promotional cents: 899

=== Total price (additional challenge) ===
Catalog total cents: 30000
Empty catalog total cents: 0
```

Cenários pedidos e onde aparecem:

- Relatório e preços promocionais de 9000 e 19000 centavos sobre a mesma
  coleção: primeiro e segundo blocos.
- Novo relatório confirmando que os preços originais foram preservados:
  terceiro bloco.
- Coleção vazia, com os dois visitantes, sem nenhuma linha de produto:
  quarto bloco.
- Livro de 999 centavos com preço promocional de 899
  (999 × 90 / 100 = 899,1, fração descartada): quinto bloco.
- Desafio adicional: total de 30000 centavos para os dois produtos e zero
  para a coleção vazia, cada cálculo com uma instância nova: último bloco.

## Valor total (desafio adicional)

A nova operação exigiu apenas criar `TotalPriceVisitor` e usá-lo no
cliente. `Product`, `Book`, `Electronics`, `ProductVisitor` e os visitantes
existentes não foram alterados.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Visitor?**
`ProductReport` escolhia o comportamento com `instanceof` e conversões de
tipo, e cada nova operação (como o preço promocional) repetiria a mesma
cadeia de verificações em outro serviço, com o risco de esquecer um tipo
sem nenhum aviso do compilador. Colocar as operações dentro dos produtos
resolveria a seleção, mas encheria `Book` e `Electronics` de regras de
relatório e campanha. Com o Visitor, cada operação fica em uma classe
própria, a seleção do método é feita pelo despacho duplo, e o compilador
exige que todo visitante trate todos os tipos.

**b) Quais abstrações e classes representam os elementos, os elementos concretos, o visitante e os visitantes concretos na sua implementação?**
Elemento: `Product`. Elementos concretos: `Book` e `Electronics`.
Visitante: `ProductVisitor`. Visitantes concretos: `ProductReportVisitor`,
`PromotionalPriceVisitor` e `TotalPriceVisitor`.

**c) Como a chamada product.accept(visitor) seguida de visitor.visit(this) permite selecionar o comportamento adequado? Por que apenas sobrecarregar visit() e chamar visitor.visit(product) no cliente não produz o mesmo resultado?**
São dois despachos. Em `product.accept(visitor)`, o polimorfismo
(despacho dinâmico) escolhe o `accept` da classe real do produto. Dentro
dele, `this` tem o tipo estático `Book` (ou `Electronics`), então o
compilador escolhe a sobrecarga `visit(Book)` (ou `visit(Electronics)`), e
o despacho dinâmico, de novo, escolhe a implementação do visitante
concreto. Em Java, a sobrecarga é resolvida em tempo de compilação pelo
tipo estático do argumento. No cliente, `product` tem tipo `Product`, e
como não existe `visit(Product)`, `visitor.visit(product)` nem compila; se
existisse, seria sempre essa a chamada, qualquer que fosse o tipo real.
Por isso `accept` precisa estar em cada classe concreta.

**d) Quais mudanças seriam necessárias para incluir um novo tipo Furniture? Como esse custo difere do custo de acrescentar uma nova operação por meio de outro visitante?**
Seria preciso criar `Furniture` implementando `accept` com
`visitor.visit(this)`, acrescentar `visit(Furniture)` em `ProductVisitor`
e implementar esse método em todos os visitantes concretos (relatório,
promoção, total e quaisquer outros, inclusive de terceiros). Ou seja, um
novo tipo afeta toda a hierarquia de visitantes. Já uma nova operação é
uma única classe nova, sem tocar em produtos nem visitantes existentes,
como aconteceu com `TotalPriceVisitor`. O Visitor compensa quando os tipos
são estáveis e as operações mudam com frequência; se os tipos mudam muito,
ele vira um custo.

**e) Por que reutilizar um visitante com acumulador pode alterar o resultado de um novo percurso? Que cuidados são necessários para que os visitantes acessem os dados sem expor desnecessariamente o estado interno dos elementos?**
`TotalPriceVisitor` guarda o total entre as chamadas de `visit`. Se a
mesma instância percorrer o catálogo de novo (ou outra coleção), os
valores são somados ao total anterior, e o resultado sai dobrado ou
misturado. Por isso cada cálculo independente usa uma instância nova (o
total da coleção vazia foi calculado com outra instância e deu zero).
Quanto ao encapsulamento, o Visitor exige que os elementos ofereçam os
dados de que as operações precisam, o que tende a abrir o estado interno.
Os cuidados são: expor apenas consultas (sem setters), com campos
`final` e tipos imutáveis (`String`, `int`), para que os visitantes não
consigam modificar os produtos; não devolver coleções ou objetos
mutáveis internos sem cópia defensiva; e expor só o necessário para as
operações, em vez de todos os detalhes de implementação.

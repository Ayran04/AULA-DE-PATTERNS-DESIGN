# Atividade 021 — Padrão Chain of Responsibility

Sistema de aprovação de despesas refatorado com o padrão Chain of
Responsibility: cada nível de aprovação é um objeto que aprova a despesa se
ela estiver dentro do seu limite ou a encaminha ao próximo da cadeia. O
cliente envia todas as solicitações ao primeiro elemento, sem escolher o
aprovador.

## Estrutura

- `ExpenseApprover` — tratador (classe abstrata). Guarda a referência
  `next` e oferece `setNext(ExpenseApprover)` e `approve(ExpenseRequest)`.
  A lógica de encaminhamento fica aqui:
  - valor menor ou igual ao limite (inclusivo): exibe o aprovador e a
    despesa e encerra, sem encaminhar;
  - acima do limite, com próximo: encaminha a mesma solicitação;
  - acima do limite, sem próximo (`setNext(null)` ou nunca configurado):
    exibe uma única mensagem de que não há aprovador disponível.
- `TeamLeaderApprover` (R$ 500,00), `ManagerApprover` (R$ 2.000,00),
  `DirectorApprover` (R$ 10.000,00) e `PresidentApprover` (R$ 50.000,00,
  desafio adicional) — tratadores concretos. Cada um informa apenas o
  próprio limite em centavos e o próprio nome, sem conhecer os demais.
- `ExpenseRequest` — solicitação. Mantém as validações originais
  (descrição não vazia, valor positivo em centavos) e os métodos de
  consulta; os campos passaram a ser `final`.
- `Main` — cliente. Monta a cadeia
  `TeamLeaderApprover → ManagerApprover → DirectorApprover` e envia tudo
  para o líder.

`ExpenseApprovalService` foi removida: sem a seleção centralizada, ela
apenas repassaria a chamada ao início da cadeia.

## Saída esperada

```
=== Original chain: Team leader -> Manager -> Director ===
Team leader approved: Office supplies (R$ 300,00)
Manager approved: Training (R$ 1.500,00)
Director approved: Equipment (R$ 8.000,00)
No approver available: Office renovation (R$ 15.000,00)

=== Exact limits ===
Team leader approved: Team lunch (R$ 500,00)
Manager approved: Conference ticket (R$ 2.000,00)
Director approved: Laptops (R$ 10.000,00)

=== Extended chain: ... -> Director -> President ===
Team leader approved: Office supplies (R$ 300,00)
Manager approved: Training (R$ 1.500,00)
Director approved: Equipment (R$ 8.000,00)
President approved: Office renovation (R$ 15.000,00)
President approved: Office expansion (R$ 50.000,00)
No approver available: New branch (R$ 50.000,01)
```

Cenários pedidos e onde aparecem:

- R$ 300,00, R$ 1.500,00, R$ 8.000,00 e R$ 15.000,00 aprovados pelo líder,
  gerente, diretor e sem aprovador: primeiro bloco.
- Limites exatos de R$ 500,00, R$ 2.000,00 e R$ 10.000,00, cada um com uma
  única aprovação, pelo próprio responsável do limite: segundo bloco.
- Desafio adicional: com a presidência no fim da cadeia, as despesas
  menores continuam com os mesmos aprovadores, R$ 15.000,00 passa a ser
  aprovada pela presidência, R$ 50.000,00 é aprovada no limite e
  R$ 50.000,01 fica sem aprovador: terceiro bloco.

## Presidência (desafio adicional)

A extensão exigiu apenas criar `PresidentApprover` e mudar a montagem no
cliente: `director.setNext(new PresidentApprover())`. Nenhum aprovador
existente nem `ExpenseApprover` foi alterado.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Chain of Responsibility?**
`ExpenseApprovalService` conhecia todos os níveis e seus limites em uma
sequência fixa de `if/else`. Incluir um responsável ou mudar a ordem
exigia alterar essa classe, e não dava para montar fluxos diferentes
combinando os aprovadores. Agora cada nível é um objeto que conhece só o
próprio limite e o próximo elo; o fluxo é montado por composição no
cliente, que só envia a solicitação ao início da cadeia.

**b) Quais abstrações e classes representam o tratador, os tratadores concretos, a solicitação e o cliente na sua implementação?**
Tratador: `ExpenseApprover`. Tratadores concretos: `TeamLeaderApprover`,
`ManagerApprover`, `DirectorApprover` e `PresidentApprover`. Solicitação:
`ExpenseRequest`. Cliente: `Main`.

**c) O que aconteceria com as despesas de menor valor se o diretor fosse colocado no início da cadeia? Por que a ordem dos responsáveis faz parte da configuração do fluxo?**
O diretor aprovaria todas as despesas de até R$ 10.000,00, inclusive as de
R$ 300,00 e R$ 1.500,00, e o líder e o gerente nunca seriam acionados, já
que o primeiro capaz de tratar encerra o processamento. Cada aprovador só
olha o próprio limite, então é a ordem da cadeia que garante que a despesa
chegue ao responsável de menor nível capaz de aprová-la. Mudar a ordem muda
o fluxo, por isso ela faz parte da configuração montada no cliente.

**d) Como a solução trata uma solicitação que nenhum responsável consegue aprovar e por que esse caso precisa estar previsto?**
O último elo, ao receber um valor acima do limite e sem próximo,
exibe uma única mensagem "No approver available" e encerra. Sem esse caso,
a solicitação seria descartada em silêncio (ou causaria um
`NullPointerException` ao chamar o próximo inexistente), e o solicitante
não saberia que a despesa não foi aprovada. Em uma cadeia, chegar ao fim
sem tratamento é um resultado possível e precisa ser explícito.

**e) Como o encaminhamento entre responsáveis difere da notificação de todos os interessados no Observer? Por que esta cadeia deve parar após a primeira aprovação?**
No Observer, o sujeito avisa todos os observadores registrados, e cada um
reage de forma independente ao mesmo evento. Na Chain of
Responsibility, a solicitação passa de um elo para o próximo em ordem, e
cada elo decide se trata ou encaminha; em geral só um a trata. Aqui a
cadeia deve parar na primeira aprovação porque uma despesa precisa de uma
única aprovação, do responsável de menor nível com alçada: continuar
geraria aprovações duplicadas e acionaria níveis superiores sem
necessidade.

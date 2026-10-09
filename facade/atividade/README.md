# Atividade 012 — Padrão Facade

Sistema de Home Theater refatorado para que o código cliente inicie e
encerre uma sessão de cinema por meio de uma única classe,
`HomeTheaterFacade`, sem precisar conhecer cada equipamento nem a ordem
correta das operações.

## Estrutura

- `Projector`, `SoundSystem`, `StreamingPlayer`, `RoomLights` — subsistemas.
  Mantêm exatamente as mesmas operações do código original e não conhecem
  a fachada.
- `ProjectionScreen` — novo subsistema (desafio adicional), com as
  operações `lower()` e `raise()`.
- `HomeTheaterFacade` — Facade. Recebe todos os equipamentos pelo
  construtor e expõe duas operações simples:
  - `watchMovie(String movie)`: diminui as luzes, abaixa a tela, liga o
    projetor, liga o som, ajusta o volume para 20 e inicia o filme.
  - `endMovie()`: interrompe o filme, desliga o som, desliga o projetor,
    recolhe a tela e acende as luzes.
- `Main` — cria os equipamentos e a fachada e executa a sessão usando
  apenas `watchMovie(...)` e `endMovie()`.

## Saída esperada

```
=== Starting session ===
Lights dimmed
Screen lowered
Projector on
Sound system on
Volume: 20
Playing: Design Patterns: The Movie
=== Ending session ===
Playback stopped
Sound system off
Projector off
Screen raised
Lights on
```

## Tela de projeção motorizada (desafio adicional)

A integração da `ProjectionScreen` exigiu mudanças apenas na fachada: um
novo parâmetro no construtor e duas chamadas a mais na sequência — a tela
é abaixada antes de ligar o projetor e recolhida logo após desligá-lo.
As assinaturas de `watchMovie(String movie)` e `endMovie()` foram
preservadas, então o fluxo da sessão no cliente não mudou; só a montagem
da fachada em `Main` precisou receber o novo equipamento.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Facade?**
O cliente precisava conhecer quatro classes e a ordem exata das chamadas
para iniciar e encerrar uma sessão. Essa sequência tenderia a ser
duplicada em todo lugar que precisasse dela, e qualquer mudança na
preparação da sala (como a nova tela) exigiria alterar todos esses pontos,
com risco de esquecer etapas. Com a fachada, a sequência fica definida em
um único lugar.

**b) Quais classes representam a fachada e os subsistemas na sua implementação?**
A fachada é `HomeTheaterFacade`. Os subsistemas são `Projector`,
`SoundSystem`, `StreamingPlayer`, `RoomLights` e `ProjectionScreen`.

**c) Como a fachada reduz o acoplamento do código cliente com os equipamentos?**
O fluxo do cliente passa a depender só de `HomeTheaterFacade` e de duas
operações de alto nível. Ele não chama mais métodos dos equipamentos nem
precisa saber quais existem ou em que ordem acioná-los. Mudanças na
coordenação (novos equipamentos, nova ordem, outro volume padrão) ficam
restritas à fachada; no máximo a montagem dela precisa ser ajustada.

**d) Qual é a diferença entre coordenar operações na fachada e transferir toda a lógica dos equipamentos para ela?**
Coordenar significa apenas decidir *quais* operações chamar e *em que
ordem*, delegando a execução a cada equipamento — é o que
`HomeTheaterFacade` faz. Transferir a lógica seria a fachada reimplementar
o funcionamento interno dos equipamentos (por exemplo, imprimir ela mesma
"Projector on"), o que a transformaria numa classe inchada, duplicaria
responsabilidades e esvaziaria os subsistemas. Cada equipamento continua
responsável pelo próprio comportamento.

**e) O padrão Facade impede o acesso direto aos subsistemas? Em quais situações esse acesso ainda pode ser necessário?**
Não. A fachada é um atalho para os casos de uso comuns, não uma barreira:
os equipamentos continuam públicos e podem ser usados diretamente. Isso
ainda é necessário em operações que a fachada não cobre ou que exigem
controle mais fino — por exemplo, mudar o volume no meio do filme, acender
só as luzes durante uma pausa, recolher a tela para manutenção ou testar
um equipamento isoladamente.

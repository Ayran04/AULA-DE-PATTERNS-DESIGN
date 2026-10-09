# Atividade 014 — Padrão Proxy

Galeria de imagens refatorada com um Proxy Virtual: cada imagem é
representada por um `ImageProxy` leve, e a `HighResolutionImage` real só
é criada (e carregada do disco) na primeira vez em que é exibida.

## Estrutura

- `Image` — abstração (sujeito) com a operação `display()`.
- `HighResolutionImage` — sujeito real. Implementa `Image`, continua
  carregando a imagem no construtor e exibindo em `display()`.
- `ImageProxy` — Proxy Virtual. Implementa `Image`, guarda o nome do
  arquivo e uma referência inicialmente `null` para `HighResolutionImage`.
  O construtor apenas registra o nome; a primeira chamada de `display()`
  cria a imagem real e as seguintes a reutilizam.
- `ProtectedImageProxy` — Proxy de Proteção (desafio adicional). Recebe uma
  `Image` e um indicador de permissão; delega `display()` somente se o
  acesso for permitido, caso contrário exibe uma mensagem de acesso negado.
- `Main` — monta a galeria como `List<Image>` de proxies e exibe as imagens
  pelo método `show(Image)`, que depende apenas da abstração.

## Saída esperada

```
Gallery created
=== First image (twice) ===
Loading image from disk: forest.jpg
Displaying image: forest.jpg
Displaying image: forest.jpg
=== Second image ===
Loading image from disk: beach.jpg
Displaying image: beach.jpg
=== Third image is never displayed, so it is never loaded ===

=== Protected images ===
User without permission:
Access denied: you do not have permission to view this image
User with permission:
Loading image from disk: vip.jpg
Displaying image: vip.jpg
Displaying image: vip.jpg
```

- Nenhum carregamento antes de `Gallery created`.
- `forest.jpg`: um carregamento e duas exibições.
- `beach.jpg`: carrega apenas a nova imagem.
- `mountain.jpg`: nunca é carregada.
- `secret.jpg`: sem permissão, a chamada não chega ao `ImageProxy`, então a
  imagem real nunca é criada.

## Imagens protegidas (desafio adicional)

`ProtectedImageProxy` envolve qualquer `Image` — aqui, um `ImageProxy`.
Os dois proxies se combinam: o de proteção decide *se* o acesso acontece e
o virtual decide *quando* a imagem real é criada. Como a verificação
ocorre antes da delegação, uma tentativa negada não provoca carregamento.
Para o cliente, tudo continua sendo apenas uma `Image`.

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Proxy Virtual?**
Todas as imagens eram carregadas na criação da galeria, mesmo as que nunca
seriam exibidas, tornando a abertura lenta e desperdiçando memória. Com o
proxy, o carregamento só acontece quando a imagem é de fato exibida, e
essa lógica fica encapsulada no proxy em vez de espalhada pelo cliente.

**b) Quais classes representam o sujeito, o sujeito real e o proxy na sua implementação?**
Sujeito: `Image`. Sujeito real: `HighResolutionImage`. Proxies:
`ImageProxy` (virtual) e `ProtectedImageProxy` (de proteção).

**c) Por que o proxy e o objeto real devem implementar a mesma abstração?**
Para que sejam intercambiáveis: o cliente trabalha com `Image` e não
precisa saber se está falando com a imagem real ou com um intermediário.
Isso permite introduzir (ou remover, ou empilhar) proxies sem alterar o
código cliente e sem verificações de tipo concreto.

**d) Como o carregamento sob demanda altera o comportamento da galeria? Qual custo ainda existe na primeira exibição de uma imagem?**
A galeria passa a ser montada instantaneamente, criando apenas objetos
leves, e só as imagens efetivamente exibidas consomem recursos. O custo
do carregamento não desaparece, apenas é adiado: a primeira exibição de
cada imagem paga o tempo de leitura do disco e a memória da imagem, o que
pode causar um pequeno atraso perceptível nesse momento. As exibições
seguintes do mesmo proxy não têm esse custo.

**e) Embora Proxy e Decorator possam envolver outro objeto com a mesma interface, como suas intenções se diferenciam?**
O Decorator existe para *acrescentar comportamento/responsabilidades* ao
objeto, geralmente empilhando várias camadas que enriquecem o resultado.
O Proxy existe para *controlar o acesso* ao objeto — quando ele é criado
(virtual), quem pode usá-lo (proteção), onde ele está (remoto) — sem mudar
o que a operação faz. Além disso, o proxy costuma gerenciar o ciclo de
vida do objeto real (como o `ImageProxy`, que o cria), enquanto o
decorator normalmente recebe um objeto já pronto.

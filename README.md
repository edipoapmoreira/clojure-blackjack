# Blackjack - Jogo de Cartas

Este é um projeto simples de Blackjack implementado em Clojure. O jogo simula uma partida de Blackjack entre um jogador e o dealer (computador). A principal lógica do jogo está focada na mecânica de cálculo de pontos, decisões de compra de cartas e a interação entre o jogador e o dealer.

## Funcionalidades

- **Criação de Cartas**: O jogo gera cartas aleatórias com valores entre 1 e 13 (Ás, Valete, Dama, Rei).
- **Cálculo de Pontos**: O valor das cartas é calculado considerando que J, Q, K valem 10, e Ás pode valer 1 ou 11.
- **Interação com o Jogador**: O jogador pode decidir se quer mais cartas até que decida parar ou ultrapasse 21 pontos.
- **Decisão do Dealer**: O dealer continua comprando cartas até alcançar ou superar os pontos do jogador, ou até que o jogador ultrapasse 21.
- **Determinação de Vencedor**: O vencedor é determinado com base nas regras do Blackjack: quem tiver mais pontos sem ultrapassar 21 ganha, ou ambos podem perder se ultrapassarem 21.

## Estrutura do Código

O código está organizado dentro do namespace `blackjack.game` e utiliza uma biblioteca para desenhar as cartas em formato ASCII.

### Funções Principais

- **new-card**: Gera uma carta aleatória com valor entre 1 e 13.
- **JQK->10**: Converte J, Q, K para 10.
- **A->11**: Converte Ás de 1 para 11.
- **point-cards**: Calcula a pontuação de uma mão de cartas.
- **player**: Cria um novo jogador com um nome e duas cartas iniciais.
- **more-card**: Adiciona uma nova carta a um jogador e recalcula seus pontos.
- **player-decision-continue?**: Pergunta ao jogador se ele deseja mais cartas.
- **dealer-decision-continue?**: Define a lógica do dealer para continuar comprando cartas.
- **game**: Gerencia o fluxo de ações do jogador e do dealer até o término do jogo.
- **end-game**: Determina o vencedor baseado nas regras do Blackjack.

## Como Executar o Jogo

1. **Pré-requisitos**
    - Tenha o [Clojure](https://clojure.org/guides/getting_started) instalado em seu sistema.
    - Clone este repositório em sua máquina.

2. **Rodar o código**
    - Para iniciar o jogo, execute o arquivo `.clj` com o comando:

   ```bash
   clj blackjack.game.clj

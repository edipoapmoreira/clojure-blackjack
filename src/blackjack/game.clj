; ns blackjack.game: Define o namespace do código como blackjack.game, que organiza o código e é usado para importação de funções em outros arquivos.
; (:require [card-ascii-art.core :as card]): Importa a biblioteca card-ascii-art.core (que é presumivelmente responsável por desenhar as cartas de forma ASCII), atribuindo o alias card para ser utilizado no código.
(ns blackjack.game
  (:require [card-ascii-art.core :as card]))

; A função new-card gera um número aleatório entre 1 e 13. Esses números representam as cartas do jogo (1 = Ás, 11 = Valete, 12 = Dama, 13 = Rei).
; rand-int 13: Gera um número aleatório entre 0 e 12.
; inc: Incrementa o valor retornado por rand-int para garantir que o intervalo seja entre 1 e 13.
(defn new-card []
  "Gera um numero aleatorio entre 1 e 13"
  (inc (rand-int 13)))

; Esta função transforma os valores das cartas J, Q, e K (que são representadas como 11, 12 e 13) em 10.
; Se a carta for maior que 10, ela se torna 10; caso contrário, retorna o valor original.
(defn JQK->10 [card]
  (if (> card 10) 10 card))

; Esta função transforma o valor do Ás (1) em 11, mas não altera as outras cartas.
; Se a carta for um Ás (1), ela se torna 11; caso contrário, retorna o valor original da carta.
(defn A->11 [card]
  (if (= card 1) 11 card))

; A função point-cards calcula os pontos de uma mão de cartas.
; Primeiro, ela converte as cartas J, Q, K em 10, e depois converte o Ás (1) para 11.
; Depois, calcula a soma dos pontos com o Ás valendo 11 (point-with-A-11) e com o Ás valendo 1 (point-with-A-1).
; Se a soma com o Ás valendo 11 for maior que 21, então usa-se a soma com o Ás valendo 1 (para evitar ultrapassar 21).
(defn point-cards [cards]
  (let [cards-without-JQK (map JQK->10 cards)
        cards-with-A11 (map A->11 cards-without-JQK)
        point-with-A-1 (reduce + cards-without-JQK)
        point-with-A-11 (reduce + cards-with-A11)]
    (if (> point-with-A-11 21) point-with-A-1 point-with-A-11)))

; A função player cria um jogador com um nome, duas cartas aleatórias e calcula seus pontos.
; new-card é chamada para gerar as duas cartas.
; point-cards é chamada para calcular os pontos das cartas.
(defn player [player-name]
  (let [card1 (new-card)
        card2 (new-card)
        cards [card1 card2]
        points (point-cards cards)]
    {:player-name player-name
     :cards       cards
     :points      points}))

; Esta função adiciona mais uma carta ao jogador, atualizando suas cartas e recalculando seus pontos.
; conj adiciona a nova carta à lista de cartas do jogador.
; update atualiza o mapa do jogador com a nova carta.
; assoc atualiza os pontos do jogador.
(defn more-card [player]
  (let [card (new-card)
        cards (conj (:cards player) card)
        new-player (update player :cards conj card)
        points (point-cards cards)]
    (assoc new-player :points points)))

; Esta função pergunta ao jogador se ele deseja mais cartas. Se o jogador digitar "sim",
; a função retorna true; caso contrário, retorna false.
(defn player-decision-continue? [player]
  (println (:player-name player) ": mais cartas?")
  (= (read-line) "sim"))

; O dealer segue uma regra simples: ele continua pegando cartas enquanto seus pontos forem menores
; ou iguais aos do jogador, ou até que o jogador ultrapasse 21.
(defn dealer-decision-continue? [player-points dealer]
  (let [dealer-points (:points dealer)]
    (if (> player-points 21) false (<= dealer-points player-points))))

; A função game pergunta se o jogador quer mais cartas e, se a resposta for "sim",
; adiciona mais uma carta ao jogador e imprime a mão do jogador.
; Ela continua chamando a si mesma recursivamente até o jogador decidir parar.
(defn game [player fn-decision-continue?]
  (if (fn-decision-continue? player)
    (let [player-with-more-cards (more-card player)]
      (card/print-player player-with-more-cards)
      (recur player-with-more-cards fn-decision-continue?))
    player))

; Esta função determina o vencedor após o jogo.
; Verifica as condições:
; Se ambos ultrapassaram 21, ambos perdem.
; Se as pontuações forem iguais, é um empate.
; Se o jogador ultrapassou 21, o dealer ganha, e vice-versa.
; Caso contrário, compara as pontuações e determina o vencedor.
(defn end-game [player dealer]
  (let [player-points (:points player)
        dealer-points (:points dealer)
        player-name (:player-name player)
        dealer-name (:player-name dealer)
        message (cond
                  (and (> player-points 21) (> dealer-points 21)) "Ambos perderam"
                  (= player-points dealer-points) "empatou"
                  (> player-points 21) (str dealer-name " ganhou")
                  (> dealer-points 21) (str player-name " ganhou")
                  (> player-points dealer-points) (str player-name " ganhou")
                  (> dealer-points player-points) (str dealer-name " ganhou"))]
    (card/print-player player)
    (card/print-player dealer)
    (print message)))

(def player-1 (player "Edipo"))
(card/print-player player-1)

(def dealer (player "Dealer"))
(card/print-masked-player dealer)

(def player-after-game (game player-1 player-decision-continue?))
(def partial-dealer-decision-continue? (partial dealer-decision-continue? (:points player-after-game)))
(def dealer-after-game (game dealer partial-dealer-decision-continue?))

(end-game player-after-game dealer-after-game)
(ns blackjack.teste)

(def nome "Cesar")
(def idade 30)
(def numeros [1 2 3 5])
(println numeros)

(defn saudação
  "Saudaçao iniciante em clojure"
  [nome]
  (str "Bem vindo " nome))

(println (saudação nome))

(ns advent.day2
  (:require [clojure.string :as str]))

(def match-score {:WIN 6, :DRAW 3, :LOOSE 0})

(def shape-score {:ROCK 1, :PAPER 2, :SCISSORS 3})

(def shape-index {"A" :ROCK
                  "X" :ROCK
                  "B" :PAPER
                  "Y" :PAPER
                  "C" :SCISSORS
                  "Z" :SCISSORS})

(def result-index {"X" :LOOSE
                   "Y" :DRAW
                   "Z" :WIN})

(def shape-counter {:ROCK :PAPER
                    :PAPER :SCISSORS
                    :SCISSORS :ROCK})

(def reverse-counter (clojure.set/map-invert shape-counter))

(def counter-strategy-skeleton [:rival-move :your-move])

(def result-strategy-skeleton [:rival-move :expected-result])

(defn get-raw-strategies! []
  (-> "input-day2"
      slurp
      (str/split #"\n")))

(defn create-strategy
  ([raw-strategy strategy-skeleton letf-index right-index]
   (let [[first-val second-val] (str/split raw-strategy #"\s")
         left (letf-index first-val)
         right (right-index second-val)]
     (zipmap strategy-skeleton [left right]))))

(defn get-strategies
  [raw-strategy-collection strategy-skeleton letf-index right-index]
  (map #(create-strategy % strategy-skeleton letf-index right-index) raw-strategy-collection))

(defn get-match-result
  [your-move rival-move]
  (cond (= your-move rival-move) :DRAW
        (= your-move (shape-counter rival-move)) :WIN
        :else :LOOSE))

(defn get-expected-move
  [rival-move expected-result]
  (cond (= expected-result :DRAW) rival-move
        (= expected-result :LOOSE) (reverse-counter rival-move)
        :else (shape-counter rival-move)))

(defn counter-strategy-score
  [{rival-move :rival-move your-move :your-move}]
  (let [match-result (get-match-result your-move rival-move)]
    (+ (match-score match-result)
       (shape-score your-move))))

(defn result-strategy-score
  [{rival-move :rival-move expected-result :expected-result}]
  (let [your-move (get-expected-move rival-move expected-result)]
    (+ (match-score expected-result) (shape-score your-move))))

(defn get-score
  ([strategy-skeleton score-counter left-index]
   (get-score strategy-skeleton score-counter left-index left-index))
  ([strategy-skeleton score-counter left-index rigth-index]
   (let [strategies (-> (get-raw-strategies!)
                        (get-strategies strategy-skeleton left-index rigth-index))]
     (reduce + (map score-counter strategies)))))

(get-score counter-strategy-skeleton counter-strategy-score shape-index)
(get-score result-strategy-skeleton result-strategy-score shape-index result-index)

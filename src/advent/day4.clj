(ns advent.day4
  (:require [advent.core :as core]
            [clojure.string :as str]))

(defn convert-to-pairs [raw-pairs]
  (let [[raw-pair-a raw-pair-b] (str/split raw-pairs #"\,")
        [pair-a-left pair-a-right] (str/split raw-pair-a #"\-")
        [pair-b-left pair-b-right] (str/split raw-pair-b #"\-")]
    {:pair-a-left (Integer/parseInt pair-a-left)
     :pair-a-right (Integer/parseInt pair-a-right)
     :pair-b-left (Integer/parseInt pair-b-left)
     :pair-b-right (Integer/parseInt pair-b-right)}))

(defn complete-overlaping?
  [{:keys [pair-a-left
           pair-a-right
           pair-b-left
           pair-b-right]}]
  (cond
    (and (>= pair-a-left pair-b-left) (<= pair-a-right pair-b-right)) true
    (and (>= pair-b-left pair-a-left) (<= pair-b-right pair-a-right)) true
    :else false))

(defn between-any?
  [val & body]
  (when (odd? (count body))
    (throw (clojure.lang.ArityException "body number parameter must be even")))
  (let [ranges (partition 2 body)]
    (some (fn [[begin end]] (<= begin val end)) ranges)))

(defn overlapping?
  [{:keys [pair-a-left
           pair-a-right
           pair-b-left
           pair-b-right]}]
  (cond
    (or (between-any? pair-a-left pair-b-left pair-b-right)
        (between-any? pair-a-right pair-b-left pair-b-right)
        (between-any? pair-b-left pair-a-left pair-a-right)
        (between-any? pair-b-right pair-a-left pair-a-right)) true
    :else false))


(defn sum-complete-overlaping-pairs
  [val raw-pair]
  (let [pairs (convert-to-pairs raw-pair)]
    (cond
      (complete-overlaping? pairs) (inc val)
      :else val)))

(defn sum-any-overlapping
  [val raw-pair]
  (let [pairs (convert-to-pairs raw-pair)]
    (cond
      (overlapping? pairs) (inc val)
      :else val)))

;part 1
(->> (core/get-raw-input! "input-day4")
     (reduce sum-complete-overlaping-pairs 0))

;part 2
(->> (core/get-raw-input! "input-day4")
     (reduce sum-any-overlapping 0))
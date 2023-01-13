(ns advent.day3
  (:require [advent.core :as core]
            [clojure.string :as str]))

(def base-items "abcdefghijklmnopqrstuvwxyz")

(def items (str base-items (str/upper-case base-items)))

(def priority (zipmap items (range 1 (+ (count items) 1))))

(defn organize-rucksack
  [raw-items]
  (let [rucksack-elements (count raw-items)
        compartment-size (/ rucksack-elements 2)
        left-compartment (take compartment-size raw-items)
        right-compartment (nthrest raw-items compartment-size)]
    {:left-compartment left-compartment
     :right-compartment right-compartment}))

(defn get-rucksacks [raw-input]
  (map organize-rucksack raw-input))

(defn get-priority-rucksack
  [rucksack]
  (let [common-items (clojure.set/intersection
                      (set (:left-compartment rucksack))
                      (set (:right-compartment rucksack)))]
    (reduce (fn [sum item] (+ sum (* 2 (get priority item)))) 0 common-items)))

(defn rucksacks-total-priority [rucksacks]
  (-> #(map get-priority-rucksack rucksacks)
      #(reduce + %)))

(-> "input-day3"
    core/get-raw-input!
    get-rucksacks
    rucksacks-total-priority)
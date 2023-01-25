(ns advent.day3
  (:require [advent.core :as core]
            [clojure.string :as str]))

(def base-items "abcdefghijklmnopqrstuvwxyz")

(def items (str base-items (str/upper-case base-items)))

(def priority (zipmap items (range 1 (+ (count items) 1))))

(defn split-rucksack-in-compartments
  [rucksack]
  (let [rucksack-elements (count rucksack)
        compartment-size (/ rucksack-elements 2)
        left-compartment (take compartment-size rucksack)
        right-compartment (nthrest rucksack compartment-size)]
    [left-compartment right-compartment]))

(defn rucksacks-priority
  "Returns the estimated priority for the rucksacks provided"
  [& rucksack-chunk]
  (let [common-item (first
                     (apply clojure.set/intersection
                            (map set rucksack-chunk)))]
    (get priority common-item)))

(->> (core/get-raw-input! "input-day3")
     (map split-rucksack-in-compartments)
     (map #(apply rucksacks-priority %))
     (reduce +))

(ns advent.day1
  (:require [clojure.string :as str]))

(defn get-calories! []
  (-> "input"
      slurp
      (clojure.string/split #"\n")))

(defn group-calories [calories, val]
  (let [cal (if (clojure.string/blank? val)
              0
              (Integer/parseInt val))
        last-idx (- (count calories) 1)]
    (if (zero? cal)
      (conj calories cal)
      (update calories last-idx #(+ cal %)))))

(defn get-calories-group-by-elf! []
  (let [raw-calories (get-calories!)]
    (sort (reduce group-calories
                  [(-> raw-calories first Integer/parseInt)]
                  (rest raw-calories)))))

(defn elf-with-more-calories! []
  (last (get-calories-group-by-elf!)))

(defn top-three-elfs-with-more-calories! []
  (reduce + (take-last 3 (get-calories-group-by-elf!))))

(elf-with-more-calories!)
(top-three-elfs-with-more-calories!)

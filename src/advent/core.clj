(ns advent.core)

(defn get-raw-input!
  [file-name]
  (-> file-name slurp (clojure.string/split #"\n")))

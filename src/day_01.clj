(ns day-01
  (:require [clojure.string :as str]
            [babashka.curl :as curl]))

(def test-data "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")

(def data (-> "./src/data/01-day.txt"
              slurp))

;; part 1
(->> data
     str/split-lines
     (map parse-long)
     (reduce (fn [[total counter] x]
               (if x [total (+ x counter)] [(max total counter) 0] )) [0 0])
     first)

;; part 2
(->> data
     str/split-lines
     (map parse-long)
     (reduce (fn [[totals cnt] x]
               (if x [totals (+ x cnt)] [(conj totals cnt) 0])) [[] 0])
     first
     sort
     reverse
     (take 3)
     (apply +))

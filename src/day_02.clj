(ns day-02
  (:require [clojure.string :as str]))

(def test-data "A Y
B X
C Z")

(def data (slurp "./src/data/02-day.txt"))

(def shape {"X" 1 "Y" 2 "Z" 3})
(def winner #{["A" "Y"] ["B" "Z"] ["C" "X"]})
(def draw   #{["A" "X"] ["B" "Y"] ["C" "Z"]})

(defn points [a b]
  (let [x (shape b) y (cond (winner [a b]) 6 (draw [a b]) 3 :else 0)]
    (+ x y)))

;; part 1
(->> data
     str/split-lines
     (map #(str/split % #" "))
     (map #(apply points %))
     (apply +))

(def points-map {
                 "A X" (+ 1 3)
                 "A Y" (+ 2 6)
                 "A Z" (+ 3 0)
                 "B X" (+ 1 0)
                 "B Y" (+ 2 3)
                 "B Z" (+ 3 6)
                 "C X" (+ 1 6)
                 "C Y" (+ 2 0)
                 "C Z" (+ 3 3)})

(->> data
     str/split-lines
     (map points-map)
     (reduce + 0))
;; A Rock | B Paper | C Scissor
(def points-map-2 {
                   "A X" (+ 3 0)
                   "A Y" (+ 1 3)
                   "A Z" (+ 2 6)
                   "B X" (+ 1 0)
                   "B Y" (+ 2 3)
                   "B Z" (+ 3 6)
                   "C X" (+ 2 0)
                   "C Y" (+ 3 3)
                   "C Z" (+ 1 6)})

(->> data
     str/split-lines
     (map points-map-2)
     (reduce + 0))
;; => 10334
;; => (5 1 9)

(ns day-04
  (:require [clojure.string :as str]))

(def data-test "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(def data (slurp "src/data/04-day.txt"))

(defn parse-line [line]
  (rest (re-find #"(\d+)-(\d+),(\d+)-(\d+)" line)))

(defn overlap? [[x y] [a b]]
  (or (<= x a y) (<= x b y)))

(defn subset? [line]
  (let [[x1 x2 y1 y2] (parse-line line)
        [e1 e2]       [[(parse-long x1) (parse-long x2)][(parse-long y1) (parse-long y2)]]
        ]
    (or (overlap? e1 e2) (overlap? e2 e1))))

(->> data
     str/split-lines
     (map subset?)
     (filter true?)
     count
     )
;; => 657
;; => 544
;; => 275

(ns day-03
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def test-data "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

(def data (slurp "src/data/03-day.txt"))

(defn priority [sack]
  (let [half (/ (count sack) 2)
        x    (subs sack 0 half)
        y    (subs sack half)
        z    (set/intersection (set x) (set y))
        c    (int (first z))]
    (if (Character/isLowerCase c)
      (- (+ 1 c)(int \a))
      (- (+ 27 c)(int \A)))
    ))


(->> data
     str/split-lines
     (map priority)
     (reduce + 0))
;; => 7701
;; => (\p \L \P \v \t \s)

(defn priority-2 [grp]
  (let [a (apply set/intersection grp)
        c (int (first a))]
    (if (Character/isLowerCase c)
      (- (+ 1 c)(int \a))
      (- (+ 27 c)(int \A)))))

(->> data
     str/split-lines
     (map set)
     (partition 3)
     (map priority-2)
     (reduce + 0));; => 2644

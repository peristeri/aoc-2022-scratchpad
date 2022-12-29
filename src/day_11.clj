(ns day-11
  (:require [clojure.string :as str]))

(def data (slurp "src/data/11-day.txt"))

(def test-data "Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1")

(defn get-numbers [i] (map (fn [x] (parse-long x)) (re-seq #"\d+" i)))
(defn get-number [i] (first (get-numbers i)))
(defn maybe-long [v] (when-not (= v "old") (parse-long v)))


(defn parse-monkey [input]
  (let [[l1 l2 l3 l4 l5 l6] (str/split-lines input)]
    {:monkey  (get-number l1)
     :items   (get-numbers l2)
     :divisor (get-number l4)
     :true    (get-number l5)
     :false   (get-number l6)
     :op      (let [[lhs op rhs] (drop 3 (str/split l3 #"\s+"))
                    fn'          (resolve (symbol op))
                    [rhs' lhs']  (map maybe-long [rhs lhs])]
                (fn [arg] (fn' (if lhs' lhs' arg) (if rhs' rhs' arg))))}))



(->> (str/split test-data #"\n\n")
     (map parse-monkey))

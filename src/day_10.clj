(ns day-10
  (:require [clojure.string :as str]))

(def data (slurp "src/data/10-day.txt"))

(defn parse [input]
  (->> input
       str/split-lines
       (map (fn [x] (let [[ops step] (str/split x #"\s+")] [(keyword ops) (when step (parse-long step))])))))

(defn cpu-step [[command :as ops]]
  (let [padding (if (= :addx command) [[:noop]] [])]
    (conj padding ops)))

(defn step-val [data]
  (->> data
       parse
       (mapcat cpu-step)
       (reductions (fn [acc [_ val]] (+ acc (or val 0))) 1)
       vec))

(let [steps (step-val data)]
  (->> (for [time (range 20 (inc 220) 40)]
         (* time (get steps (dec time))))
       (reduce +)))


(let [steps (step-val data)]
  (->> (for [y (range 6)]
         (for [x (range 40)]
           (let [time     (+ x (* 40 y))
                 sprite   (get steps time)
                 overlap? (<= -1 (- x sprite) 1)]
             (if overlap? "#" "."))))
       (map str/join)
       (map println)))

(ns day-08
  (:require [clojure.string :as str]))

(def test-data "30373
25512
65332
33549
35390")

(def data (slurp "src/data/08-day.txt"))

(defn parse [data] (map (comp parse-long str) data))

(defn build-data
  [data]
  (let [parsed (mapv parse data)]
    {:h parsed
     :v (apply mapv vector parsed)}))

(defn check-visibility
  [f agr {:keys [h v]}]
  (for [row-id (range (count h))
        col-id (range (count v))
        :let [[r1 [t1 & r2]] (split-at col-id (h row-id))
              [c1 [t2 & c2]] (split-at row-id (v col-id))
              res (agr (f t1 (reverse r1))
                       (f t1 r2)
                       (f t2 (reverse c1))
                       (f t2 c2))]
        :when (pos? res)]
    res))

(defn visible? [f line] (if (every? (partial > f) line) 1 0))

(count 
(check-visibility visible? + (-> data str/split-lines build-data)))
;; => 1538
;; => (2 1 1 3 2 1 2 2 1 4 1 1 1 1 2 4 2 2 1 4 2)

(defn visible-trees [f line] (let [[a b] (split-with (partial > f) line)]
                               (+ (count a) (count (take 1 b)))))

(->> data
    str/split-lines
    build-data
    (check-visibility visible-trees *)
    (reduce max));; => 496125

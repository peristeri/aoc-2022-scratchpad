(ns day-05
  (:require [clojure.string :as str]))

(def data-test "    [D]    \n[N] [C]    \n[Z] [M] [P]\n 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(def data (slurp "src/data/05-day.txt"))

(defn parse-line [line]
  (->> line
       (partition-all 4)
       (map #(->> % (apply str)str/trim))))

(defn parse-towers [towers]
  (let [indexes (->> towers last rest (take-nth 4))
        cols    (->> towers
                     butlast
                     (apply map list)
                     (map #(remove #{\space} %))
                     rest
                     (take-nth 4))]
    (zipmap indexes cols))
  )

(defn parse-step [n from to] (list (parse-long n) (first from) (first to)))

(defn parse-steps [steps]
  (map (fn [x] (->> x
                   (re-find #"\D+(\d+)\D+(\d+)\D+(\d+)")
                   (drop 1)
                   (apply parse-step)
                   )) steps))

(defn move-1 [towers [n from to]]
  (loop [source (towers from)
         target (towers to)
         n      (dec n)]
    (if (pos? n)
      (recur (butlast source)
             (cons target (last source))
             (dec n))
      (assoc towers from source to target))))

(defn move-step [reverse? stacks [cnt from to]]
  (let [[top rst] (split-at cnt (stacks from))]
    (assoc stacks
           to (reduce conj (stacks to) (if reverse? (reverse top) top))
           from rst)))

(defn parse-input [input]
  (let [[stacks commands] (str/split input #"\n\n")
        towers            (parse-towers (str/split-lines stacks))
        steps             (parse-steps (str/split-lines commands))
        after             (reduce (partial move-step true) towers steps)]
    (apply str (map #(first (second %)) after))
    ))

(parse-input data)
;; => "RNRGDNFQG"
;; => "FWNSHLDNZ"
;; => "123"

(ns day-09
  [:require [clojure.string :as str]])

(def test-data "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2")

(def data (slurp "src/data/09-day.txt"))

(defn parse [data]
  (->> data
       str/split-lines
       (map (fn [line] (let [[k v] (str/split line #"\s+")][(keyword k) (parse-long v)])))))

(defn signum [x] (long (Math/signum (double x))))

(defn follow [head {:keys [x y] :as tail}]
  (let [dx (- (:x head) x)
        dy (- (:y head) y)
        touching? (and (<= (abs dx) 1)
                       (<= (abs dy) 1))]
    (if touching?
      tail
      {:x (+ x (signum dx))
       :y (+ y (signum dy))})))

(defn move-head [knot direction]
  (case direction
    :L (update knot :x dec)
    :R (update knot :x inc)
    :U (update knot :y inc)
    :D (update knot :y dec)))

(defn move-rope [[head & rst] direction]
  (reduce (fn [rope tail]
            (conj rope (follow (peek rope) tail)))
          [(move-head head direction)] rst))

(defn tail-tracking [input]
  (let [rope (take 10 (repeat {:x 0 :y 0}))]
    (->> (parse input)
         (mapcat (fn [[direction steps]]
                   (repeat steps direction)))
         (reductions move-rope rope)
         (map last)
         distinct
         count)))

(tail-tracking data)
;; => 2352
;; => 5981


;; part 1

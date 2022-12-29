(ns day-06)

(def data (slurp "src/data/06-day.txt"))

(->> (partition 4 1 data)
     (map (comp count set))
     (map-indexed vector)
     (sort-by second >)
     (first)
     (reduce +))


(->> (partition 14 1 data)
     (map (comp count set))
     (map-indexed vector)
     (sort-by second >)
     (first)
     (reduce +))

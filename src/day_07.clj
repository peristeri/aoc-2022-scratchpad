(ns day-07
  (:require [clojure.string :as str]))

(def test-data "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(def data (slurp "src/data/07-day.txt"))

(defn split-line
  ([line] (split-line line #"\s+"))
  ([line re] (str/split (str/trim line) re)))

(defn get-size
  [files]
  (->> files
       (map (comp first split-line))
       (remove #{"dir"})
       (map parse-long)
       (reduce +)))

(defn build-tree
  ([listing] (build-tree {} [] (rest listing)))
  ([tree path [line & rst]]
   (if line
     (if (str/starts-with? line "$ ls")
       (let [[listing rst] (split-with #(not (str/starts-with? % "$")) rst)]
         (recur (assoc-in tree (conj path :files) (get-size listing)) path rst ))
       (let [f (last (split-line line))]
         (if (= ".." f)
           (recur tree (subvec path 0 (dec (count path))) rst)
           (recur tree (conj path f) rst))))
     tree)))

(defn sizes
  [tree]
  (conj (->> (vals tree)
             (filter map?)
             (mapcat sizes))
        (->> (tree-seq map? vals tree)
             (remove map?)
             (reduce +))))

(->> (str/split-lines data) build-tree sizes (filter #(< % 100000)) (reduce +))
;; => 1243729
;; => {:files 23352670, "a" {:files 94269, "e" {:files 584}}, "d" {:files 24933642}}


(defn find-smallest-dir
  [data]
  (let [ts (- (first data) 40000000)]
    (reduce min (filter #(> % ts) data))))

(->> (str/split-lines data) build-tree sizes find-smallest-dir);; => 4443914

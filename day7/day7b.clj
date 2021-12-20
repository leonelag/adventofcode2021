(defn parse-ints [s]
  (map #(Integer/parseInt %)
       s))

(defn read-input [filename]
  (-> filename
      slurp
      .strip
      (.split ",")
      parse-ints))

(defn arithmetic-series [n]
  (/ (* n (inc n))
     2))

(defn crab-fuel-cost [pos crab]
  (->> (- crab pos)
       Math/abs
       arithmetic-series))

(defn total-fuel-cost [crabs pos]
  (reduce + 0
          (map (partial crab-fuel-cost pos)
               crabs)))

(defn optimal-pos [crabs]
  (let [_max (apply max crabs)
        _min (apply min crabs)]
    (apply min
           (map (partial total-fuel-cost crabs)
                (range _min (inc _max))))))

(-> "./input.txt"
    read-input
    optimal-pos
    println)

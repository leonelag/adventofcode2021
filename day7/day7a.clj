(defn parse-ints [s]
  (map #(Integer/parseInt %)
       s))

(defn read-input [filename]
  (-> filename
      slurp
      .strip
      (.split ",")
      parse-ints))

(defn fuel-cost [crabs pos]
  (->> (for [c crabs]
         (Math/abs (- c pos)))
       (reduce + 0)))

(defn optimal-pos [crabs]
  (let [_max (apply max crabs)
        _min (apply min crabs)]
    (apply min
           (map (partial fuel-cost crabs)
                (range _min (inc _max))))))

(println (optimal-pos (read-input "./input.txt")))


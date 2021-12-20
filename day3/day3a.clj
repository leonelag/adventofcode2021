(defn str->nums [s]
  (vec
   (map (fn [ch]
          (-> ch
              (str)
              (Integer/parseInt)))
        s)))

(defn read-input []
  (let [lines (-> (slurp "./input.txt")
                  (.split "\n")
                  (vec))]
    (map str->nums lines)))

(def input (read-input))

(defn transpose [matrix]
  (apply map vector matrix))

(defn bit-frequency-by-col [matrix most-or-least?]
  (let [cmp (case most-or-least?
              :most >
              :least <)
        freqs (map frequencies (transpose matrix))]
    (for [f freqs]
      (let [zeros-count (f 0)
            ones-count  (f 1)]
        (if (cmp zeros-count ones-count)
          0
          1)))))

(defn vec->int [v]
  (reduce (fn [acc val]
            (+ val (* 2 acc)))
          0
          v))

(defn gamma [matrix]
  (-> (bit-frequency-by-col matrix :most)
      (vec->int)))

(defn epsilon [matrix]
  (-> (bit-frequency-by-col matrix :least)
      (vec->int)))




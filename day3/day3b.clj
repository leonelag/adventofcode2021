(defn str->nums [s]
  (vec
   (map (fn [ch]
          (-> ch
              (str)
              (Integer/parseInt)))
        s)))

(defn read-input [filename]
  (let [lines (-> (slurp filename)
                  (.split "\n"))]
    (->> lines
         (map (comp str->nums
                    #(.strip %)))
         (vec))))

(defn vec->int [v]
  (reduce (fn [acc val]
            (+ val (* 2 acc)))
          0
          v))

(defn matrix-col [matrix col-idx]
  (map (fn [row]
         (get row col-idx))
       matrix))

(defn most-common-bit [matrix col-idx]
  (let [freqs (->> (matrix-col matrix col-idx)
                   (frequencies))
        zeros-count (get freqs 0)
        ones-count (get freqs 1)]
    (if (> zeros-count ones-count)
      0
      1)))

(defn least-common-bit [matrix col-idx]
  (->> (matrix-col matrix col-idx)
       (frequencies)
       ;; Find element in map with highest val
       ;; see: https://clojuredocs.org/clojure.core/max-key#example-5490032de4b09260f767ca79
       (apply min-key val)
       (key)))

(def input (read-input "./input.txt"))
(def input1 (read-input "./input1.txt"))

;; ---------------------------------------------------------------------------


(defn step [[matrix col-idx]]
  [(let [mcb (most-common-bit matrix col-idx)]
     (filter (fn [row]
               (= mcb (get row col-idx)))
             matrix))
   (inc col-idx)])

(let [step (fn [[matrix col-idx]]
             [(let [mcb (most-common-bit matrix col-idx)]
                (filter (fn [row]
                          (= mcb (get row col-idx)))
                        matrix))
              (inc col-idx)])]
     step)

(defn more-than-one-elem [[x & xs]]
  ;; destructure input, if xs is non-nil, the input has more than one elem
  (boolean xs))

(defn search [matrix bit-criteria]
  (let [s (->> (iterate step [matrix
                              0])
               (drop-while (fn [[matrix _]]
                             (more-than-one-elem matrix))))]
    (when (empty? s)
      (throw (IllegalStateException. "Rating not found")))
    (let [[ratings _] (first s)]
      ratings)))

(defn ogr [matrix]
  (search matrix :most))

(defn co2 [matrix]
  (search matrix :least))

(ogr input1)
(co2 input1)





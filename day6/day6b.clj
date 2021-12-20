(defn read-input [filename]
  (let [pieces (-> filename
                   slurp
                   .trim
                   (.split ",")
                   seq)]
    (->> pieces
         (map #(Integer/parseInt %))
         vec)))

(defn get-frequencies [input]
  (->> input
       frequencies
       ))

(defn step [freqs]
  (let [decs (map (fn [[t cnt]]
                    (if (> t 0)
                      [(dec t) cnt]
                      [6 cnt]))
                  freqs)
        f +
        decs (reduce (fn [acc [k new-val]]
                  (if-let [old-val (get acc k)]
                    (assoc acc k (f old-val new-val))
                    (assoc acc k new-val)))
                {}
                decs)]
    (if-let [cnt (get freqs 0)]
      (assoc decs 8 cnt)
      decs
      )))


(let [input (-> "input_a.txt"
                read-input
                )]
  (->> (get-frequencies input)
       (iterate step)
       (take 50)))

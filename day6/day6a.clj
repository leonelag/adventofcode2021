(defn read-input [filename]
  (let [pieces (-> filename
                   slurp
                   .trim
                   (.split ",")
                   seq)]
    (->> pieces
         (map #(Integer/parseInt %))
         vec)))

(defn step [timers]
  (let [decs (map (fn [t]
                    (if (> t 0)
                      (dec t)
                      6))
                  timers)
        count-zeros (count (filter zero? timers))]
    (concat decs (repeat count-zeros 8))))

(nth (map (fn [i s]
            [i (count s)])
          (iterate inc 0)
          (iterate step (read-input "input_a.txt")))
     80)



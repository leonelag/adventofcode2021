(defn parse-ints [s]
  (map #(Integer/parseInt %) 
       s))

(defn read-ints [lines]
  (mapcat (fn [line]
            (->> (.split line " ")
                 vec
                 (filter (complement empty?))
                 parse-ints))
          lines))

(defn make-board [id nums]
  {:nums (map vec (partition 5 nums))     ;; 5x5 grid of nums
   :marked #{}
   :id id
   })

(defn read-boards [lines]
  (->> (read-ints lines)
       (partition 25)
       (map make-board (iterate inc 1))))

(defn read-input [filename]
  (let [lines (-> filename
                  slurp
                  (.split "\n")
                  vec)
        [first-line rest-lines] (split-at 1 lines)]
    ;; First line is comma-separated list of drawn numbers
    ;; Remaining lines are the boards
    {:draws (-> first-line
                first
                (.split ",")
                vec
                parse-ints)

     :boards (read-boards rest-lines)
     }))

(defn has-num? [board draw]
  (some true?
        (for [row (:nums board)]
          (some #(= draw %) row))))

(defn play-draw [board draw]
  (if (has-num? board draw)
    (-> board
        (update-in [:marked] #(conj % draw)))
    board))

(defn transpose [m]
  (apply mapv vector m))

(defn bingo? [board]
  (let [marked (:marked board)]
    (or
     (some
      (fn [row]
        (when (every? marked row)
          [:row row]))
      (:nums board))

     (some
      (fn [col]
        (when (every? marked col)
          [:col col]))
      (transpose (:nums board))))))

(defn calc [bingoed-board bingo-draw]
  (let [marked (:marked bingoed-board)
        unmarked (for [row (:nums bingoed-board)
                       num row
                       :when (not (marked num))]
                   num)]
    (* (apply + unmarked)
       bingo-draw)))

(defn last-bingoed-board [filename]
  (loop [game (read-input filename)
         bingoed-boards []
         ]
    (let [{[draw & draws] :draws
           boards         :boards} game

          played-boards (map (fn [board]
                               (play-draw board draw))
                             boards)

          {new-bingoed-boards true
           non-bingoed-boards false} (group-by (comp boolean bingo?) played-boards)]

      (println draw new-bingoed-boards)
      
      (if (empty? non-bingoed-boards)
        [draw (first new-bingoed-boards)]
        (recur {:draws draws
                :boards non-bingoed-boards}
               (concat new-bingoed-boards bingoed-boards)
               )
        ))))


(last-bingoed-board "input_a.txt")



(let [[draw lbb] (last-bingoed-board "input.txt")
      unmarked (filter (complement (:marked lbb))
                       (flatten (:nums lbb)))]
  (calc lbb draw))

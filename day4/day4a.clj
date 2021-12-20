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

(defn make-board [nums]
  {:nums (map vec (partition 5 nums))     ;; 5x5 grid of nums
   :marked #{}
   })

(defn read-boards [lines]
  (->> (read-ints lines)
       (partition 25)
       (map make-board)))

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
    (update-in board [:marked] #(conj % draw))
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

(defn run-bingo [game]
  (let [{[draw & draws] :draws
         boards :boards} game
        
        played-boards (map (fn [board]
                             (play-draw board draw))
                           boards)]

    (if-let [bingo-boards (seq (filter bingo? played-boards))]
      [bingo-boards draw]
      (recur {:draws draws
              :boards played-boards}))))

(defn calc [bingoed-board bingo-draw]
  (let [marked (:marked bingoed-board)
        unmarked (for [row (:nums bingoed-board)
                       num row
                       :when (not (marked num))]
                   num)]
    (* (apply + unmarked)
       bingo-draw)))

;; ---------------------------------------------------------------------------

(let [[b bingo-draw] (run-bingo (read-input "./input.txt"))
      board (first b)]
  (calc board bingo-draw))

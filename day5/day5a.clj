(require '[clojure.java.io :as io])

(defn parse-int [s]
  (Integer/parseInt s))

(defn read-input [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (doall
     (for [line (line-seq rdr)]
       (let [pieces (.split line " ")
             [x1 y1] (map parse-int (.split (first pieces) ","))

             [x2 y2] (map parse-int (.split (last pieces) ","))]
         (if (< x1 x2)
           [x1 y1 x2 y2]
           [x2 y2 x1 y1]))))))

(defn vert? [[x1 _y1 x2 _y2]]
  (= x1 x2))

(defn horiz? [[_x1 y1 _x2 y2]]
  (= y1 y2))

(defn vert-horz-lines [lines]
  (filter (fn [line]
            (or (vert? line)
                (horiz? line)))
          lines))

(defn walk-vert-line [[x1 y1 _x2 y2]]
  ;; expect x1 == x2
  (for [y (range (min y1 y2)
                 (inc (max y1 y2)))]
    [x1 y]))

(defn walk-horiz-line [[x1 y1 x2 _y2]]
  ;; expect y1 == y2
  (for [x (range (min x1 x2)
                 (inc (max x1 x2)))]
    [x y1]))

(defn walk-line [line]
  (cond (vert? line) (walk-vert-line line)
        (horiz? line) (walk-horiz-line line)
        :else []))

(defn count-overlapping-lines [filename]
  (->> filename
       read-input
       vert-horz-lines
       (mapcat walk-line)
       frequencies
       (filter (fn [[pt cnt]]
                 (> cnt 1)))
       count))

(count-overlapping-lines "input.txt")

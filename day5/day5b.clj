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
         (if (or (< x1 x2)
                 (and (= x1 x2) (< y1 y2)))
           [x1 y1 x2 y2]
           [x2 y2 x1 y1]           
           ))))))

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

(count-overlapping-lines "input_a.txt")

;; ---------------------------------------------------------------------------

(defn take-until
  "Returns a lazy sequence of successive items from coll until
   (pred item) returns true, including that item. pred must be
   free of side-effects."
  [pred coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (if (pred (first s))
        (cons (first s) nil)
        (cons (first s) (take-until pred (rest s)))))))

(defn walk-line [line]
  (let [[x1 y1 x2 y2] line
        dx (cond (< x1 x2)  1 ; :E
                 (= x1 x2)  0 ; :_
                 (> x1 x2) -1 ; :W
                 :else     (throw (ex-info (str "Invalid line, backwards" line)
                                           {})))
        dy (cond (< y1 y2)  1 ; :N
                 (= y1 y2)  0 ; :_
                 (> y1 y2) -1 ; :S
                 :else     (throw (ex-info (str "Invalid line, down" line)
                                           {})))
        step (fn [[x y]]
               [(+ x dx) (+ y dy)])]
    (->> (iterate step [x1 y1])
         (take-until (fn [[x y]]
                       (and (= x x2)
                            (= y y2)))))))

(defn count-overlapping-lines [filename]
  (->> filename
       read-input
       (mapcat walk-line)
       frequencies
       (filter (fn [[pt cnt]]
                 (> cnt 1)))
       count))

(count-overlapping-lines "input.txt")

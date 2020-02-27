(ns main)

(defn keep-odd
  "Returns a seq of odd numbers in input seq."
  [input-seq]
  nil)

(comment
  (assert (= [1 3 5 7 9] (keep-odd (range 10))))
  (assert (= [357 949] (keep-odd [357 true nil "947" 949]))))

(defn pairwise-sum
  "Sums integers that are in indices 2n and 2n+1 of given input seq.
   Returns a seq of the resulting integers.
   If the length of input seq is odd, the last input element is returned as-is."
  [input-seq]
  nil)

(comment
  (assert (= [1 5 9 13] (pairwise-sum (range 8))))
  (assert (= [1 3 4 7 42] (pairwise-sum [-1 2 0 3 2 2 -7 14 42]))))

(defn my-map
  "Implements clojure.core/map using reduce.
   Supports a single coll instead of variable number of arguments."
  [map-fn coll]
  nil)

(comment
  (assert (= [1 2 3 4 5] (my-map inc (range 5))))
  (assert (= ["this" "is" "good"] (my-map #(apply str %) '(["t" "h" "i" "s"] ["i" "s"] ["g" "o" "o" "d"])))))

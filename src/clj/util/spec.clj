(ns util.spec
  (:require [clojure.spec.alpha :as s]))

(defn spec->item
  ([spec]
   (spec->item spec nil))
  ([spec overrides]
   (-> (s/exercise spec 1)
       (ffirst)
       (merge overrides))))

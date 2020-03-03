(ns events.spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::id integer?)

(s/def ::longitude (s/double-in :min -180.0 :max 180.0))
(s/def ::latitude (s/double-in :min -90.0 :max 90.0))

(s/def ::title string?)
(s/def ::description string?)
(s/def ::location (s/keys :req-un [::longitude ::latitude]))

(s/def ::event (s/keys :req-un [::title ::description]
                       :opt-un [::location]))

(s/def ::events (s/coll-of ::event))


(comment
  (s/exercise ::event 10)
  (s/exercise ::events 5))

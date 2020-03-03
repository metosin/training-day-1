(ns routing
  (:require [reitit.frontend.controllers :as rfc]
            [re-frame.core :as re]))

(re/reg-event-db
  :routing/navigate
  (fn [db [_ new-match]]
    (let [old-match (get db :routing/match)
          controllers (rfc/apply-controllers (:controllers old-match) new-match)]
      (assoc db :routing/match (assoc new-match :controllers controllers)))))

(re/reg-sub :routing/match
  (fn [db _]
    (:routing/match db)))

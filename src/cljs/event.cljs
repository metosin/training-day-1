(ns event
  (:require [re-frame.core :as re]
            [ajax.core :refer [GET]]))

(re/reg-event-fx
  :events/fetch-all
  (fn [ctx [event-name]]
    (let [{:keys [db]} ctx]
      {:db (assoc db :events [])
       :http :fetch-all})))

(re/reg-fx
  :http
  (fn [_]
    (GET
      "http://localhost:8080/api/event"
      {:handler #(re/dispatch [:events/set-events %])
       :format :transit
       :response-format :transit})))

(re/reg-event-db
  :events/set-events
  (fn [db [_ events]]
    (assoc db :events events)))

(re/reg-sub
  :events/events
  (fn [db _]
    (:events db)))

(re/reg-event-db
  :events/sort
  (fn [db [_ key]]
    (update
      db
      :events
      #(sort-by key %))))

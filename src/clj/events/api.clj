(ns events.api
  (:require [ring.util.http-response :refer [ok unprocessable-entity]]
            [events.db :as db]
            [events.spec :as spec]
            [clojure.spec.alpha :as s]))

(defn list-events
  [db _]
  (-> (db/list-events db)
      (ok)))

(defn create-event
  [db {{:keys [body]} :parameters}]
  (-> (db/create-event db body)
      (ok)))

(defn read-event
  [db {{:keys [path]} :parameters}]
  (if-let [event (db/read-event db (:id path))]
    (ok event)
    (unprocessable-entity)))

(defn delete-event
  [db {{{:keys [id]} :path} :parameters}]
  (if (db/delete-event db id)
    (ok)
    (unprocessable-entity)))

(defn get-routes
  [{:keys [db]}]
  [["" {:get {:responses {200 {:body ::spec/events}}
              :handler (partial list-events db)}
        :post {:parameters {:body ::spec/event}
               :responses {200 {:body ::spec/event}}
               :handler (partial create-event db)}}]
   ["/:id" {:get {:parameters {:path {:id integer?}}
                  :responses {200 {:body ::spec/event}}
                  :handler (partial read-event db)}
            :delete {:parameters {:path {:id integer?}}
                     :handler (partial delete-event db)}}]])

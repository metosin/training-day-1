(ns events.db
  (:require [honeysql.core :as hsql]
            [honeysql.helpers :as sql]
            [honeysql-postgres.helpers :as psqlh]
            [clojure.java.jdbc :as jdbc]))

(defn event->row
  [{{:keys [latitude longitude]} :location :as event}]
  (if-not (and latitude longitude)
    event
    (-> event
        (assoc :latitude latitude)
        (assoc :longitude longitude)
        (dissoc :location))))

(defn row->event
  [{:keys [latitude longitude] :as event}]
  (if-not (and latitude longitude)
    event
    (-> event
        (assoc :location {:latitude latitude
                          :longitude longitude})
        (dissoc :latitude)
        (dissoc :longitude))))

(def list-events-q
  (-> (sql/select :*)
      (sql/from :events)))

(defn list-events
  [db]
  (as-> list-events-q $
        (hsql/format $)
        (jdbc/query db $ {:row-fn row->event})))

(defn create-event-q
  [event]
  (-> (sql/insert-into :events)
      (sql/values [event])
      (psqlh/returning :*)))

(defn create-event
  [db event]
  (as-> (event->row event) $
        (create-event-q $)
        (hsql/format $)
        (jdbc/query db $ {:row-fn row->event})
        (first $)))

(defn read-event-q
  [id]
  (-> list-events-q
      (sql/where [:= :id id])))

(defn read-event
  [db id]
  (let [query! (partial jdbc/query db)]
    (-> (read-event-q id)
        (hsql/format)
        (query! {:row-fn row->event})
        (first))))

(defn delete-event-q
  [id]
  (-> (sql/delete-from :events)
      (sql/where [:= :id id])))

(defn delete-event
  [db id]
  (let [res (->> (delete-event-q id)
                 (hsql/format)
                 (jdbc/execute! db)
                 (first))]
    (< 0 res)))

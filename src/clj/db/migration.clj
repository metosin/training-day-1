(ns db.migration
  (:require [integrant.core :as ig]
            [clojure.tools.logging :as log]
            [clojure.string :as str])
  (:import (org.flywaydb.core Flyway)
           (org.flywaydb.core.api FlywayException)))

(defn- get-creds
  [uri]
  (let [username (->> uri (re-find #"user=([^&]+)") (second))
        password (->> uri (re-find #"password=([^&]+)") (second))
        ssl (->> uri (re-find #"sslmode=([^&]+)"))
        uri (-> uri (str/split #"\?") (first))]
    {:username username
     :password password
     :uri (if-not ssl uri (str uri "?" (first ssl)))}))

(defn- mk-flyway
  [{:keys [db-spec migrations]}]
  (let [db-creds (-> db-spec :uri get-creds)]
    (-> (Flyway/configure)
        (.dataSource (:uri db-creds) (:username db-creds) (:password db-creds))
        (.schemas (into-array String [(:schema migrations)]))
        (.locations (into-array String (:files migrations)))
        (.load))))

(defn- migrate!
  [config]
  (try
    (let [flyway (mk-flyway config)]
      (.migrate flyway))
    (catch FlywayException e
      (log/error e "Exception while running migrations")
      (throw e))))

(defmethod ig/init-key :component/flyway
  [_ config]
  (migrate! config))

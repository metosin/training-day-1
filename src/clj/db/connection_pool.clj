(ns db.connection-pool
  (:require [hikari-cp.core :as hikari]
            [integrant.core :as ig]
            [clojure.string :as str])
  (:import (org.flywaydb.core.api FlywayException)))

(defn- make-datasource [jdbc-url]
  (hikari/make-datasource
    {:pool-name "db-pool"
     :jdbc-url jdbc-url
     :connection-timeout 5000
     :validation-timeout 1000
     :driver-class-name "org.postgresql.Driver"
     :tcp-keep-alive true
     :leak-detection-threshold 30000
     :socket-timeout 240}))

(defmethod ig/init-key :component/hikaricp
  [_ {:keys [db-spec]}]
  (let [{:keys [uri]} db-spec
        ds (make-datasource uri)]
    (try
      (catch FlywayException e
        (hikari/close-datasource ds)
        (throw e)))
    {:datasource ds
     :entities #(str/replace % #"-" "_")
     :identifiers #(str/replace (str/lower-case %) #"_" "-")}))

(defmethod ig/halt-key! :component/hikaricp
  [_ conn]
  (hikari/close-datasource (:datasource conn)))

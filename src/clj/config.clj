(ns config
  (:require [aero.core :refer [read-config] :as aero]
            [integrant.core :as ig]
            [environ.core :refer [env]]
            [clojure.string :as str]))

(def ^:private config-file "config.edn")

(defmethod aero/reader 'ig
  [_ _ value]
  (ig/ref value))

(defn current-profile []
  (some-> (env :profile) (str/lower-case) (keyword)))

(defn load-config
  ([]
   (load-config (current-profile)))
  ([profile]
   (-> (read-config config-file {:profile profile})
       :system)))

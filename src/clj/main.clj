(ns main
  (:gen-class)
  (:require [integrant.core :as i-c]
            [clojure.tools.logging :as log]
            [config]
            [system]))

(defonce system nil)

(defn reset-system
  [prev-system]
  (when prev-system
    (i-c/halt! prev-system))
  (i-c/init (config/load-config :prod)))

(defn -main [& _]
  (try
    (alter-var-root #'system reset-system)
    (catch Throwable t
      (log/fatal t "Failed to start system"))))

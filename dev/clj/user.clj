(ns user
  (:require [integrant.repl :refer [clear go halt prep init reset reset-all] :as i-repl]
            [config]
            [system]))

(i-repl/set-prep! #(config/load-config :dev))

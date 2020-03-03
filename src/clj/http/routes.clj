(ns http.routes
  (:require [events.api :as events]))

(defn get-routes
  [system]
  ["/event" (events/get-routes system)])

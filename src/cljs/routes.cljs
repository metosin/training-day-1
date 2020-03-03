(ns routes
  (:require [reitit.frontend :as rf]))

(def +routes+
  (rf/router
    ["/"
     ["" {:name :index
          :view (constantly [:h2 "Index page"])}]
     ["foo" {:name :sub-page
             :view (constantly [:h2 "Sub page"])}]]))

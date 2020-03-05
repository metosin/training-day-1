(ns routes
  (:require [reitit.frontend :as rf]
            [re-frame.core :refer [dispatch]]))

(def +routes+
  (rf/router
    ["/"
     ["" {:name :index
          :view (constantly [:h2 "Index page"])
          :controllers [{:start #(dispatch [:events/fetch-all])}]}]

     ["foo" {:name :sub-page
             :view (constantly [:h2 "Sub page"])}]
     ["nope" {:name :nope}]]))

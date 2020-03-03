(ns main
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [reitit.frontend.easy :as rfe]
            [routing]
            [routes :refer [+routes+]]))

(defn current-route
  []
  (when-let [route-match @(re-frame/subscribe [::routing/match])]
    (if-let [view-fn (get-in route-match [:data :view])]
      [view-fn (:params view-fn)]
      [:p "No view function defined for route."])))

(defn main-view
  []
  [:div
   [:h1 "Iä iä! Cthulhu fhtang!"]
   [current-route]])

(defn ^:export init
  []
  (rfe/start! +routes+
              #(re-frame/dispatch [::routing/navigate %])
              {:use-fragment false})
  (reagent/render [main-view] (.getElementById js/document "app")))

(init)

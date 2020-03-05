(ns main
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [reitit.frontend.easy :as rfe]
            [routing]
            [routes :refer [+routes+]]
            [event]))

(defn current-route
  []
  (when-let [route-match @(re-frame/subscribe [::routing/match])]
    (if-let [view-fn (get-in route-match [:data :view])]
      [view-fn]
      [:p "No view function defined for route."])))

(defn click-me
  []
  (let [counter (reagent/atom 0)]
    (fn render []
      [:div
       [:p
        "You have clicked "
        @counter
        " times"]
       [:button {:on-click #(swap! counter inc)} "Click me!"]])))

(def events [{:title "Joogaa" :description "Jooga on kivaa"}
             {:title "ClojuTRE" :description "October 4-5, 2020"}
             {:title "YLE-koulutus" :description "Tänään"}])

(defn event-table
  []
  (let [data (re-frame/subscribe [:events/events])]
    (fn render []
       [:table.table
        [:thead
         [:tr
          [:th
           {:on-click
            #(re-frame/dispatch [:events/sort :title])}
           "Title"]
          [:th
           {:on-click
            #(re-frame/dispatch [:events/sort :description])}
           "Description"]]]

        [:tbody
         (for [event @data]
           [:tr
            [:td (:title event)]
            [:td (:description event)]])]])))

(defn main-view
  []
  [:div {:class :container}
   [current-route]
   [click-me]
   [event-table]])



(defn ^:export init
  []
  (rfe/start! +routes+
              #(re-frame/dispatch [::routing/navigate %])
              {:use-fragment false})
  (reagent/render [main-view] (.getElementById js/document "app")))

(init)

(ns main
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [reitit.frontend.easy :as rfe]
            [routing]
            [routes :refer [+routes+]]
            [ajax.core :refer [GET]]))

(defn current-route
  []
  (when-let [route-match @(re-frame/subscribe [::routing/match])]
    (if-let [view-fn (get-in route-match [:data :view])]
      [view-fn (:params view-fn)]
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
  (let [data (reagent/atom events)]
    (GET
      "http://localhost:8080/api/event"
      {:handler #(reset! data %)})
    (fn render []
       [:table.table
        [:thead
         [:tr
          [:th
           {:on-click
            #(swap!
               data
               (fn [x] (sort-by :title x)))}
           "Title"]
          [:th
           {:on-click
            #(swap!
               data
               (fn [x] (sort-by :description x)))}
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

(ns http.app
  (:require [hiccup.core :refer [html]]
            [ring.util.http-response :refer [ok]]
            [ring.util.response :as response]
            [metosin.ring.util.hash :as hash]
            [metosin.ring.util.cache :as cache]))

(defn index-handler [_]
  (-> (html
        [:head
         [:title "Event app"]
         [:meta {:charset "UTF-8"}]
         [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
         [:link {:rel "stylesheet" :href (str "/css/main.css?v=" (hash/resource-hash "public/css/main.css"))}]]
        [:body
         [:div#app]
         [:script {:src (str "/js/main.js?v=" (hash/resource-hash "public/js/main.js")) :type "text/javascript"}]])
      (ok)
      (response/content-type "text/html; charset=UTF-8")
      (cache/cache-control cache/no-cache)))

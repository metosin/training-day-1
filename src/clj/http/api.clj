(ns http.api
  (:require [integrant.core :as ig]
            [reitit.ring :as reitit]
            [reitit.ring.spec :as rrs]
            [reitit.coercion.spec]
            [clojure.spec.alpha :as spec]
            [muuntaja.core :as muuntaja]
            [http.app :refer [index-handler]]
            [http.middleware :refer [middleware]]
            [http.routes :refer [get-routes]]
            [util.ring :refer [mk-static-handler]]))

(defmethod ig/init-key :component/api-handler
  [_ config]
  (reitit/ring-handler
    (reitit/router
      [["/health" {:get {:responses {200 {:body {:status (spec/spec #{:pass :fail :warn})}}}
                         :handler (constantly {:status 200
                                               :body {:status :pass}
                                               :headers {"content-type" "application/health+json"}})}}]
       ["/api" (get-routes config)]]
      {:data     {:coercion   reitit.coercion.spec/coercion
                  :muuntaja   muuntaja/instance
                  :middleware middleware}
       :validate rrs/validate})
    (some-fn
      (mk-static-handler)
      (reitit/create-default-handler {:not-found index-handler}))))

(ns main
  (:require [org.httpkit.server :refer [run-server]]
            [jsonista.core :as json]))

(defn parse-params
  [query-string]
  (->> (re-seq #"([^=^&]+)=(\d+)" query-string)
       (reduce (fn [acc [_ k v]] (assoc acc ( keyword k) v)) {})))

(defn handle
  [req]
  (case (:uri req)
    "/"
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/write-value-as-string {"statement" "Clojure is quite good!"})}
    "/add"
    (let [params (some-> req :query-string (parse-params))
          a (some-> (:a params) (Integer/parseInt))
          b (some-> (:b params) (Integer/parseInt))]
      (if (and a b)
        {:status 200
         :headers {"Content-Type" "application/json"}
         :body (json/write-value-as-string {"a" a
                                            "b" b
                                            "sum" (+ a b)})}
        {:status 422}))
    {:status 404}))

(defn -main
  [& args]
  (let [port (-> (System/getenv "PORT") (Integer/parseInt))]
    (run-server handle {:port port})
    (println "Server running in port" port)))

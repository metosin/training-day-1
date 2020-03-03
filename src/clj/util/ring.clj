(ns util.ring
  (:require [clojure.java.io :as io]
            [ring.middleware.content-type :as content-type]
            [ring.util.http-response :as resp]))

(defn mk-static-handler []
  (-> (if (.exists (io/file "target/dev/resources"))
        (fn [req]
          (if (= (:request-method req) :get)
            ;; In dev mode:
            ;; Compiled JS/CSS from file system
            ;; Static files from classpath
            (or (resp/file-response (:uri req) {:root "target/dev/resources/public"})
                (resp/resource-response (:uri req) {:root "public"}))))
        (fn [req]
          (if (= (:request-method req) :get)
            (resp/resource-response (:uri req) {:root "public"}))))
      (content-type/wrap-content-type)))

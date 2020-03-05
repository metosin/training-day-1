(defproject clj-training "1.0.0"
  :description "Clojure(Script) training"
  :url "https://github.com/metosin/training-day-1"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.logging "1.0.0"]
                 [integrant "0.8.0"]
                 [integrant/repl "0.3.1"]
                 [aero "1.1.6"]
                 [environ "1.1.0"]
                 [hikari-cp "2.10.0"]
                 [org.postgresql/postgresql "42.2.10"]
                 [org.flywaydb/flyway-core "6.2.4"]
                 [duct/server.http.jetty "0.2.0"]
                 [metosin/reitit "0.4.2"]
                 [honeysql "0.9.9"]
                 [nilenso/honeysql-postgres "0.2.6"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [metosin/muuntaja "0.6.6"]
                 [org.slf4j/slf4j-log4j12 "1.7.30"]
                 [hiccup "1.0.5"]
                 [metosin/ring-http-response "0.9.1"]
                 [metosin/metosin-common "0.5.0"]]
  :source-paths ["src/clj" "src/cljs"]
  :resource-paths ["resources"]
  :target-path "target/%s/"
  :main ^:skip-aot main
  :profiles {:dev {:source-paths ["src/clj" "dev/clj"]
                   :repl-options {:init-ns user}}
             :cljs {:source-paths ["src/cljs"]
                    :dependencies [[thheller/shadow-cljs "2.8.90"]
                                   [metosin/reitit-core "0.4.2"]
                                   [metosin/reitit-frontend "0.4.2"]
                                   [reagent "0.9.1"]
                                   [re-frame "0.11.0"]
                                   [cljs-ajax "0.8.0"]]}})

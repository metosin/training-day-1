(defproject clj-training "1.0.0"
  :description "Clojure(Script) training"
  :url "https://github.com/metosin/training-day-1"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [http-kit "2.3.0"]
                 [metosin/jsonista "0.2.5"]]
  :plugins [[nightlight/lein-nightlight "RELEASE"]]
  :source-paths ["src"]
  :target-path "target/%s/"
  :main ^:skip-aot main)

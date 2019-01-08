(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIX ME"
  :url "https://github.com/FIX-ME/{{name}}"
  :min-lein-version "2.8.3"
  :license {:name "The Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [reagent "0.8.1" :exclusions [[cljsjs/react]
                                               [cljsjs/react-dom]
                                               [cljsjs/create-react-class]]]
                 [re-frame "0.10.6"]]
  :plugins [[lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            [lein-figwheel "0.5.17"]]
  :source-paths ["src"]
  :clean-targets ["target" "node_modules" "package.json" "package-lock.json"]
  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src"]
                        :figwheel     {:on-jsload "{{name}}.core/load"}
                        :compiler     {:main {{name}}.core
                                       :asset-path "target/js/compiled/dev"
                                       :output-to "target/js/compiled/{{name}}.js"
                                       :output-dir "target/js/compiled/dev"
                                       :target :nodejs
                                       :optimizations :none
                                       :source-map-timestamp true
                                       :npm-deps             {:blessed                   "0.1.81"
                                                              :react-blessed             "0.5.0"
                                                              :react                     "16.7.0"
                                                              :react-dom                 "16.7.0"
                                                              :create-react-class        "15.6.3"
                                                              :node-json-color-stringify "1.10"
                                                              :ws                        "6.1.2"}
                                       :install-deps         true}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler     {:output-to     "target/main.js"
                                       :output-dir    "target/js/compiled/prod"
                                       :target        :nodejs
                                       :optimizations :simple
                                       :npm-deps      {:blessed                   "0.1.81"
                                                       :react-blessed             "0.5.0"
                                                       :react                     "16.7.0"
                                                       :react-dom                 "16.7.0"
                                                       :create-react-class        "15.6.3"
                                                       :node-json-color-stringify "1.10"}
                                       :install-deps true}}]}
  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.13"]
                                  [com.cemerick/piggieback "0.2.2"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})

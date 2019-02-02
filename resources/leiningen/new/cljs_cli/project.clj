(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME"
  :url "https://github.com/FIXME/{{raw-name}}"
  :min-lein-version "2.7.1"
  :license {:name "The Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [mount "0.1.15"]
                 [reagent "0.8.1" :exclusions [[cljsjs/react]
                                               [cljsjs/react-dom]
                                               [cljsjs/create-react-class]]]
                 [re-frame "0.10.6"]]{{#lein-figwheel?}}
  :plugins [[lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            [lein-figwheel "0.5.18"]]{{/lein-figwheel?}}
  :source-paths ["src"]
  {{#figwheel-main?}}
  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"   ["trampoline" "run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test"  ["trampoline" "run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" {{main-ns}}.test-runner]}
  {{/figwheel-main?}}
  {{#lein-figwheel?}}
  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src" "env/dev"]
                        :figwheel     {:on-jsload "{{raw-name}}.core/load"}
                        :compiler     {:main {{raw-name}}.app
                                       :asset-path "target/js/compiled/dev"
                                       :output-to "target/js/compiled/{{raw-name}}.js"
                                       :output-dir "target/js/compiled/dev"
                                       :target :nodejs
                                       :optimizations :none
                                       :source-map-timestamp true
                                       :npm-deps             {:blessed                   "0.1.81"
                                                              :react-blessed             "0.5.0"
                                                              :react                     "16.7.0"
                                                              :react-dom                 "16.7.0"
                                                              :create-react-class        "15.6.3"
                                                              :node-json-color-stringify "1.1.0"
                                                              :ws                        "6.1.2"}
                                       :install-deps         true}}
                       {:id           "prod"
                        :source-paths ["src"]
                        :compiler     {:main {{raw-name}}.core
                                       :output-to     "target/main.js"
                                       :output-dir    "target/js/compiled/prod"
                                       :target        :nodejs
                                       :optimizations :simple
                                       :npm-deps      {:blessed                   "0.1.81"
                                                       :react-blessed             "0.5.0"
                                                       :react                     "16.7.0"
                                                       :react-dom                 "16.7.0"
                                                       :create-react-class        "15.6.3"
                                                       :node-json-color-stringify "1.1.0"}
                                       :install-deps true}}]}
  {{/lein-figwheel?}}{{^shadow?}}
  :profiles {:dev {:dependencies [{{#lein-figwheel?}}[figwheel-sidecar "0.5.18"]
                                  {{/lein-figwheel?}}[cider/piggieback "0.3.10"]
                                  [org.clojure/tools.namespace "0.2.11"]{{#figwheel-main?}}
                                  [com.bhauman/figwheel-main "0.2.0"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]{{/figwheel-main?}}]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   :clean-targets ["target" "node_modules"]
                   :source-paths ["src" "env/dev"]}}{{/shadow?}})

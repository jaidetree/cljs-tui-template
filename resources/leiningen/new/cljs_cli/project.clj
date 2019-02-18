{{=<% %>=}}
(defproject <%raw-name%> "0.1.0-SNAPSHOT"
  :description "FIXME"
  :url "https://github.com/FIXME/<%raw-name%>"
  :min-lein-version "2.7.1"
  :license {:name "The Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"
            :distribution :repo}
  :dependencies [<%#clj-deps%>[<%name%> "<%version%>"<%#exclusions?%> :exclusions [<%#exclusions%>[<%name%>]<%^last%>
                                               <%/last%><%/exclusions%>]<%/exclusions?%>]<%^last%>
                 <%/last%><%/clj-deps%>]<%#lein-figwheel?%>
  :plugins [[lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            [lein-figwheel "0.5.18"]]<%/lein-figwheel?%>
  :source-paths ["src"]<%#figwheel-main?%>
  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:prod"  ["trampoline" "run" "-m" "figwheel.main" "-bo" "prod"]
            "fig:test"  ["trampoline" "run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" <%main-ns%>.test-runner]}
  <%/figwheel-main?%><%#lein-figwheel?%>
  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src" "env/dev"]
                        :figwheel     {:on-jsload            <%raw-name%>.app/reload!}
                        :compiler     {:main                 <%raw-name%>.app
                                       :asset-path           "target/js/compiled/dev"
                                       :output-to            "target/js/compiled/<%raw-name%>.js"
                                       :output-dir           "target/js/compiled/dev"
                                       :target               :nodejs
                                       :optimizations        :none
                                       :source-map-timestamp true}}
                       {:id           "test"
                        :source-paths ["src" "test"]
                        :figwheel     {:on-jsload     <%raw-name%>.test-runner/main!}
                        :compiler     {:main          <%raw-name%>.test-runner
                                       :asset-path    "target/js/compiled/test"
                                       :output-to     "target/test.js"
                                       :output-dir    "target/js/compiled/test"
                                       :target        :nodejs
                                       :optimizations :none}}
                       {:id           "prod"
                        :source-paths ["src"]
                        :compiler     {:main          <%raw-name%>.main
                                       :output-to     "target/main.js"
                                       :output-dir    "target/js/compiled/prod"
                                       :target        :nodejs
                                       :optimizations :simple}}]}<%/lein-figwheel?%>
  :profiles {:dev {:dependencies [<%#lein-figwheel?%>[figwheel-sidecar "0.5.18"]
                                  <%/lein-figwheel?%>[cider/piggieback "0.3.10"]
                                  [org.clojure/tools.namespace "0.2.11"]<%#figwheel-main?%>
                                  [com.bhauman/figwheel-main "0.2.0"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]<%/figwheel-main?%>]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   :clean-targets ["target" "node_modules"]
                   :source-paths ["src" "env/dev"]}})
<%={{ }}=%>

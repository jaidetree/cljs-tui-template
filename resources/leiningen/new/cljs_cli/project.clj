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
            "fig:min"   ["trampoline" "run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test"  ["trampoline" "run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" <%main-ns%>.test-runner]}
  <%/figwheel-main?%><%#lein-figwheel?%>
  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src" "env/dev"]
                        :figwheel     {:on-jsload "<%raw-name%>.core/load"}
                        :compiler     {:main <%raw-name%>.app
                                       :asset-path "target/js/compiled/dev"
                                       :output-to "target/js/compiled/<%raw-name%>.js"
                                       :output-dir "target/js/compiled/dev"
                                       :target :nodejs
                                       :optimizations :none
                                       :source-map-timestamp true
                                       :npm-deps             {<%#cljs-deps.common%>:<%name%> "<%version%>"<%^last%>
                                                              <%/last%><%/cljs-deps.common%><%#cljs-deps.dev%>
                                                              :<%name%> "<%version%>"<%^last%>
                                                              <%/last%><%/cljs-deps.dev%>}
                                       :install-deps         true}}
                       {:id           "prod"
                        :source-paths ["src"]
                        :compiler     {:main <%raw-name%>.core
                                       :output-to     "target/main.js"
                                       :output-dir    "target/js/compiled/prod"
                                       :target        :nodejs
                                       :optimizations :simple
                                       :npm-deps      {<%#cljs-deps.common%>:<%name%> "<%version%>"<%^last%>
                                                       <%/last%><%/cljs-deps.common%>}
                                       :install-deps true}}]}<%/lein-figwheel?%>
  :profiles {:dev {:dependencies [<%#lein-figwheel?%>[figwheel-sidecar "0.5.18"]
                                  <%/lein-figwheel?%>[cider/piggieback "0.3.10"]
                                  [org.clojure/tools.namespace "0.2.11"]<%#figwheel-main?%>
                                  [com.bhauman/figwheel-main "0.2.0"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]<%/figwheel-main?%>]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   :clean-targets ["target" "node_modules"]
                   :source-paths ["src" "env/dev"]}})
<%={{ }}=%>

(ns leiningen.new.cljs-cli
  (:require [leiningen.new.templates :refer [->files
                                             date
                                             multi-segment
                                             name-to-path
                                             project-name
                                             renderer
                                             sanitize-ns
                                             year]]
            [leiningen.core.main :as main]))

(def render (renderer "cljs-cli"))

(defn mark-last
  [deps]
  (assoc-in deps [(dec (count deps)) :last] true))

(defn format-deps
  [deps]
  (->> deps
       (map (fn format-dep
              [[name version & {:keys [exclusions]}]]
              {:name name
               :version version
               :exclusions? (some? exclusions)
               :exclusions (when exclusions
                             (format-deps exclusions))}))
       (into [])
       (mark-last)))

(def clj-deps (format-deps
               '[[org.clojure/clojure       "1.10.0"]
                 [org.clojure/clojurescript "1.10.516"]
                 [mount                     "0.1.15"]
                 [reagent                   "0.8.1" :exclusions [[cljsjs/react]
                                                                 [cljsjs/react-dom]
                                                                 [cljsjs/create-react-class]]]
                 [re-frame                  "0.10.6"]]))

(def cljs-deps {:common (format-deps
                         '[[blessed                   "0.1.81"]
                           [react-blessed             "0.5.0"]
                           [react                     "16.7.0"]
                           [react-dom                 "16.7.0"]
                           [create-react-class        "15.6.3"]
                           [node-json-color-stringify "1.1.0"]])
                :shadow (format-deps
                         '[[shadow-cljs               "2.7.24"]
                           [source-map-support        "0.5.10"]])
                :dev    (format-deps
                         '[[ws                        "6.1.2"]])})

(def files {:common          [[".gitignore"                               "gitignore"]
                              [".hgignore"                                "hgignore"]
                              ["CHANGELOG.md"                             "CHANGELOG.md"]
                              ["docs/intro.md"                            "docs/intro.md"]
                              ["env/dev/{{nested-dirs}}/app.cljs"         "env/dev/app.cljs"]
                              ["env/dev/{{nested-dirs}}/debug/views.cljs" "env/dev/debug/views.cljs"]
                              ["package.json"                             "package.json"]
                              ["LICENSE"                                  "LICENSE"]
                              ["README.md"                                "README.md"]
                              ["src/{{nested-dirs}}/core.cljs"            "src/core.cljs"]
                              ["src/{{nested-dirs}}/events.cljs"          "src/events.cljs"]
                              ["src/{{nested-dirs}}/keys.cljs"            "src/keys.cljs"]
                              ["src/{{nested-dirs}}/subs.cljs"            "src/subs.cljs"]
                              ["src/{{nested-dirs}}/views.cljs"           "src/views.cljs"]
                              ["test/{{nested-dirs}}/core_test.cljs"      "test/core_test.cljs"]]

            "+lein-figwheel" [["env/dev/user.clj"                      "lein-figwheel/env/dev/user.clj"]
                              ["project.clj"                           "project.clj"]
                              ["test/{{nested-dirs}}/test_runner.cljs" "lein-figwheel/test/test_runner.cljs"]]

            "+figwheel-main" [["env/dev/user.clj"                      "figwheel-main/env/dev/user.clj"]
                              ["dev.cljs.edn"                          "figwheel-main/dev.cljs.edn"]
                              ["figwheel-main.edn"                     "figwheel-main/figwheel-main.edn"]
                              ["project.clj"                           "project.clj"]
                              ["test.cljs.edn"                         "figwheel-main/test.cljs.edn"]
                              ["test/{{nested-dirs}}/test_runner.cljs" "figwheel-main/test/test_runner.cljs"]]

            "+shadow"        [["shadow-cljs.edn" "shadow/shadow-cljs.edn"]]})

(def opts (set (keys files)))

(defn select-files
  [args]
  (->> args
       (select-keys files)
       (vals)
       (reduce into (get files :common))))

(defn render-template
  [data [dest template]]
  [dest (render template data)])

(defn create-files
  [name args]
  (let [render (renderer "cljs-cli")
        main-ns (sanitize-ns name)
        type (first args)
        data {:raw-name name
              :name (project-name name)
              :namespace (multi-segment main-ns)
              :main-ns main-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)
              :date (date)
              :lein-figwheel? (= type "+lein-figwheel")
              :figwheel-main? (= type "+figwheel-main")
              :shadow?        (= type "+shadow")
              :clj-deps clj-deps
              :cljs-deps cljs-deps}]
    (main/info "Generating fresh 'lein new' cljs-cli project.")
    (->> args
         (mapcat #(get files %))
         (into (get files :common))
         (map #(render-template data %))
         (apply ->files data))))

(defn args-valid?
  [args]
  (and (every? opts args)
       (= (count (filter opts args)) 1)))

(defn help
  []
  (println "USAGE: lein new cljs-cli [args...]")
  (println "")
  (println   "  Args can be one of the following:

    +lein-figwheel
        Generate a ClojureScript CLI template using lein-fighwheel.
        https://github.com/bhauman/lein-figwheel

    +figwheel-main
        Generate a ClojureScript CLI template using figwheel-main.
        https://figwheel.org/

    +shadow
        Generate a ClojureScript CLI template using shadow-cljs
        http://shadow-cljs.org/"))

(defn display-help
  []
  (help)
  (System/exit 0))

(defn display-error
  [args]
  (println "Invalid arguments to new cljs-cli template.")
  (println "Received:" (clojure.string/join " " args))
  (println "")
  (help)
  (System/exit 1))

(defn cljs-cli
  "FIXME: write documentation"
  ([name]
   (create-files name ["+figwheel-main"]))
  ([name & args]
   (if (args-valid? args)
     (create-files name args)
     (display-error args))))

(comment
 (select-files [])
 (select-files ["+bad"])
 (select-files ["+lein-figwheel"])
 (args-valid? ["+lein-figwheel"])
 (args-valid? ["+figwheel-main"])
 (args-valid? ["+shadow"])
 (args-valid? [])
 (args-valid? ["+lein-figwheel" "+figwheel-main" "+shadow"])
 (args-valid? ["+false" "+lein-figwheel"])
 (help))

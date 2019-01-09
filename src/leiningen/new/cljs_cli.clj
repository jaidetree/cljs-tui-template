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

(defn cljs-cli
  "FIXME: write documentation"
  [name]
  (let [render (renderer "cljs-cli")
        main-ns (sanitize-ns name)
        data {:raw-name name
              :name (project-name name)
              :namespace (multi-segment main-ns)
              :main-ns main-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)
              :date (date)}]
    (main/info "Generating fresh 'lein new' cljs-cli project.")
    (->files data
             [".gitignore"                          (render "gitignore" data)]
             [".hgignore"                           (render "hgignore" data)]
             ["CHANGELOG.md"                        (render "CHANGELOG.md" data)]
             ["LICENSE"                             (render "LICENSE" data)]
             ["README.md"                           (render "README.md" data)]
             ["docs/intro.md"                       (render "intro.md" data)]
             ["project.clj"                         (render "project.clj" data)]
             ["src/{{nested-dirs}}/core.cljs"       (render "core.cljs" data)]
             ["src/{{nested-dirs}}/events.cljs"     (render "events.cljs" data)]
             ["src/{{nested-dirs}}/keys.cljs"       (render "keys.cljs" data)]
             ["src/{{nested-dirs}}/subs.cljs"       (render "subs.cljs" data)]
             ["src/{{nested-dirs}}/view.cljs"       (render "view.cljs" data)]
             ["src/{{nested-dirs}}/debug/view.cljs" (render "debug_view.cljs" data)]
             ["test/{{nested-dirs}}/core_test.cljs" (render "test.cljs" data)])))

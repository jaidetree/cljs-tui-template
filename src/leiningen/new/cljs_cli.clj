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
             [".gitignore"                           (render "gitignore" data)]
             [".hgignore"                            (render "hgignore" data)]
             ["CHANGELOG.md"                         (render "CHANGELOG.md" data)]
             ["LICENSE"                              (render "LICENSE" data)]
             ["README.md"                            (render "README.md" data)]
             ["dev/user.clj"                         (render "dev/user.clj" data)]
             ["docs/intro.md"                        (render "docs/intro.md" data)]
             ["package.json"                         (render "package.json" data)]
             ["project.clj"                          (render "lein-figwheel/project.clj" data)]
             ["src/{{nested-dirs}}/core.cljs"        (render "src/core.cljs" data)]
             ["src/{{nested-dirs}}/events.cljs"      (render "src/events.cljs" data)]
             ["src/{{nested-dirs}}/keys.cljs"        (render "src/keys.cljs" data)]
             ["src/{{nested-dirs}}/subs.cljs"        (render "src/subs.cljs" data)]
             ["src/{{nested-dirs}}/views.cljs"       (render "src/views.cljs" data)]
             ["src/{{nested-dirs}}/debug/views.cljs" (render "src/debug/views.cljs" data)]
             ["test/{{nested-dirs}}/core_test.cljs"  (render "test/core_test.cljs" data)])))

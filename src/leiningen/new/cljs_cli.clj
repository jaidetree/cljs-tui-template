(ns leiningen.new.cljs-cli
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "cljs-cli"))

(defn cljs-cli
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' cljs-cli project.")
    (->files data
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["project.clj"                (render "project.clj" data)])))

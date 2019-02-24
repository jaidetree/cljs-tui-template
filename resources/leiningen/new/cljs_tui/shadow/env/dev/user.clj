(ns user
  (:require
   [shadow.cljs.devtools.api :refer [nrepl-select]]))

;; Required for proto-repl ns refresh
;; https://shadow-cljs.github.io/docs/UsersGuide.html#_proto_repl_atom
(defn reset [])

(defn repl
  "Start a shadow-cljs ClojureScript repl ontop of a connected nREPL session."
  []
  (nrepl-select :app))

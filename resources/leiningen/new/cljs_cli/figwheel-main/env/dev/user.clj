(ns user
  (:require [figwheel.main.api]))

(defn start
  "Start build server and build the dev app."
  []
  (figwheel.main.api/start "dev"))

(defn repl
  "Start a cljs-repl"
  []
  (figwheel.main.api/cljs-repl "dev"))

(ns user
  (:require [figwheel.main.api]))

(defn start
  "This starts the figwheel build"
  []
  (figwheel.main.api/start "dev"))

(defn repl
  []
  (figwheel.main.api/cljs-repl "dev"))

(ns user
  (:require [figwheel.main.api]))

;; user is a namespace that the Clojure runtime looks for and
;; loads if its available

;; You can place helper functions in here. This is great for starting
;; and stopping your webserver and other development services

;; The definitions in here will be available if you run "clj -Acljs" or launch a
;; Clojure repl some other way

(defn start
  "This starts the figwheel build"
  []
  ;; otherwise you can pass a configuration into start-figwheel! manually
  (figwheel.main.api/start "dev"))

(defn repl
  []
  (figwheel.main.api/cljs-repl "dev"))

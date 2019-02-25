(ns {{namespace}}
  "Create application state with mount."
  (:require
   [cljs.nodejs :as nodejs]
   [mount.core :refer [defstate] :as mount]
   [{{main-ns}}.keys :as keys]))

;; Import required npm & node dependencies
(def blessed (js/require "blessed"))
(def fs (js/require "fs"))
(def react-blessed (js/require "react-blessed"))
(def tty (js/require "tty"))

;; Setup mount to work in ClojureScript
(mount/in-cljc-mode)

(defstate tty-fd
  "Used to open a text-terminal interface for reading and writing."
  :start (.openSync fs "/dev/tty" "r+"))

(defstate program
  "Blessed program state describes general app behavior.
  https://github.com/chjj/blessed/blob/v0.1.81/lib/program.js"
  :start
  (.program blessed
            #js {:input (.ReadStream tty @tty-fd)
                 :output (.WriteStream tty @tty-fd)}))

(defstate screen
  "Blessed screen stores state like terminal size and provides methods for
  binding keys.
  https://github.com/chjj/blessed#screen-from-node"
  :start
  (doto
    (.screen blessed
             #js {:program @program
                  :autoPadding true
                  :smartCSR true
                  :title "{{name}}"})
    keys/setup))

;; Create a render function to translate hiccup into blessed components
(defonce render (.createBlessedRenderer react-blessed blessed))

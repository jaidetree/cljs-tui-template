(ns {{namespace}}
  (:require
   [cljs.nodejs :as nodejs]
   [mount.core :refer [defstate] :as mount]
   [{{main-ns}}.keys :as keys]))

(def blessed (js/require "blessed"))
(def fs (js/require "fs"))
(def react-blessed (js/require "react-blessed"))
(def tty (js/require "tty"))

(mount/in-cljc-mode)

(defstate tty-fd :start (.openSync fs "/dev/tty" "r+"))
(defstate program :start
  (.program blessed
            #js {:input (.ReadStream tty @tty-fd)
                 :output (.WriteStream tty @tty-fd)}))

(defstate screen :start
  (doto
    (.screen blessed
             #js {:program @program
                  :autoPadding true
                  :smartCSR true
                  :title "{{name}}"})
    keys/setup))

(defonce render (.createBlessedRenderer react-blessed blessed))

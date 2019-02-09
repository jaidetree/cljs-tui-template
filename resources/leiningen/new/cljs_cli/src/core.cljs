(ns {{namespace}}
  (:require
   [cljs.nodejs :as nodejs]
   ;; TODO: Which of these do we need?
   [my-test-project.debug.views :as debug]
   [my-test-project.keys :as keys]
   [my-test-project.subs]
   [my-test-project.events]
   [my-test-project.views :as views]
   [mount.core :refer [defstate] :as mount]
   [re-frame.core :as rf]
   [reagent.core :as r]))

(def blessed (js/require "blessed"))
(def fs (js/require "fs"))
(def react-blessed (js/require "react-blessed"))
(def tty (js/require "tty"))

(mount/in-cljc-mode)

(defstate tty-fd :start (.openSync fs "/dev/tty" "r+"))
(defstate program :start
  (.program blessed #js
            {:input (.ReadStream tty @tty-fd)
             :output (.WriteStream tty @tty-fd)}))

(defstate screen :start
  (doto
    (.screen blessed #js
             {:program @program
              :autoPadding true
              :smartCSR true
              :title "{{name}}"})
    keys/setup))

(defonce render (.createBlessedRenderer react-blessed blessed))

(defn init!
  [view]
  (mount/start)
  (rf/dispatch-sync [:init])
  (-> (r/reactify-component view)
      (r/create-element #js {})
      (render @screen)))

(defn main!
  []
  (init! views/root))

(set! *main-cli-fn* main!)

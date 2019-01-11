(ns {{namespace}}
  (:require
   ["react-blessed" :as react-blessed]
   [blessed :as blessed]
   [cljs.nodejs :as nodejs]
   ;; TODO: Which of these do we need?
   [{{main-ns}}.debug.views :as debug]
   [{{main-ns}}.keys :as keys]
   [{{main-ns}}.subs]
   [{{main-ns}}.events]
   [{{main-ns}}.views :as views]
   [fs :as fs]
   [mount.core :refer [defstate] :as mount]
   [re-frame.core :as rf]
   [reagent.core :as r]
   [tty :as tty]))

(mount/in-cljc-mode)

(defstate tty-fd :start (fs/openSync "/dev/tty" "r+"))
(defstate program :start (blessed/program #js
                                          {:input (tty/ReadStream @tty-fd)
                                           :output (tty/WriteStream @tty-fd)}))

(defstate screen :start (doto
                         (blessed/screen #js {:program @program
                                              :autoPadding true
                                              :smartCSR true
                                              :title "{{name}}"})
                         keys/setup))

(defonce render (react-blessed/createBlessedRenderer blessed))

(defn log-fn
  [& args]
  (swap! debug/logger conj (clojure.string/join " " args)))

(defn load
  []
  (-> (r/reactify-component views/root)
      (r/create-element #js {})
      (render @screen)))

(defn -main
  []
  (mount/start)
  (rf/dispatch-sync [:init])
  (load))

(set! (.-log js/console) log-fn)
(set! (.-error js/console) log-fn)
(re-frame.loggers/set-loggers! {:log log-fn
                                :warn log-fn})

(set! *main-cli-fn* -main)

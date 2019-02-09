(ns {{namespace}}
  (:require
   ["react-blessed" :as react-blessed]
   ["blessed" :as blessed]
   [clojure.tools.cli :refer [parse-opts]]
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

(def fs (js/require "fs"))
(def tty (js/require "tty"))

(mount/in-cljc-mode)

(defstate tty-fd :start (.openSync fs "/dev/tty" "r+"))
(defstate program :start (blessed/program #js
                                          {:input (.ReadStream tty @tty-fd)
                                           :output (.WriteStream tty @tty-fd)}))

(defstate screen :start (doto
                         (blessed/screen #js {:program @program
                                              :autoPadding true
                                              :smartCSR true
                                              :title "{{name}}"})
                         keys/setup))

(defonce render (react-blessed/createBlessedRenderer blessed))

(def cli-options
  [["-p" "--port PORT" "port number"
    :default 80
    :parse-fn #(js/Number %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-v" nil "Verbosity level"
    :id :verbosity
    :default 0
    :update-fn inc]
   ["-h" "--help"]])

(defn args->opts
  [args]
  (parse-opts args cli-options))

(defn init!
  [view & {:keys [opts]}]
  (mount/start)
  (rf/dispatch-sync [:init (:options opts)])
  (-> (r/reactify-component view)
      (r/create-element #js {})
      (render @screen)))

(defn main!
  [& args]
  (init! views/root :opts (args->opts args)))

(set! *main-cli-fn* main!)

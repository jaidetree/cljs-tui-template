(ns {{main-ns}}.main
  "Main application entrypoint. Defines root UI view, cli-options,
  arg parsing logic, and initialization routine"
  (:require
   [clojure.tools.cli :refer [parse-opts]]
   [mount.core :refer [defstate] :as mount]
   [re-frame.core :as rf]
   [reagent.core :as r]
   [{{main-ns}}.core :refer [render screen]]
   [{{main-ns}}.demo.views :refer [demo]]
   [{{main-ns}}.events]
   [{{main-ns}}.subs]
   [{{main-ns}}.views :as views]))

(defn ui
  "Root ui view.
  Takes no arguments.
  Returns hiccup demo element to run the demo app."
  [_]
  (let [view @(rf/subscribe [:view])]
    [demo {:view view}]))

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
  "Takes a list of arguments.
  Returns a map of parsed CLI args."
  [args]
  (parse-opts args cli-options))

(defn init!
  "Initialize the application.
  Takes a root UI view function that returns a hiccup element and optionally
  a map of parsed CLI args.
  Returns rendered reagent view."
  [view & {:keys [opts]}]
  (mount/start)
  (rf/dispatch-sync [:init (:options opts)])
  (-> (r/reactify-component view)
      (r/create-element #js {})
      (render @screen)))

(defn main!
  "Main application entrypoint function. Initializes app, renders root UI view
  and initializes the re-frame app db.
  Takes list of CLI args.
  Returns rendered reagent view."
  [& args]
  (init! ui :opts (args->opts args)))

(set! *main-cli-fn* main!)

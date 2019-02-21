(ns {{#figwheel-main?}}^:figwheel-hooks {{/figwheel-main?}}{{main-ns}}.app
  (:require
   [reagent.core :as r]
   [{{main-ns}}.core :refer [render screen]]
   [{{main-ns}}.debug.views :as debug]
   [{{main-ns}}.main :as main]))

(defn main!
  "Main entrypoint when in development.
  Takes list of cli args, parses into opts map, inserts opts into re-frame db
  then initializes the ui."
  [& args]
  (main/init! debug/ui :opts (main/args->opts args)))

(defn log-fn
  "A general logging function used to display all console.log messages into
  the debug log-box.
  Takes variadic arguments.
  Appends string of all args to log."
  [& args]
  (swap! debug/logger conj (clojure.string/join " " args)))

(defn {{#figwheel-main?}}^:after-load {{/figwheel-main?}}reload!
  "Called when dev env is reloaded. Re-renders the ui and logs a message."
  [_]
  (-> (r/reactify-component debug/ui)
      (r/create-element #js {})
      (render @screen))
  (println "Reloading app"))

;; Override the log messages to display in our log-box
(set! (.-log js/console) log-fn)
(set! (.-error js/console) log-fn)
(set! (.-info js/console) log-fn)
(set! (.-debug js/console) log-fn)
(re-frame.loggers/set-loggers! {:log log-fn
                                :error log-fn
                                :warn log-fn})

(set! *main-cli-fn* main!)

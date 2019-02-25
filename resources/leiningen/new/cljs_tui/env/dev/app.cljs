(ns {{#figwheel-main?}}^:figwheel-hooks {{/figwheel-main?}}{{main-ns}}.app
  (:require
   [reagent.core :as r]
   [{{main-ns}}.core :refer [render screen]]
   [{{main-ns}}.debug.views :as debug]
   [{{main-ns}}.main :as main]))

(defn main!
  "Main development entrypoint.
  Takes list of cli args, parses into opts map, inserts opts into re-frame db
  then initializes the ui.
  Returns nil but mostly invokes side-effects."
  [& args]
  (main/init! debug/ui :opts (main/args->opts args)))

(defn log-fn
  "A log handler function to append messages to our debug/logger atom.
  Takes variadic arguments, typically strings or stringifiable args.
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

;; Override the console.* messages to use our log-fn. Later if we call
;; console.log or println the message shows up in our debug box.
(set! (.-log js/console) log-fn)
(set! (.-error js/console) log-fn)
(set! (.-info js/console) log-fn)
(set! (.-debug js/console) log-fn)
(re-frame.loggers/set-loggers! {:log log-fn
                                :error log-fn
                                :warn log-fn})

(set! *main-cli-fn* main!)

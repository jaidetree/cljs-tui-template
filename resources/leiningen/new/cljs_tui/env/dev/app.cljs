(ns {{#figwheel-main?}}^:figwheel-hooks {{/figwheel-main?}}{{main-ns}}.app
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]
   [{{main-ns}}.core :refer [render screen]]
   [{{main-ns}}.debug.views :as debug]
   [{{main-ns}}.demo.views :refer [demo]]
   [{{main-ns}}.main :as main]))

(defn ui
  "Basic wrapper to show the demo app and the debug view half height.
  Returns hiccup vector."
  [_]
  (let [view @(rf/subscribe [:view])
        rows (:rows @(rf/subscribe [:size]))]
    [demo
     {:view view}
     [debug/debug-box rows]]))

(defn main!
  "Main development entrypoint.
  Takes list of cli args, parses into opts map, inserts opts into re-frame db
  then initializes the ui.

  Returns nil but mostly invokes side-effects."
  [& args]
  (main/init! ui :opts (main/args->opts args)))

(defn {{#figwheel-main?}}^:after-load {{/figwheel-main?}}reload!
  "Called when dev env is reloaded. Re-renders the ui and logs a message."
  [_]
  (-> (r/reactify-component ui)
      (r/create-element #js {})
      (render @screen))
  (println "Reloading app"))

(defn log-fn
  "A log handler function to append messages to our debug/logger atom.
  Takes variadic arguments, typically strings or stringifiable args.
  Appends string of all args to log.

  Source for this came from:
  https://gist.github.com/polymeris/5e117676b79a505fe777df17f181ca2e#file-core-cljs-L105"
  [& args]
  (swap! debug/logger conj (clojure.string/join " " args)))

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

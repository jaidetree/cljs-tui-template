(ns {{#figwheel-main?}}^:figwheel-hooks {{/figwheel-main?}}{{main-ns}}.app
  (:require
   [reagent.core :as r]
   [{{main-ns}}.core :refer [render screen]]
   [{{main-ns}}.debug.views :as debug]
   [{{main-ns}}.main :as main]))

(defn main!
  [& args]
  (main/init! debug/ui :opts (main/args->opts args)))

(defn log-fn
  [& args]
  (swap! debug/logger conj (clojure.string/join " " args)))

(defn {{#figwheel-main?}}^:after-load {{/figwheel-main?}}reload!
  [_]
  (-> (r/reactify-component debug/ui)
      (r/create-element #js {})
      (render @screen))
  (println "Reloading app"))

(set! (.-log js/console) log-fn)
(set! (.-error js/console) log-fn)
(set! (.-info js/console) log-fn)
(set! (.-debug js/console) log-fn)
(re-frame.loggers/set-loggers! {:log log-fn
                                :error log-fn
                                :warn log-fn})

(set! *main-cli-fn* main!)

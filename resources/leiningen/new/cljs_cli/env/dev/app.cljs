(ns {{#figwheel-main?}}^:figwheel-hooks {{/figwheel-main?}}{{main-ns}}.app
  (:require
   [{{namespace}} :as core]
   [{{main-ns}}.debug.views :as debug]))

(defn main!
  [& args]
  (core/init! debug/root :opts (core/args->opts args)))

(defn log-fn
  [& args]
  (swap! debug/logger conj (clojure.string/join " " args)))

(defn {{#figwheel-main?}}^:after-load {{/figwheel-main?}}reload!
  []
  (println "Reloading app")
  (core/reload! debug/root))

(set! (.-log js/console) log-fn)
(set! (.-error js/console) log-fn)
(set! (.-info js/console) log-fn)
(set! (.-debug js/console) log-fn)
(re-frame.loggers/set-loggers! {:log log-fn
                                :error log-fn
                                :warn log-fn})

(set! *main-cli-fn* main!)

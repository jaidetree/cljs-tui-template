(ns {{^shadow?}}^:figwheel-no-load {{/shadow?}}{{main-ns}}.app
  (:require [{{namespace}} :as core]
            [{{main-ns}}.debug.views :as debug]))

(core/init! debug/root)

(defn log-fn
  [& args]
  (swap! debug/logger conj (clojure.string/join " " args)))

(set! (.-log js/console) log-fn)
(set! (.-error js/console) log-fn)
(set! (.-info js/console) log-fn)
(set! (.-debug js/console) log-fn)
(re-frame.loggers/set-loggers! {:log log-fn
                                :warn log-fn})

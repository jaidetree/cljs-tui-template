(ns {{main-ns}}.keys
  (:require-macros
   [reagent.core :refer [with-let]]))

(defmacro with-keys
  [screen key-bindings & body]
  `(with-let []
     ~@body
     (finally
       (doseq [[hotkeys f] bindings]
         (.unkey screen (clj->js hotkeys) f)))))

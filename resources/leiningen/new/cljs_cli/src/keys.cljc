(ns {{main-ns}}.keys
  (:require-macro
   [reagent.core :refer [with-let]])
  (:require
   [{{main-ns}}.core :refer [screen]]))

(defmacro with-keys
  [key-bindings & body]
  `(with-let []
     ~@body
     (finally
       (doseq [[hotkeys f] bindings]
         (.unkey screen (clj->js hotkeys) f)))))

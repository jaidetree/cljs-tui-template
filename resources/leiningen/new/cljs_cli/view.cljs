(ns {{main-ns}}.view
  (:require [{{main-ns}}.debug.view :as debug-view]))

(defn root [_]
  [:box#base {:left   0
              :right  0
              :width  "100%"
              :height "100%"}
   [debug-view/debug-box {:height 10}]])

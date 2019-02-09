(ns {{main-ns}}.views
  (:require [{{main-ns}}.debug.views :as debug]))

(defn root [_]
  [:box#base {:left   0
              :right  0
              :width  "100%"
              :height "100%"}
   [:text {:width   "40%"
           :content "It works"}]])

(ns {{main-ns}}.keys
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]))

(def global-bindings
  {["escape" "q" "C-c"] #(.exit js/process 0)})
   ; ["g"]                #(rf/dispatch [:movement/first])
   ; ["S-g"]              #(rf/dispatch [:movement/last])
   ; ["h" "left" "C-a"]   #(rf/dispatch [:movement/left])
   ; ["j" "down" "C-n"]   #(rf/dispatch [:movement/down])
   ; ["k" "up" "C-p"]     #(rf/dispatch [:movement/up])
   ; ["l" "right" "C-e"]  #(rf/dispatch [:movement/right])})

(defn bind-keys
  [screen key-bindings]
  (doseq [[hotkeys f] key-bindings]
    (.key screen (clj->js hotkeys) f)))

(defn unbind-keys
  [screen key-bindings]
  (doseq [[hotkeys f] key-bindings]
    (.unkey screen (clj->js hotkeys) f)))

(defn setup
  [screen]
  (bind-keys screen global-bindings))

(defn with-keys
  [screen key-bindings content]
  (r/with-let [_ (bind-keys screen key-bindings)]
    content
    (finally
      (unbind-keys screen key-bindings))))

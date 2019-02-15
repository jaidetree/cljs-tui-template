(ns {{main-ns}}.views)

(defn router
  [{:keys [views view] :as props}]
  [(get views view) props])

(defn root [_]
  [:box#base {:left   0
              :right  0
              :width  "100%"
              :height "100%"}
   [:text {:width   "40%"
           :content "It works"}]])

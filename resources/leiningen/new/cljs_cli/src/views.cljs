(ns {{main-ns}}.views
  (:require
   [reagent.core :as r]
   [{{main-ns}}.core :refer [screen]]
   [{{main-ns}}.keys :refer [with-keys]]))

(defn router
  [{:keys [views view] :as props}]
  [(get views view) props])

(defn find-index
  [target options]
  (some->> options
           (keys)
           (map-indexed vector)
           (filter (fn [[idx v]] (= v target)))
           (first)
           (first)))

(defn next-option
  [current options]
  (let [total (count options)
        current-idx (find-index current options)
        next-idx (inc current-idx)]
    (-> options
        (vec)
        (nth (if (< next-idx total) next-idx 0))
        (key))))

(defn prev-option
  [current options]
  (let [total (count options)
        current-idx (find-index current options)
        prev-idx (dec current-idx)]
    (-> options
        (vec)
        (nth (if (< prev-idx 0) (dec total) prev-idx))
        (key))))

(defn vertical-menu
  [{:keys [options default on-select]}]
  (r/with-let [selected (r/atom (or default (->> options first key)))]
    (with-keys @screen {["j"] #(swap! selected next-option options)
                        ["k"] #(swap! selected prev-option options)
                        ["enter"] #(on-select @selected)}
      (let [current @selected]
        [:box#menu
         {:top 1
          :left 1
          :right 1
          :bottom 1}
         (for [[idx [value label]] (map-indexed vector options)]
           [:box {:key value
                  :top idx
                  :style {:bg (when (= value current) :green)
                          :fg (when (= value current) :white)}
                  :height 1
                  :content label}])]))))

(defn root [_]
  [:box#base {:left   0
              :right  0
              :width  "100%"
              :height "100%"}
   [:text {:width   "40%"
           :content "It works"}]])

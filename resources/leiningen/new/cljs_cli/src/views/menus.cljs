(ns {{main-ns}}.views.menus
  (:require [reagent.core :as r]
            [{{main-ns}}.core :refer [screen]]
            [{{main-ns}}.keys :refer [with-keys]]))

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
         {:height (count options)}
         (for [[idx [value label]] (map-indexed vector options)]
           [:box {:key value
                  :top idx
                  :style {:bg (when (= value current) :green)
                          :fg (when (= value current) :white)}
                  :content label}])]))))

(comment
 (def options {:about "About"
               :help "Help"
               :library "Library"})
 (def sel (atom :about))
 (println @sel)
 (println (swap! sel next-option options))
 (reset! sel :about)
 (find-index :help options))

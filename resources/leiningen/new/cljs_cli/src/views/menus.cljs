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
           (keys)
           (first)))


(defn next
  [current options]
  (let [current-idx (find-index current options)]
   (min (inc current-idx) (dec (count options)))))

(defn prev
  [current options]
  (let [current-idx (find-index current options)]
    (max (dec current-idx) 0)))

(defn vertical-menu
  [{:keys [options default on-select]}]
  (let [selected (r/atom (or default (->> options first key)))]
    (with-keys @screen {["j"] #(swap! selected next options)
                        ["k"] #(swap! selected prev options)}
      [:box#menu
       {}
       (for [[value label] options]
         [:text {:content label
                 :key value
                 :style {:bg (when (= value selected) :green)
                         :fg (when (= value selected) :white)}}])])))

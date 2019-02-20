(ns {{main-ns}}.debug.views
  (:require
   [clojure.pprint :refer [pprint]]
   [clojure.string :refer [join]]
   [re-frame.core :as rf]
   [reagent.core :as r]
   [{{main-ns}}.core :refer [screen]]
   [{{main-ns}}.keys :refer [with-keys]]
   [{{main-ns}}.views :refer [router vertical-menu]]
   [{{main-ns}}.demo.views :refer [demo]]))

(defonce logger
  (r/atom []))

(defn log-height
  [screen]
  (- (/ (.-rows screen) 2)
     3))

(defn log-box
  []
  [:box#log
   {:top          0
    :bottom       0
    :right        0
    :width        "50%"
    :style        {:fg :yellow
                   :bg :grey}
    :scrollable   true
    :scrollbar    true
    :alwaysScroll true}
   [:text {:left    1
           :top     0
           :bottom  0
           :right   1
           :style   {:fg :yellow
                     :bg :grey}
           :content (->> (take-last (log-height @screen) @logger)
                         (join "\n"))}]])

(defn debug-box
  []
  [:text#debug {:bottom 0
                :left   0
                :width  " 100%"
                :height "50%"
                :style  {:border {:fg :yellow}}
                :border {:type :line}
                :label  "Debug info"}
   [:box {:width   "48%"
          :top 1
          :left 1
          :bottom 1
          :content (with-out-str (pprint @(rf/subscribe [:db])))}]
   [log-box]])

(defn ui
  [_]
  (let [view (:router/view @(rf/subscribe [:db]))]
    [demo
     {:view view}
     [debug-box]]))

(defn clear-log!
  []
  (reset! logger {}))

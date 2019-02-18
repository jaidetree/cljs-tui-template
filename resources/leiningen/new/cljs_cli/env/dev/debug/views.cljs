(ns {{main-ns}}.debug.views
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [{{main-ns}}.views :refer [router]]))

(defonce logger
  (r/atom []))

(defn log-box
  [n]
  [:text#log
   {:bottom       0
    :right        0
    :width        "50%"
    :height       n
    :style        {:fg :yellow :bg :grey}
    :scrollable   true
    :scrollbar    true
    :alwaysScroll true
    :content      (->> (take-last n @logger)
                       (clojure.string/join "\n"))}])

(defn debug-box
  [{:keys [height]}]
  [:text#debug {:bottom 0
                :left   0
                :width  "100%"
                :style  {:border {:fg :yellow}}
                :border {:type :line}
                :label  "Debug info"}
   [:text {:width   "40%"
           :content (str @(rf/subscribe [:db]))}]
   [log-box (dec height)]])

(defn home
  [_]
  [:box#home
   {:top 0
    :left 0
    :width "100%"
    :height 10
    :style {:border {:fg :green}}
    :border {:type :line}
    :label "Home"}
   [:text {:width "50%"
           :content "Welcome home"}]])


(defn root [_]
  [:box#base {:left   0
              :right  0
              :width  "100%"
              :height "100%"}
   [router {:views {:home home}
            :view (:router/view @(rf/subscribe [:db]))}]
   [debug-box {:height 10}]])

(defn clear-log!
  []
  (reset! logger {}))

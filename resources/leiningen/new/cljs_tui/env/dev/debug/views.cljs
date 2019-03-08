(ns {{main-ns}}.debug.views
  "General debug views. These wont be included in your production build by default.

  Portions based on:
  https://github.com/denisidoro/floki/blob/167b6da56fdee86043d34514b352c732fdfc3487/src/floki/debug/view.cljs
  and https://gist.github.com/polymeris/5e117676b79a505fe777df17f181ca2e"
  (:require
   [clojure.pprint :refer [pprint]]
   [clojure.string :refer [join]]
   [re-frame.core :as rf]
   [reagent.core :as r]
   [{{main-ns}}.views :refer [router vertical-menu]]))

(defonce logger
  (r/atom []))

(defn log-height
  "Calculates half the height of the screen minus 3 for padding and margin.
  Takes the number of rows that make up the terminal's character height.
  Returns an integer."
  [rows]
  (- (/ rows 2)
     3))

(defn log-box
  "Display a box that shows the last several lines of logged output based on
  screen height.
  Can be thrown off by multi-line lines of text.
  Returns hiccup vector.

  Source inspired by:
  https://gist.github.com/polymeris/5e117676b79a505fe777df17f181ca2e"
  [rows]
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
           :content (->> (take-last (log-height rows) @logger)
                         (join "\n"))}]])

(defn debug-box
  "Displays both the current state and last several lines of output.
  Takes number of rows representing the total screen height.
  Will display in the bottom half of the screen.
  Returns hiccup vector.

  Source for this came from:
  https://gist.github.com/polymeris/5e117676b79a505fe777df17f181ca2e"
  [rows]
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
   [log-box rows]])

(defn clear-log!
  "Util function to clear the log if needed. This should likely be called
  from the REPL during development."
  []
  (reset! logger {}))

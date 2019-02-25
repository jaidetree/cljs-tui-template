(ns {{main-ns}}.debug.views
  "General debug views. These wont be included in your production build by default.

  Portions based on:
  https://github.com/denisidoro/floki/blob/167b6da56fdee86043d34514b352c732fdfc3487/src/floki/debug/view.cljs"
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
  "Takes a blessed screen instance.
  Calculates half the height of the screen minus 3 for padding and margin.
  Returns an integer."
  [screen]
  (- (/ (.-rows screen) 2)
     3))

(defn log-box
  "Display a box that shows the last several lines of logged output based on
  screen height.
  Can be thrown off by multi-line lines of text.
  Returns hiccup vector."
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
  "General debug box UI.
  Displays both the current state and last several lines of output.
  Defaults to half the height of the screen.
  Returns hiccup vector."
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
  "Basic wrapper to show the demo app and the debug view half height.
  Returns hiccup vector."
  [_]
  (let [view @(rf/subscribe [:view])]
    [demo
     {:view view}
     [debug-box]]))

(defn clear-log!
  "Util function to clear the log if needed. This should likely be called
  from the REPL during development."
  []
  (reset! logger {}))

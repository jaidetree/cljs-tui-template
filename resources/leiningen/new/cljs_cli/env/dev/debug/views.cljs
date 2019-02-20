(ns {{main-ns}}.debug.views
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [{{main-ns}}.core :refer [screen]]
            [{{main-ns}}.keys :refer [with-keys]]
            [{{main-ns}}.views :refer [router vertical-menu]]))

(defonce logger
  (r/atom []))

(defn log-box
  [n]
  [:text#log
   {:top          0
    :bottom       0
    :right        0
    :width        "50%"
    :style        {:fg :yellow :bg :grey}
    :scrollable   true
    :scrollbar    true
    :alwaysScroll true
    :content      (->> (take-last n @logger)
                       (clojure.string/join "\n"))}])

(defn debug-box
  []
  [:text#debug {:bottom 0
                :left   0
                :width  " 100%"
                :height "50%"
                :style  {:border {:fg :yellow}}
                :border {:type :line}
                :label  "Debug info"}
   [:text {:width   "40%"
           :content (str @(rf/subscribe [:db]))}]
   [log-box 10]])

(defn home
  [_]
  [:box#home
   {:top 0
    :left 0
    :width "100%"
    :height "50%"
    :style {:border {:fg :green}}
    :border {:type :line}
    :label " Home "}
   [:box#content
    {:top 1
     :left 1
     :right 1}
    [:box
     {:align :center
      :content "Welcome, you are successfully running the app. Happy hacking!"
      :style {:fg :green}}]
    [vertical-menu {:options {:about "About"
                              :resources "Resources"
                              :credits "Credits"}
                    :on-select #(rf/dispatch [:update {:router/view %}])
                    :props {:top 2
                            :left 0
                            :right 0}}]
    [:box#keys
     {:top 6
      :width "100%"
      :align :center
      :style {:padding 1
              :fg :yellow}
      :content "( j/up or k/down to move, enter to select. Enter or backspace to return. )"}]]])

(defn about
  [_]
  (with-keys @screen {["h" "backspace" "return"] #(rf/dispatch [:update {:router/view :home}])}
    [:box#about
     {:top 0
      :left 0
      :width "100%"
      :height 10
      :style {:border {:fg :blue}}
      :border {:type :line}
      :label " About "}
     [:text {:width "50%"
             :content "About this app"}]]))

(defn resources
  [_]
  (with-keys @screen {["h" "backspace" "return"] #(rf/dispatch [:update {:router/view :home}])}
    [:box#about
     {:top 0
      :left 0
      :width "100%"
      :height "50%"
      :style {:border {:fg :cyan}}
      :border {:type :line}
      :label " Resources "}
     [:text {:width "50%"
             :content "Resources"}]]))

(defn credits
  [_]
  (with-keys @screen {["h" "backspace" "return"] #(rf/dispatch [:update {:router/view :home}])}
    [:box#about
     {:top 0
      :left 0
      :width "100%"
      :height "50%"
      :style {:border {:fg :magenta}}
      :border {:type :line}
      :label " Credits "}
     [:text {:width "50%"
             :content "Resources"}]]))

(defn root [_]
  [:box#base {:left   0
              :right  0
              :width  "100%"
              :height "100%"}
   [router {:views {:home home
                    :about about
                    :resources resources
                    :credits credits}
            :view (:router/view @(rf/subscribe [:db]))}]
   [debug-box]])

(defn clear-log!
  []
  (reset! logger {}))

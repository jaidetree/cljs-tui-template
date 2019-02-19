(ns {{main-ns}}.debug.views
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [{{main-ns}}.core :refer [screen]]
            [{{main-ns}}.keys :refer [with-keys]]
            [{{main-ns}}.views :refer [router]]
            [{{main-ns}}.views.menus :refer [vertical-menu]]))

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
  (with-keys @screen {["g"]                #(rf/dispatch [:movement/first])
                      ["S-g"]              #(rf/dispatch [:movement/last])
                      ; ["h" "left" "C-a"]   #(rf/dispatch [:movement/left])
                      ; ["j" "down" "C-n"]   #(rf/dispatch [:movement/down])
                      ; ["k" "up" "C-p"]     #(rf/dispatch [:movement/up])
                      ["l" "right" "C-e"]  #(rf/dispatch [:update {:router/view :about}])}
    [:box#home
     {:top 0
      :left 0
      :width "100%"
      :height 10
      :style {:border {:fg :green}}
      :border {:type :line}
      :label "Home"}
     [vertical-menu {:options {:about "About"
                               :help "Help"
                               :library "Library"}
                     :on-select #(rf/dispatch [:update {:router/view %}])}]]))

(defn about
  [_]
  (with-keys @screen {["h"] #(rf/dispatch [:update {:router/view :home}])}
    [:box#about
     {:top 0
      :left 0
      :width "100%"
      :height 10
      :style {:border {:fg :blue}}
      :border {:type :line}
      :label "About"}
     [:text {:width "50%"
             :content "About this app"}]]))

(defn root [_]
  [:box#base {:left   0
              :right  0
              :width  "100%"
              :height "100%"}
   [router {:views {:home home
                    :about about}
            :view (:router/view @(rf/subscribe [:db]))}]
   [debug-box {:height 10}]])

(defn clear-log!
  []
  (reset! logger {}))

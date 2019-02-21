(ns {{main-ns}}.demo.views
  (:require
   [clojure.pprint :refer [pprint]]
   [clojure.string :refer [join]]
   [re-frame.core :as rf]
   [reagent.core :as r]
   [{{main-ns}}.views :refer [router vertical-menu]]))

(defn navbar
  "Display a box with a border and an interactive vertical-menu.
  User can use j/k or up/down to navigate items and either enter or l to view
  a page. Dispatches re-frame :update to set :router/view in app db.
  Returns a hiccup :box element."
  [_]
  [:box#home
   {:top    0
    :left   0
    :width  "30%"
    :height "50%"
    :style  {:border {:fg :cyan}}
    :border {:type :line}
    :label  " Menu "}
   [vertical-menu {:options {:home "Intro"
                             :about "About"
                             :resources "Resources"
                             :credits "Credits"}
                   :bg :magenta
                   :fg :black
                   :on-select #(rf/dispatch [:update {:router/view %}])}]])

(defn home
  "Intro page with welcome message and usage bindings.
  Takes no arguments.
  Returns hiccup :box element."
  [_]
  [:box#home
   {:top 0
    :right 0
    :width "70%"
    :height "50%"
    :style {:border {:fg :magenta}}
    :border {:type :line}
    :label " Intro "}
   [:box#content
    {:top 1
     :left 1
     :right 1}
    [:box
     {:align :center
      :style {:fg :yellow}
      :content "Welcome, you are successfully running the app.\nHappy hacking!"}]
    [:box#keys
     {:top 5
      :left 2
      :right 2
      :align :left
      :content "Usage:\n\n  - j/k or up/down to select a page\n  - enter or l to view page"}]]])

(defn about
  "About page to explain project and features.
  Takes no arguments.
  Returns hiccup :box element."
  [_]
  [:box#about
   {:top 0
    :right 0
    :width "70%"
    :height "50%"
    :style {:border {:fg :blue}}
    :border {:type :line}
    :label " About "}
   [:box#content
    {:top 1
     :left 1
     :right 1
     :bottom 1}
    [:text {:content "This is a sample app using the leiningen cljs-cli template:"}]
    [:box {:top 3
           :align :center
           :style {:fg :green}
           :content "https://github.com/eccentric-j/cljs-cli-template"}]
    [:text {:top 5
            :align :center
            :content  (join "\n  - "
                        ["Features:\n"
                         "Use ClojureScript and functional programming\n    to deliver rich CLIs quickly"
                         "Manage your state and side-effects with re-frame"
                         "Compose simple view functions into a rich UI\n    with Reagent React views"
                         "Use web technologies you are already familiar with"
                         "Faster start up time with node"
                         "Supports shadow, figwheel-main, or lein-figwheel"])}]]])

(defn resources
  "Resources page shares links to learn more about this project.
  Takes no arguments.
  Returns hiccup :box element."
  [_]
  [:box#about
   {:top 0
    :right 0
    :width "70%"
    :height "50%"
    :style {:border {:fg :red}}
    :border {:type :line}
    :label " Resources "}
   [:box#content
    {:top 1
     :left 1
     :right 1
     :bottom 1}
    [:text (join "\n  - "
                 ["Learn more about the technology behind this powerful ClojureScript template:\n"
                  "https://clojurescript.org/"
                  "https://github.com/chjj/blessed"
                  "https://github.com/Yomguithereal/react-blessed"
                  "https://reagent-project.github.io/"
                  "https://shadow-cljs.org/"
                  "https://figwheel.org/"
                  "https://github.com/bhauman/lein-figwheel"])]]])

(defn credits
  "Credits page to give proper credit to the inspiration for this project.
  Takes no arguments.
  Returns hiccup :box element."
  [_]
  [:box#about
   {:top 0
    :right 0
    :width "70%"
    :height "50%"
    :style {:border {:fg :yellow}}
    :border {:type :line}
    :label " Credits "}
   [:box#content
    {:top 1
     :left 1
     :right 1
     :bottom 1}
    [:text (join "\n  - "
                 ["This whole project would not exist without Denis Isidoro and his Floki project. A lightbulb lit up and I knew I had to make a template based on the stack he used to create Floki."])]
    [:box
     {:top 5
      :align :center
      :content "https://github.com/denisidoro/floki"}]
    [:text
     {:top 8}
     (join "\n  - "
           ["This template was created by Eccentric J and is open sourced on github. You can use the Clojure leiningen tool to generate a template."])]
    [:box
     {:top 12
      :left 4
      :align :left
      :content "https://github.com/eccentric-j/cljs-cli-template\nhttps://eccentric-j.com/"}]]])

(defn loader
  "Shows a mock-loader progress bar for dramatic effect.
  Uses with-let to create a progress atom and an interval to update it every
  15 ms until progress is 100.
  Starts the timer on each mount.
  Navigates to home page when completed.
  Takes no arguments.
  Returns hiccup :box element."
  [_]
  (r/with-let [progress (r/atom 0)
               interval (js/setInterval #(swap! progress inc) 15)]
    (when (>= @progress 100)
      (js/clearInterval interval)
      (rf/dispatch [:update {:router/view :home}]))
    [:box#loader
     {:top 0
      :width "100%"}
     [:box
      {:top 1
       :width "100%"
       :align :center
       :content "Loading Demo"}]
     [:box
      {:top 2
       :width "100%"
       :align :center
       :style {:fg :gray}
       :content "Slow reveal for dramatic effect..."}]
     [:progressbar
      {:orientation :horizontal
       :style {:bar {:bg :magenta}
               :border {:fg :cyan}
               :padding 1}
       :border {:type :line}
       :filled @progress
       :left 0
       :right 0
       :width "100%"
       :height 3
       :top 4
       :label " progress "}]]))

(defn demo
  "Takes props hash-map and a child element to display.
  Props should contain a :view keyword mapped to the current view to display
  such as :loader or :home.
  Returns hiccup :box element."
  [{:keys [view]} child]
  [:box#base {:left   0
              :right  0
              :width  "100%"
              :height "100%"}
   (when (not= view :loader) [navbar])
   [router {:views {:loader loader
                    :home home
                    :about about
                    :resources resources
                    :credits credits}
            :view view}]
   child])

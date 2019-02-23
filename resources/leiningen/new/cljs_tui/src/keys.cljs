(ns {{main-ns}}.keys
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]))

(def global-bindings
  {["escape" "q" "C-c"] #(.exit js/process 0)})

(defn bind-keys
  "Set key bindings mapping keys to functions.
  Takes a blessed screen and a map of keybindings.
  Returns nil.
  See global-bindings for example input."
  [screen key-bindings]
  (doseq [[hotkeys f] key-bindings]
    (.key screen (clj->js hotkeys) f)))

(defn unbind-keys
  "Remove key bindings from blessed screen instance.
  Takes a blssed screen and a map of keybindings.
  Returns nil."
  [screen key-bindings]
  (doseq [[hotkeys f] key-bindings]
    (.unkey screen (clj->js hotkeys) f)))

(defn setup
  "Bind global-bindings to blssed screen instance.
  Takes blessed screen instance.
  Returns nil."
  [screen]
  (bind-keys screen global-bindings))

(defn with-keys
  "Wrap a hiccup element with key-bindings. The bindings are created when
  the component is mounted and removed when the component is removed.
  Takes a blessed screen instance, map of key bindings, and a hiccup element:

  screen       blessed/screen - A blessed screen instance
  key-bindings hash-map       - Map of keybindings to handler functions
  content      vector         - A hiccup element vector to wrap

  Returns a wrapped hiccup reagent element.

  Example:
  (with-keys screen {[\"q\" \"esc\"] #(rf/dispatch [:app/quit])}
    [:box \"Quit me.\"])"
  [screen key-bindings content]
  (r/with-let [_ (bind-keys screen key-bindings)]
    content
    (finally
      (unbind-keys screen key-bindings))))

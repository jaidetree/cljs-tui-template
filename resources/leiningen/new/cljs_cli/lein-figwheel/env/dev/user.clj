(ns user
  (:require
   [figwheel-sidecar.repl-api :as f]
   [figwheel-sidecar.config   :as fc]
   [clojure.tools.namespace.repl :refer (refresh refresh-all)]))

(defn start []
  "Start figwheel build server"
  (f/start-figwheel!))

(defn repl []
  "Start cljs repl"
  (f/cljs-repl))

(defn stop []
  "Stop figwheel build server"
  (f/stop-figwheel!))

(defn reset []
  "Restart the figwheel build server"
  (println "Removing system")
  (f/remove-system)
  (refresh :after 'user/start))

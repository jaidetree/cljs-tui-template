(ns user
  (:require
   [figwheel-sidecar.repl-api :as f]
   [figwheel-sidecar.config   :as fc]
   [clojure.tools.namespace.repl :refer (refresh refresh-all)]))

(defn repl []
  "Start cljs repl"
  (f/cljs-repl))

(defn start []
  "Start figwheel build server"
  (f/start-figwheel!)
  (repl))

(defn stop []
  "Stop figwheel build server"
  (f/stop-figwheel!))

(defn reset []
  "Restart the figwheel build server"
  (println "Removing system")
  (f/remove-system)
  (refresh :after 'user/start))

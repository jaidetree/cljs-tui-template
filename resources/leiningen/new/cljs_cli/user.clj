(ns user
  (:require
   [clojure.tools.namespace :as tn]
   [mount.core :as mount]))

(defn start
  []
  (mount/start)
  :ready)

(defn stop
  []
  (mount/stop)
  :stopped)

(defn restart
  []
  (stop)
  (tn/refresh :after 'user/start))

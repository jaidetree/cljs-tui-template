(ns {{main-ns}}.events
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  :init
  (fn [db [_ opts]]
    {:router/view :home
     :opts opts}))

(rf/reg-event-db
  :update
  (fn [db [_ data]]
    (merge db data)))

(rf/reg-event-db
  :set
  (fn [db [_ data]]
    data))

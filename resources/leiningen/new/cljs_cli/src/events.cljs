(ns {{main-ns}}.events
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  :init
  (fn [_ _]
    {:router/view :home}))

(rf/reg-event-db
  :update
  (fn [db [_ data]]
    (merge db data)))

(rf/reg-event-db
  :set
  (fn [db [_ data]]
    data))

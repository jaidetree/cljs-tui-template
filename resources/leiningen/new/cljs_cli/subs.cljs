(ns {{main-ns}}.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  :db
  (fn [db _]
    db))

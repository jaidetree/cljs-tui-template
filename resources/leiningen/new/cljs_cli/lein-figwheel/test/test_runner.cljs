(ns {{main-ns}}.test-runner
  (:require
   ;; Require all your test files you wish to run
   [{{namespace}}-test]
   [cljs.test :refer [run-tests]]))

(defmethod cljs.test/report [:cljs.test/default :end-run-tests] [m]
  (if (cljs.test/successful? m)
    (.exit js/process 0)
    (.exit js/process 1)))

;; Add the tests you wish to run here
(defn main!
  []
  (cljs.test/run-tests '{{namespace}}-test))

(set! *main-cli-fn* main!)

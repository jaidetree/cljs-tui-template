(ns {{main-ns}}.test-runner
  (:require
   ;; Require all your test files you wish to run
   [{{namespace}}-test]
   [cljs.test :refer [run-tests]]))

;; Define a reporter. On failure exit with code 1 for better CI support.
(defmethod cljs.test/report [:cljs.test/default :end-run-tests] [m]
  (if (cljs.test/successful? m)
    (.exit js/process 0)
    (.exit js/process 1)))

;; Add the tests you wish to run here
(defn main!
  "Main entrypoint. Runs the provided test namespaces."
  []
  (cljs.test/run-tests '{{namespace}}-test))

(set! *main-cli-fn* main!)

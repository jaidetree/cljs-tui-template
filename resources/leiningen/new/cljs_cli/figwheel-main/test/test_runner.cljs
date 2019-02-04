(ns {{main-ns}}.test-runner
  (:require
    ;; require all the namespaces that you want to test
    [{{namespace}}-test]
    [figwheel.main.testing :refer [run-tests-async]]))

(defn -main [& args]
  (run-tests-async 5000))

(set! *main-cli-fn* nil)

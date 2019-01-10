(ns {{main-ns}}.test-runner
  (:require
   ;; Require all your test files you wish to run
   [{{namespace}}-test]
   [cljs.test :refer-macros [run-tests]]))

;; Add the tests you wish to run here
(cljs.test/run-tests '{{namespace}}-test)
#!/usr/bin/env bash
set -eu -o pipefail
mkdir -p examples
id=`date +%s`
lein new cljs-cli my-test-project --to-dir examples/test-$id -- "$@"
cd examples/test-$id && lein figwheel

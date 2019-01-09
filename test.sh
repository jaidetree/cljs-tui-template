#!/usr/bin/env bash
rm -rf test/myproject
mkdir -p test
id=`date +%s`
lein new cljs-cli my-test-project --to-dir test/test-$id
cd test/test-$id && lein figwheel

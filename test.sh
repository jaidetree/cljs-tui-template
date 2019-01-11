#!/usr/bin/env bash
set -eu -o pipefail
mkdir -p examples

if [ -n $1 ];
  then
    name="figwheel-main"
  else
    name="${1:1}"
fi;

rm -rf "examples/$name"
lein new cljs-cli my-test-project --to-dir examples/$name -- "$@"
./build.sh "examples/$name"

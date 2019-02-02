#!/usr/bin/env bash
set -eu -o pipefail
mkdir -p examples

if [ -z "$1" ];
  then
    name="figwheel-main"
  else
    name="${1:1}"
fi;

rm -rf "examples/$name"
lein new cljs-cli my-test-project --to-dir examples/$name -- "$@"

cat "examples/$name/package.json"
if [ "$name" == "shadow" ];then
  cat "examples/$name/shadow-cljs.edn"
elif [ "$name" == "lein-figwheel" ];then
  cat "examples/$name/project.clj"
else
  cat "examples/$name/project.clj"
  cat "examples/$name/dev.cljs.edn"
  cat "examples/$name/prod.cljs.edn"
fi;

#!/usr/bin/env bash
cd $1 && bash -c "node --inspect target/js/compiled/my-test-project.js"

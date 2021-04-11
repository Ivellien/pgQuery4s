#!/bin/bash

set -xeuo pipefail

JAVA_HOME="/usr/local/opt/openjdk"

if [[ "$OSTYPE" == "linux"* ]]; then
  INCLUDE_DIR="linux"
  LIB_NAME="libPgQueryWrapper.so"
elif [[ "$OSTYPE" == "darwin"* ]]; then
  INCLUDE_DIR="darwin"
  LIB_NAME="libPgQueryWrapper.dylib"
fi

cd ../native
clang++ -shared -O3 \
    -I/usr/include \
    -I$JAVA_HOME/include \
    -I$JAVA_HOME/include/$INCLUDE_DIR \
    -I./libpg_query \
    -L./libpg_query \
    PgQueryWrapper.cpp -lpg_query -o $LIB_NAME

native_dir=$(pwd)

cd ../scala
scalac PgQueryWrapper.scala
scala -cp . -Djava.library.path="$native_dir" PgQueryWrapper

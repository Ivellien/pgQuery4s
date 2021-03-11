#!/bin/bash

cd ../native
gcc -shared -O3 \
    -I/usr/include \
    -I$JAVA_HOME/include \
    -I$JAVA_HOME/include/linux \
	  -I./libpg_query \
	  -L./libpg_query \
    PgQueryWrapper.cpp -lpg_query -o libPgQueryWrapper.so
native_dir=$(pwd)
cd ../scala
scalac PgQueryWrapper.scala
scala -cp . -Djava.library.path=$native_dir PgQueryWrapper

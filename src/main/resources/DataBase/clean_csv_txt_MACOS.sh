#!/bin/bash

cd "$(dirname "$0")" || exit

for file in *.txt *.csv; do
    if [ "$file" != "authDataBase.csv" ]; then
        : > "$file"
    fi
done

echo "Content of text and csv files erased, except for authDataBase.csv."

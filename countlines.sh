#!/bin/bash

find . -name '*.java' -o -name "*.xml" | xargs wc -l

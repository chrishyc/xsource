#!/usr/bin/env bash
path=`cd $(dirname $0);pwd -P`
for i in `ls`
do
    text=`sed -n '1p' ${i}`
    echo ${text}
    text=`echo $text|sed -e "s/(//g"`
    text=`echo $text|sed -e "s/create/drop/g"`
    text=`echo $text|sed -e "s/CREATE/drop/g"`
    echo ${text}
    sed -i  "1i\\$text" ${i}
done

#!/bin/bash

if(($#==0));then
echo "variables must not be empty"
exit
fi

user=$1
sourceDir=$2
targetDir=$3

ips=(123 124 125 126)

for host in ips ; do

    echo "------------------- linux${host} --------------"
    rsync -rvl ${sourceDir} ${user}@${host}:${targetDir}

done

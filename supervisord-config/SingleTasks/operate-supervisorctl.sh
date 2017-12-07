#!/bin/bash

mkdir -p ./logs/children

if [ -z $1 ]
then
    config_file=supervisord-single.conf
else
    config_file=$1
fi

command="supervisorctl -c ${config_file}"

echo $command
eval $command

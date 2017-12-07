#!/bin/bash

mkdir -p /tmp/supervisord/children

if [ -z $1 ]
then
    config_file=supervisord-global.conf
else
    config_file=$1
fi

command="supervisord -c ${config_file}"
echo $command
eval $command

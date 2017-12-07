#!/bin/bash

config_file=supervisord-global.conf

action=$1
command="supervisorctl -c ${config_file} ${action}"

echo $command
eval $command

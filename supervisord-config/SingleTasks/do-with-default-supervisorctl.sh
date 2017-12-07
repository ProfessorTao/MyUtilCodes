#!/bin/bash

config_file=supervisord-single.conf

action=$1
command="supervisorctl -c ${config_file} ${action}"

echo $command
eval $command

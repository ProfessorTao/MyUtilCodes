#!/bin/bash
echo "START  "`date`

if test $1 = 0
then
    command='echo "  NOW  "`date`'
else
    command="ps -ef | grep python"
fi

while true
do
    sleep 5
    echo $command
    eval $command
    echo
done
echo "END  "`date`

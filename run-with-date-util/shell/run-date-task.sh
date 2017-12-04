#!/bin/bash

# author: ProfessorTao

# if [ -z $1 ]
# then
#     # no parameters, default -2
#     start_offset=-2
# else
#     start_offset=$1
# fi

# if [ -z $2 ]
# then
#     # no parameters, default -1
#     end_offset=-1
# else
#     end_offset=$2
# fi

# for mac
# alias date=gdate

if [ -z $1 ]
then
    # no parameters, default yesterday
    start_date_str=`date +%Y%m%d --date="1 days ago"`
else
    start_date_str=`date +%Y%m%d --date="$1"`
fi

if [ -z $2 ]
then
    # no parameters, default same as start_date_str
    end_date_str=$start_date_str
else
    end_date_str=`date +%Y%m%d --date="$2"`
fi

start_times=`date +%s --date="${start_date_str}"`
end_times=`date +%s --date="${end_date_str}"`

for (( times=$start_times; times<=$end_times; times+=86400 ))
do
    date_str=`date +%Y%m%d -d "@${times}"`
    command="echo ${date_str}"
    echo $command
    eval $command
done

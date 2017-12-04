#encoding:utf-8

from __future__ import print_function
from future.utils import iteritems

import sys
if sys.version[0] == 3:
    basestring = str

import datetime
from datetime import date
import argparse


def daily_task(date_str):
    step = 0

    step += 1
    print("%d. Run step 1." % step)
    # step1.daily_task(date_str)

    step += 1
    print("%d. Run step 2." % step)
    # step1.daily_task(date_str)


def main():
    yesterday = date.today() - datetime.timedelta(days=1)
    yesterday_str = yesterday.strftime('%Y%m%d')

    parser = argparse.ArgumentParser(description=
                ('Input date arguments: default use date and offset to calculate the certain day, '
                 'but will be instead when start_date and end_date are given.')
            )
    parser.add_argument('-d', '--date', default=yesterday_str,
                        help='Input date str like 20170101, default yesterday.')
    parser.add_argument('-o', '--offset', type=int, default=0,
                        help='Input days offset with given date, default 0.')
    parser.add_argument('-s', '--start_date', default=str(),
                        help='start date str like 20170101, default nothing.')
    parser.add_argument('-e', '--end_date', default=str(),
                        help='End date str like 20170101, default nothing.')

    args = parser.parse_args()
    str_to_date = lambda date_str, format_str="%Y%m%d": datetime.datetime.strptime(date_str, format_str)
    date_to_str = lambda target_date, format_str="%Y%m%d": target_date.strftime(format_str)

    print("Input Arguments:", args)
    if args.start_date and args.end_date:
        start_date = str_to_date(args.start_date)
        end_date = str_to_date(args.end_date)
        delta = end_date - start_date
        delta_zeroday = datetime.timedelta(days=0)
        if delta < delta_zeroday:
            print("Error parameters of start date %s and end date %s!" % (args.start_date, args.end_date))
            print("The program will exit with error code 1!")
            return 1
        target_dates = list()
        delta_oneday = datetime.timedelta(days=1)
        each_date = start_date
        while(each_date <= end_date):
            target_dates.append(each_date)
            each_date += delta_oneday
        target_datestr_list = [date_to_str(each_date) for each_date in target_dates]
    else:
        input_date = str_to_date(args.date)
        target_date = input_date + datetime.timedelta(days=args.offset)
        target_datestr_list = [date_to_str(target_date)]

    if not target_datestr_list:
        print("Error Arguments!")
        return 1

    # 2. 开始计算
    print(target_datestr_list)
    for date_str in target_datestr_list:
        print(date_str)
        daily_task(date_str)
    return 0
# end of main


if __name__ == '__main__':
    import time
    split_line = '=' * 100
    print(split_line)
    print('  *** Begin: %s ***' % time.ctime())
    ret = main()
    print('  *** End: %s ***\n\n' % time.ctime())
    if (ret is not None and ret != 0):
        print("Exit code is %d!" % ret)
        sys.exit(ret)

#--coding:utf-8--

from __future__ import print_function
import subprocess
import argparse
import os
import sys
import time
import logging

from python_utils import converters
from daemonize import Daemonize

CURRENT_DIR = os.path.dirname(os.path.realpath(__file__))
os.chdir(CURRENT_DIR)


daemon_config_module = 'example-subprocess'
parser = argparse.ArgumentParser(description='A tool for run a program as daemon.'
        )
parser.add_argument('-a', '--action', default="start",
                    help='Action command, should be start, stop, restart, check or maintain.')
parser.add_argument('-c', '--config', default="example-subprocess",
                    help='Config file for daemon program.')
args = parser.parse_args()
action = args.action
config_module = args.config
if config_module != daemon_config_module:
    print("Given daemon config file: %s.py." % config_module)
else:
    print("Use the example daemon config file: %s.py." % daemon_config_module)
daemon_config = __import__(config_module)


logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)
logger.propagate = False

ch = logging.StreamHandler(sys.stdout)
ch.setLevel(logging.DEBUG)
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
ch.setFormatter(formatter)
logger.addHandler(ch)

fh = logging.FileHandler(daemon_config.logfile, "a")
fh.setLevel(logging.DEBUG)
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
fh.setFormatter(formatter)
logger.addHandler(fh)

keep_fds = [fh.stream.fileno(), ch.stream.fileno()]


def test_run():
    while True:
        logger.debug("Start at %s." % time.ctime())
        time.sleep(10)
        logger.debug(" - End at %s." % time.ctime())
        break


def run_subprocess():
    # work_path = os.getcwd()
    # logger.debug("Current path: %s" % work_path)
    os.chdir(CURRENT_DIR)
    work_path = os.getcwd()
    logger.debug("Current work path: %s" % work_path)
    fpout = open(daemon_config.stdout_file, "a")
    fperr = open(daemon_config.stderr_file, "a")
    cmds = daemon_config.commands
    run_command = " ".join(cmds)
    logger.info("Subprocess run command: %s." % run_command)
    # rc = subprocess.call(cmds)
    child = subprocess.Popen(cmds, stdout=fpout, stderr=fperr)
    sub_pid = child.pid
    logger.info("Subprocess <%s> pid: %d." % (daemon_config.app_daemon_name, sub_pid))
    child.wait()
    fpout.close()
    fperr.close()
    rc = child.returncode
    logger.info("Subprocess <%s> exit with return code %d." % (daemon_config.app_daemon_name, rc))


def check_daemon():
    pid = -1
    try:
        fpr = open(daemon_config.pidfile, "r")
        pidstr = fpr.read().strip()
        fpr.close()
        pid = converters.to_int(pidstr, -1)
    except:
        pass
    if pid > 0:
        command = "ps -ef | grep -w %d | grep -v grep" % pid
        logger.info("Check <%s> command: %s" % (daemon_config.app_daemon_name, command))
        out = os.popen(command).read().strip()
        return bool(out)
    else:
        return False


def stop_daemon():
    pid = -1
    try:
        fpr = open(daemon_config.pidfile, "r")
        pidstr = fpr.read().strip()
        fpr.close()
        pid = converters.to_int(pidstr, -1)
    except:
        pass
    # stop all: ps -ef | grep -w 25926 | grep -v grep | awk '{print $2}' | xargs kill
    if pid > 0:
        command = "ps -ef | grep -w %d | grep -v grep | awk '{print $2}' | xargs kill" % pid
        logger.info("Stop <%s> command: %s" % (daemon_config.app_daemon_name, command))
        os.system(command)
        os.remove(daemon_config.pidfile)
        logger.info("Program <%s> has stopped!" % daemon_config.app_daemon_name)
    else:
        logger.info("Program <%s> is not running!" % daemon_config.app_daemon_name)


def start_daemon():
    logger.info("Prepare to daemonize program <%s>." % daemon_config.app_name)

    target = run_subprocess
    daemon = Daemonize(app=daemon_config.app_name, pid=daemon_config.pidfile, 
                       action=target, keep_fds=keep_fds)

    logger.info("Program <%s> will be started!" % daemon_config.app_daemon_name)
    daemon.start()
    # logger.info("Program <%s> has started successfully!" % daemon_config.app_daemon_name)
    logger.info("<%s> has done!\n" % daemon_config.app_daemon_name)  # End of the daemon program


def restart_daemon():
    stop_daemon()
    start_daemon()


def main():
    global action
    logger.info("* Action Command: %s" % action)
    if action == "start":
        start_daemon()
    elif action == "stop":
        stop_daemon()
    elif action == "restart":
        restart_daemon()
    elif action == "check":
        is_running = check_daemon()
        logger.info("Program <%s> is %srunning." % (daemon_config.app_daemon_name, 
                        "" if is_running else "not "))
    elif action == "maintain":
        # check and start if not running
        is_running = check_daemon()
        if is_running:
            logger.info("Program <%s> is running, will do nothing."
                        % daemon_config.app_daemon_name)
        else:
            logger.info("Program <%s> is not running, will try to start."
                        % daemon_config.app_daemon_name)
            start_daemon()
    else:
        logger.error("Error input parameters: %s" % " ".join(sys.argv))


if __name__ == "__main__":
    main()

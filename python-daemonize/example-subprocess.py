#--coding:utf-8--
__author__ = "taotianyi"

app_name = "example"
app_daemon_name = "%s-%s" % (app_name, "daemon")
pidfile = "./daemon.pid"
logfile = "./daemon.log"
stdout_file = "./stdout-%s.log" % app_daemon_name
stderr_file = "./stderr-%s.log" % app_daemon_name
commands = ["sh", "example-subprocess.sh"]

#！/bin/bask

#程序主目录
APP_HOME=/usr/local/ods/app/dispatch

if [ ! -d "$APP_HOME/log4j" ]; then
  mkdir $APP_HOME/log4j
fi

#需要启动的Java主程序(main方法类)
APP_MAINCLASS=com.pa.agentbank.drs.job.Start

#java虚拟机启动参数
JAVA_OPTS="-Xms1024m -Xms1024m -Xmn256m  -Dsys_config_path=/usr/local/ods/app/dispatch"

#拼凑完整的classpath参数，包括指定lib目录下所有的jar
for i in "$APP_HOME"/lib/*.jar; do
   CLASSPATH="$CLASSPATH":"$i"
done

for i in "$APP_HOME"/*.jar; do
   CLASSPATH="$CLASSPATH":"$i"
done

######################################
#(函数)判断程序是否启动
#
#说明：
#使用JDK自带的JPS命令即grep命令组合，准确查找pid
#jps 加 1 参数，表示显示java的完整包路径
#使用awk,分割出pid($1部分),及Java程序名称($2部分)
######################################
#初始化psid变量（全局）
psid=0

checkpid() {
	javaps=‘$JAVA_HOME/bin/jps -1 | grep $APP_MAINCLASS‘
	
	if [ -n "$javaps" ]; then
	   psid=‘echo $javaps | awk ‘{print $1}’’
	else
	   psid=0
	fi
}


#######################################
#(函数)启动程序
#
#说明：
#1.首先调用checkpid函数，刷新$psid全局变量
#2.如果程序已经启动($psid不等于0)，则提示程序已启动
#3.如果程序没有被启动，则执行启动命令行
#4.启动命令执行后，再次调用checkpid函数
#5.如果步骤4的结果能够确认程序的pid,则打印[OK],否则打印[Failed]
#注意：echo -n 表示打印字符后，不换行
#注意: ”nohup 某命令 >/dev/null 2>$1 $” 的用法
#######################################
start() {
   checkpid
   
   if [ $psid -ne 0 ]; then
      echo ”================================”
	  echo ”warn: $APP_MAINCLASS already started! (pid=$psid)”
	  echo ”================================”
   else
      echo -n ”Starting $APP_MAINCLASS...”
	  #JAVA_CMD=”nohup $JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS>/dev/null 2>&1 &”
	  #su - $RUNNING_USER -c ”$JAVA_CMD”
	  #nohup java -cp $CLASSPATH $APP_MAINCLASS>/dev/null 2>&1 &
	  #nohup java $JAVA_OPTS -cp ./app_conf:$CLASSPATH $APP_MAINCLASS >/usr/local/ods/app/process/log4j/shellStart.log &
	  nohup java -cp  ./app_conf:$CLASSPATH $APP_MAINCLASS >/dev/null 2>&1 &
	  sleep 3
	  checkpid
	  if [ $psid -ne 0 ]; then
	     echo ”(pid=$psid) [OK]”
	  else
		 echo ”[Failed]”
	  fi
	fi
}

##########################################
#(函数)停止程序
#
#说明：
#1. 首先调用checkpid函数，刷新$psid全局变量
#2. 如果程序已经启动($psid不等于0)，则开始执行停止，否则，提示程序未运行
#3. 使用kill -9 pid命令强制杀死进程
#4. 执行kill命令行紧接其后，马上查看上一句命令的返回值：$?
#5. 如果步骤4的结果$?等于0，则打印[OK],否则打印[Failed]
#6. 为了防止java程序被启动多次，这里增加反复检查进程，反复杀死进程的处理
#
#
##########################################
stop() {
   checkpid
   
   if [ $psid -ne 0 ]; then
      echo -n ”Stopping $APP_MAINCLASS...(pid=$psid)”
	  #su - $RUNNING_USER -c ”kill -9 $psid”
	  kill -9 $psid
	  if [ $? -eq 0 ];then
	     echo ”[OK]”
	  else
	     echo ”[Failed]”
	  fi
	        sleep 3
	  checkpid
	  if [ $psid -ne 0 ]; then
	     stop
	  fi
   else
      echo ”==========================”
      echo ”warn:$APP_MAINCLASS is not running”
      echo ”==========================”
   fi
}

###########################################
#(函数)检查程序运行状态
#
#说明：
#1. 首先调用checkpid函数，刷新$psid全局变量
#2. 如果程序已经启动($psid不等于0)，则提示正在运行并表示出pid
#3. 否则，提示程序未运行
###########################################
status() {
   checkpid
   
   if [ $psid -ne 0 ]; then
      echo ”$APP_MAINCLASS is running! (pid=$psid)”
   else
      echo ”$APP_MAINCLASS is not running”
   fi
}


case ”$1” in
   'start')
      start
	  ;;
   'stop')
      stop
	  ;;
   'status')
      status
	  ;;
  *)
      echo ”Usage: $0 {start|stop|status}”
	  ;;
   esac





















}

##########################################

















}



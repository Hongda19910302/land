@echo off

echo "开始国土平台部署环境初始化（远程拷贝耗时，请耐心等候）.."
cd C:\Program Files\apache-ant-1.9.6\ant-deniro
call ant.bat -f "build.xml" -logger org.apache.tools.ant.listener.TimestampedLogger


echo "开始解压war文件..."
rem 进入war所在路径
cd C:\Program Files\apache-tomcat-7.0.59\webapps\root\
call jar xvf land-1.0-SNAPSHOT.war

rem echo "开始删除war文件..."
rem cd C:\Program Files\apache-ant-1.9.6\ant-deniro
rem call ant.bat clear -f "build.xml" -logger org.apache.tools.ant.listener.TimestampedLogger

echo "开始启动tomcat"
cd C:\Program Files\apache-tomcat-7.0.59\bin\
call startup.bat


Pause
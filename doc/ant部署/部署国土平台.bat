@echo off

echo "��ʼ����ƽ̨���𻷾���ʼ����Զ�̿�����ʱ�������ĵȺ�.."
cd C:\Program Files\apache-ant-1.9.6\ant-deniro
call ant.bat -f "build.xml" -logger org.apache.tools.ant.listener.TimestampedLogger


echo "��ʼ��ѹwar�ļ�..."
rem ����war����·��
cd C:\Program Files\apache-tomcat-7.0.59\webapps\root\
call jar xvf land-1.0-SNAPSHOT.war

rem echo "��ʼɾ��war�ļ�..."
rem cd C:\Program Files\apache-ant-1.9.6\ant-deniro
rem call ant.bat clear -f "build.xml" -logger org.apache.tools.ant.listener.TimestampedLogger

echo "��ʼ����tomcat"
cd C:\Program Files\apache-tomcat-7.0.59\bin\
call startup.bat


Pause
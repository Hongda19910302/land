@echo off

echo "��ʼ����ƽ̨���𻷾���ʼ����Զ�̿�����ʱ�������ĵȺ�.."
call ant.bat -f "build.xml" -logger org.apache.tools.ant.listener.TimestampedLogger


echo "��ʼ��ѹwar�ļ�..."
rem ����war����·��
cd C:\Program Files\apache-tomcat-7.0.59\webapps\ROOT\
call jar xvf land-1.0-SNAPSHOT.war

echo "��ʼɾ��war�ļ�..."
del C:\Program Files\apache-tomcat-7.0.59\webapps\ROOT\land-1.0-SNAPSHOT.war

echo "��ʼ����tomcat"
cd C:\Program Files\apache-tomcat-7.0.59\bin\
call startup.bat


Pause